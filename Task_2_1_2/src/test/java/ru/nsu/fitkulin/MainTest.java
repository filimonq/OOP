package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Contains unit tests for the distributed prime checking system.
 */
public class MainTest {
    private int getAvailablePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testWithPrimeNumbers() throws InterruptedException, IOException {
        long[] numbers = {2, 3, 5, 7, 11, 13};
        int workerCount = 2;
        int port = getAvailablePort();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream, true);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        Master master = new Master(numbers, workerCount, port, null);

        CountDownLatch latch = new CountDownLatch(1);
        Thread masterThread = new Thread(() -> master.start(() -> latch.countDown()));
        masterThread.start();

        if (!latch.await(2, TimeUnit.SECONDS)) {
            System.setOut(originalOut);
            throw new RuntimeException("Master failed to start within 2 seconds");
        }

        CountDownLatch workerLatch = new CountDownLatch(workerCount);
        Thread[] workerThreads = new Thread[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workerThreads[i] = new Thread(() -> {
                Worker.run(port);
                workerLatch.countDown();
            });
            workerThreads[i].start();
        }

        if (!workerLatch.await(1, TimeUnit.SECONDS)) {
            System.setOut(originalOut);
            throw new RuntimeException("Workers failed to complete within 1 second");
        }
        for (Thread workerThread : workerThreads) {
            workerThread.join(1000);
            if (workerThread.isAlive()) {
                System.err.println("Worker thread still alive after join timeout");
            }
        }
        masterThread.join(3000);

        System.setOut(originalOut);
        printStream.flush();
        String output = outputStream.toString();
        System.out.println("TestWithPrimeNumbers output: [" + output + "]");
        assertTrue(output.contains("Final result: false"),
                "Ожидается false, так как все числа простые");
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testWithDyingWorker() throws InterruptedException, IOException {
        long[] numbers = {6, 8, 7, 13, 5, 9, 4};
        int workerCount = 3;
        int port = getAvailablePort();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream, true);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        CountDownLatch completionLatch = new CountDownLatch(1);
        Master master = new Master(numbers, workerCount, port, completionLatch);

        CountDownLatch startLatch = new CountDownLatch(1);
        Thread masterThread = new Thread(() -> master.start(() -> startLatch.countDown()));
        masterThread.start();

        if (!startLatch.await(2, TimeUnit.SECONDS)) {
            System.setOut(originalOut);
            throw new RuntimeException("Master failed to start within 2 seconds");
        }

        CountDownLatch workerLatch = new CountDownLatch(workerCount);
        Thread[] workerThreads = new Thread[workerCount];
        AtomicBoolean shouldDie = new AtomicBoolean(true);
        for (int i = 0; i < workerCount; i++) {
            final int workerId = i;
            workerThreads[i] = new Thread(() -> {
                try {
                    if (workerId == 0 && shouldDie.get()) {
                        Socket socket = new Socket("localhost", port);
                        socket.setSoTimeout(1000);
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        out.writeUTF("REQUEST_TASK");
                        out.flush();
                        String response = in.readUTF();
                        if ("TASK".equals(response)) {
                            System.out.println("Worker 0 received task and dies");
                            socket.close();
                            return;
                        }
                        socket.close();
                    } else {
                        Worker.run(port);
                    }
                } catch (IOException e) {
                    System.err.println("Worker " + workerId + " error: " + e.getMessage());
                } finally {
                    workerLatch.countDown();
                }
            });
            workerThreads[i].start();
        }

        if (!workerLatch.await(2, TimeUnit.SECONDS)) {
            System.setOut(originalOut);
            throw new RuntimeException("Workers failed to complete within 2 seconds");
        }
        for (Thread workerThread : workerThreads) {
            workerThread.join(1000);
            if (workerThread.isAlive()) {
                System.err.println("Worker thread still alive after join timeout");
            }
        }
        if (!completionLatch.await(3, TimeUnit.SECONDS)) {
            System.setOut(originalOut);
            throw new RuntimeException("Master failed to complete within 3 seconds");
        }

        System.setOut(originalOut);
        printStream.flush();
        String output = outputStream.toString();
        System.out.println("TestWithDyingWorker output: [" + output + "]");
        assertTrue(output.contains("Final result: true"),
                "Ожидается true, так как есть непростые числа");
    }

    @Test
    @Timeout(value = 20, unit = TimeUnit.SECONDS)
    public void testWithLargeArrayAndOneNonPrime() throws InterruptedException, IOException {
        long[] numbers = generateLargeArrayWithOneNonPrime(1000);
        int workerCount = 10;
        int port = getAvailablePort();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream, true);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        Master master = new Master(numbers, workerCount, port, null);

        CountDownLatch latch = new CountDownLatch(1);
        Thread masterThread = new Thread(() -> master.start(() -> latch.countDown()));
        masterThread.start();

        if (!latch.await(2, TimeUnit.SECONDS)) {
            System.setOut(originalOut);
            throw new RuntimeException("Master failed to start within 2 seconds");
        }

        CountDownLatch workerLatch = new CountDownLatch(workerCount);
        Thread[] workerThreads = new Thread[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workerThreads[i] = new Thread(() -> {
                Worker.run(port);
                workerLatch.countDown();
            });
            workerThreads[i].start();
        }

        if (!workerLatch.await(5, TimeUnit.SECONDS)) {
            System.setOut(originalOut);
            throw new RuntimeException("Workers failed to complete within 5 seconds");
        }
        for (Thread workerThread : workerThreads) {
            workerThread.join(1000);
            if (workerThread.isAlive()) {
                System.err.println("Worker thread still alive after join timeout");
            }
        }
        masterThread.join(5000);

        System.setOut(originalOut);
        printStream.flush();
        String output = outputStream.toString();
        System.out.println("TestWithLargeArrayAndOneNonPrime output: [" + output + "]");
        assertTrue(output.contains("Final result: true"),
                "Ожидается true, так как есть одно непростое число");
    }

    private long[] generateLargeArrayWithOneNonPrime(int size) {
        long[] primes = new long[size];
        primes[0] = 2;
        long nextPrime = 3;
        for (int i = 1; i < size - 1; i++) {
            primes[i] = nextPrime;
            nextPrime = findNextPrime(nextPrime);
        }
        primes[size - 1] = 4;
        return primes;
    }

    private long findNextPrime(long n) {
        long next = n + 1;
        while (!isPrime(next)) {
            next++;
        }
        return next;
    }

    private boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }
        for (long i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
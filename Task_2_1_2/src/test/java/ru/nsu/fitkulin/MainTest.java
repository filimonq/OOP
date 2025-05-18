package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    private int getAvailablePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testWithMixedNumbers() throws InterruptedException, IOException {
        long[] numbers = {6, 8, 7, 13, 5, 9, 4};
        int workerCount = 3;
        int port = getAvailablePort();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream, true);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        Master master = new Master(numbers, workerCount, port);

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
        masterThread.join(3000); // Увеличенный таймаут для masterThread

        System.setOut(originalOut);
        printStream.flush();
        String output = outputStream.toString();
        System.out.println("TestWithMixedNumbers output: [" + output + "]");
        assertTrue(output.contains("Final result: true"),
                "Ожидается true, так как есть непростые числа");
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

        Master master = new Master(numbers, workerCount, port);

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
        masterThread.join(3000); // Увеличенный таймаут для masterThread

        System.setOut(originalOut);
        printStream.flush();
        String output = outputStream.toString();
        System.out.println("TestWithPrimeNumbers output: [" + output + "]");
        assertTrue(output.contains("Final result: false"),
                "Ожидается false, так как все числа простые");
    }
}
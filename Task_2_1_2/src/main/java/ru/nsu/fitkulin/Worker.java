package ru.nsu.fitkulin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * A Worker in the distributed system that connects to the Master to request tasks.
 * It processes the assigned task by checking if any numbers
 * in the task's array are non-prime and reports the result back to the Master.
 */
public class Worker {
    private static final String masterHost = "localhost";
    private static final int MAX_ATTEMPTS = 3;

    public static void run(int port) {
        int attempts = 0;
        boolean shouldExit = false;
        while (attempts < MAX_ATTEMPTS && !shouldExit) {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(masterHost, port), 1000);
                socket.setSoTimeout(1000);
                System.out.println("Connected to Master at " + masterHost + ":" + port);

                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                out.writeUTF("REQUEST_TASKS");
                out.flush();

                String response = in.readUTF();
                if ("TASKS".equals(response)) {
                    int taskCount = in.readInt();
                    for (int t = 0; t < taskCount; t++) {
                        int taskId = in.readInt();
                        int size = in.readInt();
                        long[] numbers = new long[size];
                        for (int i = 0; i < size; i++) {
                            numbers[i] = in.readLong();
                        }
                        boolean hasNonPrime = false;
                        for (long num : numbers) {
                            if (!isPrime(num)) {
                                hasNonPrime = true;
                                break;
                            }
                        }
                        out.writeBoolean(hasNonPrime);
                        out.flush();
                        System.out.println("Task " + taskId + " processed: " + hasNonPrime);
                    }
                } else if ("NO_TASKS".equals(response)) {
                    System.out.println("No more tasks available");
                    shouldExit = true;
                }
            } catch (SocketTimeoutException e) {
                System.err.println("Worker timeout (attempt "
                        + (attempts + 1) + "): " + e.getMessage());
                attempts++;
                shouldExit = (attempts >= MAX_ATTEMPTS);
            } catch (IOException e) {
                System.err.println("Worker error (attempt "
                        + (attempts + 1) + "): " + e.getMessage());
                attempts++;
                shouldExit = (attempts >= MAX_ATTEMPTS);
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.err.println("Error closing socket: " + e.getMessage());
                    }
                }
            }
            if (!shouldExit) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    shouldExit = true;
                }
            }
        }
        if (attempts >= MAX_ATTEMPTS) {
            System.err.println("Worker exhausted all attempts");
        }
    }

    public static boolean isPrime(long number) {
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
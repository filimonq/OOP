package ru.nsu.fitkulin.Master;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Master {
    public static void main(String[] args) {
        long[] numbers = {11, 13, 17, 19, 23, 29, 31, 37, 41, 43};
        int workerCount = 3;
        int port = 12345;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Master started on port " + port);
            int chunkSize = (int) Math.ceil((double) numbers.length / workerCount);
            boolean finalResult = false;

            for (int i = 0; i < workerCount; i++) {
                try (Socket worker = server.accept()) {
                    worker.setSoTimeout(10000);
                    System.out.println("Worker " + i + " connected");
                    int start = i * chunkSize;
                    int end = Math.min(start + chunkSize, numbers.length);
                    long[] chunk = Arrays.copyOfRange(numbers, start, end);

                    DataOutputStream out = new DataOutputStream(worker.getOutputStream());
                    out.writeInt(chunk.length);
                    for (long num : chunk) {
                        out.writeLong(num);
                    }
                    out.flush();

                    DataInputStream in = new DataInputStream(worker.getInputStream());
                    boolean workerResult = in.readBoolean();
                    finalResult |= workerResult;
                } catch (SocketTimeoutException e) {
                    System.err.println("Worker " + i + " timed out, processing locally");
                    int start = i * chunkSize;
                    int end = Math.min(start + chunkSize, numbers.length);
                    long[] chunk = Arrays.copyOfRange(numbers, start, end);
                    finalResult |= checkChunkLocally(chunk);
                }
            }

            System.out.println("Final result: " + finalResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkChunkLocally(long[] chunk) {
        for (long number : chunk) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
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
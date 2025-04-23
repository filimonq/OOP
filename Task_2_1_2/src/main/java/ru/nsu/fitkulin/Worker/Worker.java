package ru.nsu.fitkulin.Worker;

import java.io.*;
import java.net.*;

public class Worker {
    public static void main(String[] args) {
        String masterHost = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(masterHost, port)) {
            System.out.println("Connected to Master at " + masterHost + ":" + port);

            DataInputStream in = new DataInputStream(socket.getInputStream());
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

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeBoolean(hasNonPrime);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
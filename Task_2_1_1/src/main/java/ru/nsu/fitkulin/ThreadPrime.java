package ru.nsu.fitkulin;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPrime {
    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkWithThreads(int[] numbers, int numThreads)
            throws InterruptedException {
        AtomicBoolean foundNonPrime = new AtomicBoolean(false);
        Thread[] threads = new Thread[numThreads];
        int chunkSize = (numbers.length + numThreads - 1) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, numbers.length);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    if (foundNonPrime.get()) {
                        break;
                    }
                    if (!isPrime(numbers[j])) {
                        foundNonPrime.set(true);
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        return foundNonPrime.get();
    }
}


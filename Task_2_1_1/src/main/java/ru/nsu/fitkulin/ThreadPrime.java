package ru.nsu.fitkulin;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * class for checking an array of numbers for non-prime numbers using multithreading.
 */
public class ThreadPrime {
    /**
     * checks if a number is prime.
     *
     * @param number for check.
     * @return boolean res of check.
     */
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

    /**
     * checks an array of numbers for non-prime numbers using multithreading.
     *
     * @param numbers array of numbers to check.
     * @param numThreads number of threads.
     * @return result of check.
     * @throws InterruptedException If the thread execution was interrupted.
     */
    public static boolean checkWithThreads(long[] numbers, int numThreads)
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


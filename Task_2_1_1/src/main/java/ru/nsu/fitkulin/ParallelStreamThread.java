package ru.nsu.fitkulin;

import java.util.Arrays;

/**
 * class for checking an array of numbers for non-prime numbers using parallel streams.
 */
public class ParallelStreamThread {
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
     * checks an array of numbers for non-prime numbers using parallel streams.
     *
     * @param numbers array for check.
     * @return boolean res of check.
     */
    public static boolean containsNonPrimeParallelStream(long[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !isPrime(num));
    }
}

package ru.nsu.fitkulin;

/**
 * class for checking an array of numbers for non-prime numbers in sequential mode.
 */
public class SequentialPrime {
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
     * checks an array of numbers for non-prime numbers in sequential mode.
     *
     * @param numbers array of numbers to check.
     * @return result of check.
     */
    public static boolean containsNonPrimeSequential(long[] numbers) {
        for (long number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}

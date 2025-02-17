package ru.nsu.fitkulin;

public class SequentialPrime {
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

    public static boolean containsNonPrimeSequential(long[] numbers) {
        for (long number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}

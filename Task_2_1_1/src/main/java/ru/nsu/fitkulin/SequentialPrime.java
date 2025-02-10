package ru.nsu.fitkulin;

public class SequentialPrime {
    public static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public static boolean containsNonPrimeSequential(int[] numbers) {
        for (int number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}

package ru.nsu.fitkulin;

import java.util.Arrays;

public class ParallelStreamThread {
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

    public static boolean containsNonPrimeParallelStream(long[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !isPrime(num));
    }
}

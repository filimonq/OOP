package ru.nsu.fitkulin;

import java.util.Arrays;

public class ParallelStreamThread {
    public static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public static boolean containsNonPrimeParallelStream(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !isPrime(num));
    }
}

package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SequentialPrimeTest {
    @Test
    void testSequentialWithNonPrime() {
        int[] numbers = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(SequentialPrime.containsNonPrimeSequential(numbers));
    }
}
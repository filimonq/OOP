package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamThreadTest {
    @Test
    void testParallelStream_WithNonPrime() {
        int[] numbers = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(ParallelStreamThread.containsNonPrimeParallelStream(numbers));
    }
}
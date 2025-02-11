package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamThreadTest {
    @Test
    void testParallelStream_WithNonPrime() {
        int[] numbers = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(ParallelStreamThread.containsNonPrimeParallelStream(numbers));
    }

    @Test
    void testParallelStream_WithLargePrime() {
        int[] numbers = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        assertFalse(ParallelStreamThread.containsNonPrimeParallelStream(numbers));
    }
}
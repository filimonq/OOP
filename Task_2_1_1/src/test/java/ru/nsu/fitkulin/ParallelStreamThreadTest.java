package ru.nsu.fitkulin;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


class ParallelStreamThreadTest {
    @Test
    void testParallelStream_WithNonPrime() {
        long[] numbers = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(ParallelStreamThread.containsNonPrimeParallelStream(numbers));
    }

    @Test
    void testParallelStreamWithLargePrime() {
        long[] numbers = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        assertFalse(ParallelStreamThread.containsNonPrimeParallelStream(numbers));
    }

    @Test
    public void testIsPrime() {
        assertTrue(ParallelStreamThread.isPrime(2));
        assertTrue(ParallelStreamThread.isPrime(3));
        assertFalse(ParallelStreamThread.isPrime(4));
        assertFalse(ParallelStreamThread.isPrime(1));
        assertFalse(ParallelStreamThread.isPrime(0));
        assertFalse(ParallelStreamThread.isPrime(-7));
    }

    @Test
    void timeTest() {
        long[] numbers = new long[10000];
        Arrays.fill(numbers, 2147483647);

        long startTime = System.currentTimeMillis();
        boolean result = ParallelStreamThread.containsNonPrimeParallelStream(numbers);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println("ParallelStreamThread performance test elapsed time: " + time + " ms");
        assertFalse(result);
    }
}
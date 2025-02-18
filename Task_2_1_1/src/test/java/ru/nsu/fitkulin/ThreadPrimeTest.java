package ru.nsu.fitkulin;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ThreadPrimeTest {
    @Test
    void testThreadPrimeWithNonPrime() throws InterruptedException {
        long[] numbers = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(ThreadPrime.checkWithThreads(numbers, 1));
    }

    @Test
    void testParallelStream_WithLargePrime() throws InterruptedException {
        long[] numbers = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        assertFalse(ThreadPrime.checkWithThreads(numbers, 3));
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
    void timeTest() throws InterruptedException {
        long[] numbers = new long[10000];
        Arrays.fill(numbers, 2147483647);

        long startTime = System.currentTimeMillis();
        int numThreads = 16;
        boolean result = ThreadPrime.checkWithThreads(numbers, numThreads);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println("ThreadPrime performance with " + numThreads + " threads, "
                + "test elapsed time: " + time + " ms");
        assertFalse(result);
    }
}
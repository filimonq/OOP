package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MainTest {
    @Test
    void SampleTest() {
        assertArrayEquals(new int[] {1,2,3}, Main.HeapSort(new int[] {1,2,3}));
        assertArrayEquals(new int[] {1,1,1}, Main.HeapSort(new int[] {1,1,1}));
        assertArrayEquals(new int[] {}, Main.HeapSort(new int[] {}));
        assertArrayEquals(new int[] {1,2,3,4,5}, Main.HeapSort(new int[] {5,4,3,2,1}));
    }
}
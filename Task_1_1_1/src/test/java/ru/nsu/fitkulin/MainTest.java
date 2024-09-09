package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void sampleTest() {
        assertArrayEquals(new int[] {1, 2, 3},  Main.HeapSort(new int[] {1, 2, 3}));
        assertArrayEquals(new int[] {1, 1, 1},  Main.HeapSort(new int[] {1, 1, 1}));
        assertArrayEquals(new int[] {},  Main.HeapSort(new int[] {}));
        assertArrayEquals(new int[] {1, 2, 3, 4, 5},  Main.HeapSort(new int[] {5, 4, 3, 2, 1}));
        assertArrayEquals(new int[] {1, 2, 3, 4, 5},  Main.HeapSort(new int[] {4, 2, 1, 5, 3}));
        assertArrayEquals(new int[]
                        {-435, -4, 0, 21, 2326},
                Main.HeapSort(new int[]
                        {0, 21, -435, 2326, -4}));
        assertArrayEquals(new int[]
                        {-2147483647, -2147483647, -435, -4, 0, 21, 2326, 2147483647, 2147483647},
                Main.HeapSort(new int[]
                        {2147483647, -2147483647, 0, 21, -435, 2326, -4, 2147483647, -2147483647}));
        assertArrayEquals(new int[]
                        {-10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 10},
                Main.HeapSort(new int[]
                        {0, -2, 3, -4, 5, -1, 1, 2, 7, -7, -9, -8, -3, 4, -5, 10, -6, -10, 6}));
    }
}
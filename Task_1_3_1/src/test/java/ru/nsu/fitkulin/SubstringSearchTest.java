package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringSearchTest {
    @Test
    void startTest() throws IOException {
        SubstringSearch actual = new SubstringSearch("бра");
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 8));
        assertEquals(expected, actual.find("src/test/resources/file1.txt"));
    }

    @Test
    void testUTF() throws IOException {
        SubstringSearch actual = new SubstringSearch("你");
        ArrayList<Integer> expected = new ArrayList<>(List.of(4));
        assertEquals(expected, actual.find("src/test/resources/file2.txt"));
    }

    @Test
    void testEmptyArr() throws IOException {
        SubstringSearch actual = new SubstringSearch(" ");
        ArrayList<Integer> expected = new ArrayList<>();
        assertEquals(expected, actual.find("src/test/resources/file2.txt"));
    }

    @Test
    void testLargeFile() throws IOException {
        SubstringSearch actual = new SubstringSearch("Анна Павловна");
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(2203, 2355, 3692, 5116));
        assertEquals(expected, actual.find("src/test/resources/file3.txt"));
    }

}
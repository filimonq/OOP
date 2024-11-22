package ru.nsu.fitkulin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SubstringSearchTest {
    @Test
    void startTest() throws IOException {
        SubstringSearch actual = new SubstringSearch("бра");
        ArrayList<Long> expected = new ArrayList<>(Arrays.asList(1L, 8L));
        assertEquals(expected, actual.find("src/test/resources/file1.txt"));
    }

    @Test
    void testUTF() throws IOException {
        SubstringSearch actual = new SubstringSearch("你");
        ArrayList<Long> expected = new ArrayList<>(List.of(4L));
        assertEquals(expected, actual.find("src/test/resources/file2.txt"));
    }

    @Test
    void testEmptyArr() throws IOException {
        SubstringSearch actual = new SubstringSearch(" ");
        ArrayList<Long> expected = new ArrayList<>();
        assertEquals(expected, actual.find("src/test/resources/file2.txt"));
    }

    @Test
    void testLargeFile() throws IOException {
        SubstringSearch actual = new SubstringSearch("Анна Павловна");
        ArrayList<Long> expected = new ArrayList<>(Arrays.asList(2203L, 2355L, 3691L, 5115L));
        assertEquals(expected, actual.find("src/test/resources/file3.txt"));
    }
    

}
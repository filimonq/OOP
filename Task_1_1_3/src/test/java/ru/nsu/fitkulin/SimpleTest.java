package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * test for simplified.
 */
public class SimpleTest {
    /**
     * base test.
     */
    @Test
    void simplificationTestA() {
        String expression = "(x*0) +(10-0) -  (x*1) + 12 + (128*0) + (32*1)+ 0/ y";
        String expected = "(((10-x)+12)+32)";
        String actual = Parser.parse(expression).simple().toString();
        assertEquals(expected, actual);
    }
}
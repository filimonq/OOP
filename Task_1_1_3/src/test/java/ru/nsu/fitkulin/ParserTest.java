package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * parser`s work test.
 */
class ParserTest {

    /**
     * test for (+-).
     */
    @Test
    void testParseAtom() {
        Expression res = Parser.parse("3+5+  4");
        assertInstanceOf(Add.class, res);
        assertEquals("((3+5)+4)", res.toString());
        res = Parser.parse("1- 3   -5-  4");
        assertInstanceOf(Sub.class, res);
        assertEquals("(((1-3)-5)-4)", res.toString());
    }

    /**
     * test for (/*).
     */
    @Test
    void testParseMonome() {
        Expression res = Parser.parse("y/(3*5/  4 * x)");
        assertEquals("(y/(((3*5)/4)*x))", res.toString());
    }




}
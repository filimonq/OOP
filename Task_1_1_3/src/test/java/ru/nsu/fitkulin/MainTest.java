package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    public void testMainMethods() {
        Expression e = new Add(new Number(4), new Div(new Number(2), new Variable("x")));

        assertEquals("(4+(2/x))", e.toString());

        Expression de = e.derivative("x");
        assertEquals("(0+(((x*0)-(2*1))/(x*x)))", de.toString());

        double result = e.eval("x = 10; y = 13");
        assertEquals(4.2, result);
    }

    @Test
    public void testMainMethods2() {
        Expression e = new Sub(new Mul(new Number(2.7), new Variable("x")), new Number(4));

        assertEquals("((2.7*x)-4)", e.toString());

        Expression de = e.derivative("x");
        assertEquals("(((2.7*1)+(0*x))-0)", de.toString());

        double result = e.eval("x = 10; y = 13");
        assertEquals(23.0, result);
    }
}

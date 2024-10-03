package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testMainMethods() {
        Expression e = new Add(new Number(4), new Div(new Number(2), new Variable("x")));

        assertEquals("(4+(2/x))", e.toString()); // Проверка метода toString

        Expression de = e.derivative("x");
        assertEquals("(0+(((x*0)-(2*1))/(x*x)))", de.toString()); // Проверка метода derivative

        double result = e.eval("x = 10; y = 13");
        assertEquals(4.2, result); // Проверка метода eval
    }
}

package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    public void testMain() {
        ByteArrayInputStream input = new ByteArrayInputStream("(3+7*x-10)\nx = 10\nx".getBytes());
        System.setIn(input);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        Main.main(new String[0]);
        String res = output.toString().replaceAll("\\r\\n?", "\n");

        String expectedOutput =
                """
                        Введите выражение
                        Введите переменные в формате 'x = 10; y = 5'
                        Введите переменную, по которой нужно взять производную
                        Выражение: ((3+(7*x))-10)
                        Производная: ((0+((7*1)+(0*x)))-0)
                        Результат: 63.0
                        Упрощённое: ((3+(7*x))-10)
                        """;
        assertEquals(expectedOutput, res);
    }
}
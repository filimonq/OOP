package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/**
 * Test Game class.
 */
public class GameTest {
    /**
     * checks if it runs successfully.
     */
    @Test
    public void testDealInitialCards() {
        ByteArrayInputStream input = new ByteArrayInputStream("0\n".repeat(52).getBytes());

        System.setIn(input);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Game.round();

        assertTrue(true);
    }

    @Test
    public void testDealInitialCards2() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\n".repeat(52).getBytes());

        System.setIn(input);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Game.round();

        assertTrue(true);
    }

}

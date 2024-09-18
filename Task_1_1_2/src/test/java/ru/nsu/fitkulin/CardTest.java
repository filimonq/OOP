package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CardTest {
    /**
     * Check for card structure.
     */
    @Test
    void testCard1() {
        String name = "Дама";
        String suit = "Пики";
        int value = 10;
        Card card = new Card(value, name, suit);
        assertEquals(name, card.name);
        assertEquals(value, card.value);
        assertEquals(suit, card.suit);
    }

    /**
     * Check for card print.
     */
    @Test
    void testCard2() {
        String name = "Тройка";
        String suit = "Бубны";
        int value = 3;
        Card card = new Card(value, name, suit);
        String res = card.toString();
        String expectedStr = "Тройка Бубны (3)";
        assertEquals(expectedStr, res);
    }
    /**
     * Check for card structure.
     */
    @Test
    void testCard3() {
        String name = "Туз";
        String suit = "Червы";
        int value = 11;
        Card card = new Card(value, name, suit);
        assertEquals(name, card.name);
        assertEquals(value, card.value);
        assertEquals(suit, card.suit);
    }
}

package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    void testCard() {
        String name = "Дама";
        String suit = "Пики";
        int value = 10;
        Card card = new Card(value, name, suit);
        assertEquals(name, card.name);
        assertEquals(value, card.value);
        assertEquals(suit, card.suit);

    }
}

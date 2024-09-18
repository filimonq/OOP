package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CardTest {
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
    @Test
    void testCard2() {
        String name = "Тройка";
        String suit = "Бубны";
        int value = 3;
        Card card = new Card(value, name, suit);
        assertEquals(name, card.name);
        assertEquals(value, card.value);
        assertEquals(suit, card.suit);
    }
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

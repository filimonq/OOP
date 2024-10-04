package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test for Hand class.
 */
public class HandTest {
    Card card1;
    Card card2;
    Card card3;
    Card card4;

    Hand hand;

    /**
     * sets variables and creates a special class for hands.
     */
    @BeforeEach
    public void beforeEach() {
        card1 = new Card(3, "Три", "Червы");
        card2 = new Card(10, "Король", "Пики");
        card3 = new Card(4, "Четверка", "Бубны");
        card4 = new Card(11, "Туз", "Трефы");
        hand = new Hand(card1, card4);
    }

    /**
     * test add card.
     */
    @Test
    void testAddCard() {
        hand.addCard(card3);
        assertEquals(18, hand.sumCards());
    }

    /**
     * sum with different ace values.
     */
    @Test
    void testAddAce() {
        hand.addCard(card2);
        assertEquals(14, hand.sumCards());
        hand.addCard(card4);
        assertEquals(15, hand.sumCards());
    }

    /**
     * test ace value.
     */
    @Test
    void testValueAce() {
        assertEquals(11, hand.aceValue());
        hand.addCard(card2);
        assertEquals(1, hand.aceValue());
    }


}

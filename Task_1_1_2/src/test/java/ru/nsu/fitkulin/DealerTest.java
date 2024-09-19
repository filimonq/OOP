package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Dealer Test
 */
public class DealerTest {
    Card card1;
    Card card2;
    Card card3;
    Card card4;
    Dealer dealerHand;
    @BeforeEach
    void beforeEach() {
        card1 = new Card(3, "Три", "Червы");
        card2 = new Card(10, "Король", "Пики");
        card3 = new Card(11, "Туз", "Трефы");
        card4 = new Card(11, "Туз", "Бубны");
        dealerHand = new Dealer(card1, card2);
    }
    @Test
    void testToString() {
        assertEquals("[Три Червы (3), <закрытая карта>]", dealerHand.toString());
    }

    @Test
    void testSum() {
        assertEquals(13, dealerHand.sumCards());
        dealerHand.addCard(card3);
        dealerHand.addCard(card4);
        assertEquals(15, dealerHand.sumCards());
    }

    @Test
    void testHandChangedFalse() {
        dealerHand.handChanged = false;
        assertEquals("[Три Червы (3), <закрытая карта>]", dealerHand.toString());
    }

    @Test
    void testHandChangedTrue() {
        dealerHand.handChanged = true;
        assertEquals("[Три Червы (3), Король Пики (10)]", dealerHand.toString());
    }

}

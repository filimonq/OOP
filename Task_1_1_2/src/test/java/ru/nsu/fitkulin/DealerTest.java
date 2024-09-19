package ru.nsu.fitkulin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Card card1;
    Card card2;
    Card card3;
    Card card4;
    Player playerHand;

    @BeforeEach
    void beforeEach() {
        card1 = new Card(3, "Три", "Червы");
        card2 = new Card(10, "Король", "Пики");
        card3 = new Card(11, "Туз", "Трефы");
        card4 = new Card(11, "Туз", "Бубны");
        playerHand = new Player(card1, card1);
    }

    @Test
    void testToString() {
        assertEquals("[Три Червы (3), Три Червы (3)]", playerHand.toString());
    }

    @Test
    void testAce() {
        playerHand.addCard(card3);
        assertEquals("[Три Червы (3), Три Червы (3), "
                + "Туз Трефы (11)]", playerHand.toString());
    }

    @Test
    void testAllCards() {
        playerHand.addCard(card2);
        playerHand.addCard(card3);
        playerHand.addCard(card4);
        assertEquals("[Три Червы (3), Три Червы (3), Король Пики (10), "
                + "Туз Трефы (1), Туз Бубны (1)]", playerHand.toString());
    }
    @Test
    void testTwoAce() {
        playerHand.addCard(card3);
        playerHand.addCard(card4);
        assertEquals("[Три Червы (3), Три Червы (3), "
                + "Туз Трефы (1), Туз Бубны (1)]", playerHand.toString());
    }
}

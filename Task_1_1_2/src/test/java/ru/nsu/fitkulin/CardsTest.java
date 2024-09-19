package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Cards Test.
 */
public class CardsTest {
    Cards cards;

    /**
     * test for getting card from deck.
     */
    @Test
    void getCard() {
        cards = new Cards();
        Card a = cards.getCard();
        assertEquals(51, cards.deck.size());

    }

    /**
     * test for deck initialization.
     */
    @Test
    void deckInit() {
        cards = new Cards();
        assertEquals(52, cards.deck.size());
    }


}

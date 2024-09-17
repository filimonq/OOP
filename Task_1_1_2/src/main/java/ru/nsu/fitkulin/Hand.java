package ru.nsu.fitkulin;

import java.util.ArrayList;

public class Hand {
    public ArrayList<Card> cards;

    public Hand (Card card1, Card card2) {
        this.cards = new ArrayList<Card>();
        this.cards.add(card1);
        this.cards.add(card2);
    }

    public int AceValue() {
        int sum = 0;
        int countOfAce = 0;
        for (Card card : cards) {
            if (card.value == 11) {
                countOfAce++;
            } else {
                sum += card.value;
            }
        }
        if (countOfAce * 11 + sum > 21) {
            return 1;
        }
        return 11;
    }

    public int sumCards() {
        int sum = 0;
        int countOfAce = 0;
        int curr = AceValue();
        for (Card card : cards) {
            if (card.value == 11) {
                sum += curr;
            } else {
                sum += card.value;
            }
        }
        return sum;
    }
}

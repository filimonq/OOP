package ru.nsu.fitkulin;

import java.util.ArrayList;

public class Player extends Hand {
    public Player(Card card1, Card card2) {
        super(card1, card2);
    }

    @Override
    public String toString() {
        ArrayList<String> res = new ArrayList<>();
        int curr = AceValue();
        for (Card card : cards) {
            if (card.value != 11) {
                res.add(card.name + " " + card.suit + " (" + card.value + ")");
            } else {
                res.add(card.name + " " + card.suit + " (" + curr + ")");
            }
        }
        return res.toString();
    }
}


package ru.nsu.fitkulin;

import java.util.ArrayList;

public class Dealer {
    ArrayList<Card> hand;

    public Dealer() {
        this.hand = new ArrayList<>();
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

}


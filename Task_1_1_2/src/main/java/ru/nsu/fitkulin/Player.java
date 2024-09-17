package ru.nsu.fitkulin;

import java.util.ArrayList;

public class Player {
    ArrayList<Card> hand;

    public Player() {
        this.hand = new ArrayList<>();
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}


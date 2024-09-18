package ru.nsu.fitkulin;

import java.util.ArrayList;

public class Dealer extends Hand {
    public Dealer(Card card1, Card card2) {
        super(card1, card2);
    }
    boolean handChanged = false;
    /**
     * print of dealer hand.
     *
     * @return result of output
     */
    @Override
    public String toString() {
        ArrayList<String> res = new ArrayList<>();
        int curr = aceValue();
        if (handChanged) {
            for (Card card : cards) {
                if (card.value != 11) {
                    res.add(card.name + " " + card.suit + " (" + card.value + ")");
                } else {
                    res.add(card.name + " " + card.suit + " (" + curr + ")");
                }
            }
        } else {
            res.add(this.cards.get(0).toString());
            res.add(", <закрытая карта>");
        }
        return res.toString();
    }
}


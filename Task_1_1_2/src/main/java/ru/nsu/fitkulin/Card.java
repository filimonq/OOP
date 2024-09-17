package ru.nsu.fitkulin;

/**
 * class for card.
 */
public class Card {
    int value;
    String name;
    String suit;

    /**
     * constructor.
     *
     * @param value int
     * @param name string
     * @param suit suit of card
     */
    Card (int value, String name, String suit) {
        this.value = value;
        this.name = name;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setValue(int newValue) {
        value = newValue;
    }

    /**
     * print card.
     *
     * @return all card info
     */
    @Override // аннотация для переопределения метода
    public String toString() {
        return name + " " + suit + " (" + value + ")";
    }
}

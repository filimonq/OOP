package ru.nsu.fitkulin;

/**
 * enum for information about suit.
 */
public enum CardSuit {
    SPADES("Пики"),
    CLUBS("Крести"),
    DIAMONDS("Буби"),
    HEARTS("Черви");

    final String nameOfSuit;

    CardSuit (String nameOfSuit) {
        this.nameOfSuit = nameOfSuit;
    }
}

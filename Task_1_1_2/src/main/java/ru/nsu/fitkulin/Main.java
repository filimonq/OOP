package ru.nsu.fitkulin;

/**
 * main class.
 */
public class Main {
    /**
     * start game.
     */
    public static void main(String[] args) {
        for (int i = 0; i < 52; i++) {
            Game.round();
        }
    }
}

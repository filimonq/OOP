package ru.nsu.fitkulin.model;

/**
 * Interface defining the basic properties of a game level.
 * Contains configuration parameters for game difficulty and layout.
 */
public interface Level {
    int getBaseSpeed();

    int getFoodCount();

    int getWinLength();

    int getWidth();

    int getHeight();
}
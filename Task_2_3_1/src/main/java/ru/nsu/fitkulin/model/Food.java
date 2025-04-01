package ru.nsu.fitkulin.model;

import javafx.geometry.Point2D;

/**
 * Interface representing food items in the snake game.
 * Defines the contract for any food type that can be consumed by snakes.
 */
public interface Food {
    Point2D getPosition();
}

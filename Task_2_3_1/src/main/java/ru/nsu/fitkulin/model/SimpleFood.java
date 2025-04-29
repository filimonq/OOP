package ru.nsu.fitkulin.model;

import javafx.geometry.Point2D;

/**
 * Basic implementation of Food interface representing consumable items in the game.
 * Maintains a single position on the game grid where the food appears.
 * Snake grows up + 1.
 */
public class SimpleFood implements Food {
    private final Point2D position;

    /**
     * Creates food at specified position.
     *
     * @param position The (x,y) coordinates where food will appear on game grid
     */
    public SimpleFood(Point2D position) {
        this.position = position;
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.grow();
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
}
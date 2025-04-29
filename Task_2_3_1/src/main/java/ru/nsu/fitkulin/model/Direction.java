package ru.nsu.fitkulin.model;

/**
 * Represents the four possible movement directions in the game.
 * Provides utility method to get opposite direction.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Returns the opposite direction of the current direction.
     *
     * @return The opposite direction (UP ↔ DOWN, LEFT ↔ RIGHT)
     */
    public Direction getOpposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> this;
        };
    }
}

package ru.nsu.fitkulin.model;

import java.util.List;

/**
 * Abstract base class for all bot implementations in the snake game.
 * Provides core bot functionality and defines the decision-making interface.
 */
public abstract class Bot extends Snake {
    /**
     * Creates a new bot at specified position.
     * 
     * @param startX Initial x-coordinate
     * @param startY Initial y-coordinate
     * @param baseSpeed Movement speed of the bot
     */
    public Bot(int startX, int startY, int baseSpeed) {
        super(startX, startY, baseSpeed);
    }

    /**
     * Decision-making method to be implemented by concrete bot classes.
     * Determines the bot's next move based on game state.
     */
    public abstract void decideNextMove(List<SimpleFood> foods,
                                        int width, int height, List<Snake> allSnakes);

    /**
     * Updates bot state by deciding and executing next move.
     * Combines decision logic with movement execution.
     *
     * @param foods List of available food items
     * @param allSnakes List of all snakes in the game
     */
    public void update(List<SimpleFood> foods, int width, int height, List<Snake> allSnakes) {
        decideNextMove(foods, width, height, allSnakes);
        move();
    }
}
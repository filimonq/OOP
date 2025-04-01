package ru.nsu.fitkulin.model;

import java.util.List;

public abstract class Bot extends Snake {
    public Bot(int startX, int startY, int baseSpeed) {
        super(startX, startY, baseSpeed);
    }

    public abstract void decideNextMove(List<SimpleFood> foods,
                                        int width, int height, List<Snake> allSnakes);

    public void update(List<SimpleFood> foods, int width, int height, List<Snake> allSnakes) {
        decideNextMove(foods, width, height, allSnakes);
        move();
    }
}
package ru.nsu.fitkulin.model;

public class SimpleLevel implements Level {
    private final int baseSpeed;
    private final int foodCount;
    private final int width;
    private final int height;
    private final int winLength;

    public SimpleLevel(int baseSpeed, int foodCount, int width, int height, int winLength) {
        this.baseSpeed = baseSpeed;
        this.foodCount = foodCount;
        this.width = width;
        this.height = height;
        this.winLength = winLength;
    }

    @Override
    public int getBaseSpeed() {
        return baseSpeed;
    }

    @Override
    public int getFoodCount() {
        return foodCount;
    }

    @Override
    public int getWinLength() {
        return winLength;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
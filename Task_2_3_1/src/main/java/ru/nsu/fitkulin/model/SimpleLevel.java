package ru.nsu.fitkulin.model;

public class SimpleLevel implements Level {
    private final int width;
    private final int height;
    private final int winLength;

    public SimpleLevel(int width, int height, int winLength) {
        this.width = width;
        this.height = height;
        this.winLength = winLength;
    }

    @Override
    public int getBaseSpeed() {
        return 300;
    }

    @Override
    public int getFoodCount() {
        return 1;
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
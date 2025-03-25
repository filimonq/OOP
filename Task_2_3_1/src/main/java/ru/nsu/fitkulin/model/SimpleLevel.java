package ru.nsu.fitkulin.model;

public class SimpleLevel implements Level {
    private final int width;
    private final int height;
    private final int winLength; // Условие победы

    public SimpleLevel(int width, int height, int winLength) {
        this.width = width;
        this.height = height;
        this.winLength = winLength;
    }

    @Override
    public int getBaseSpeed() {
        return 10; // Скорость в клетках за обновление
    }

    @Override
    public int getFoodCount() {
        return 1;
    }

    @Override
    public int getWinLength() {
        return winLength;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
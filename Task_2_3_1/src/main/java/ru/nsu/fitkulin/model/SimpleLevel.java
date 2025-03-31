package ru.nsu.fitkulin.model;

/**
 * base level.
 */
public class SimpleLevel implements Level {
    private final int baseSpeed;
    private final int foodCount;
    private final int width;
    private final int height;
    private final int winLength;

    /**
     * constructor.
     *
     * @param baseSpeed speed of the snake on level.
     * @param foodCount amount of the food.
     * @param winLength amount of necessary food for win
     */
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
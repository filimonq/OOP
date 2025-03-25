package ru.nsu.fitkulin.model;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {
    private final int width;
    private final int height;
    private final Snake snake;
    private final List<Food> foods;
    private final Level level;
    private final Random random;
    private int score;
    private boolean gameOver;
    private boolean gameWon;

    public GameBoard(Level level) {
        this.level = level;
        this.width = ((SimpleLevel) level).getWidth();
        this.height = ((SimpleLevel) level).getHeight();
        this.snake = new Snake(width / 2, height / 2, level.getBaseSpeed());
        this.foods = new ArrayList<>();
        this.random = new Random();
        this.score = 0;
        this.gameOver = false;
        this.gameWon = false;
        spawnFood();
    }

    public void update() {
        if (!gameOver && !gameWon) {
            snake.move();

            Point2D head = snake.getHead();
            if (head.getX() < 0 || head.getX() >= width || head.getY() < 0 || head.getY() >= height) {
                gameOver = true;
                return;
            }

            if (snake.checkSelfCollision()) {
                gameOver = true;
                return;
            }

            for (Food food : foods) {
                if (head.equals(food.getPosition())) {
                    snake.grow();
                    score += 10;
                    food.setPosition(generateRandomPosition());
                    if (snake.getLength() >= level.getWinLength()) {
                        gameWon = true;
                    }
                    break;
                }
            }
        }
    }

    private void spawnFood() {
        while (foods.size() < level.getFoodCount()) {
            foods.add(new SimpleFood(generateRandomPosition()));
        }
    }

    private Point2D generateRandomPosition() {
        List<Point2D> snakeBody = snake.getBody();
        Point2D pos;
        do {
            pos = new Point2D(random.nextInt(width), random.nextInt(height));
        } while (snakeBody.contains(pos) || isPositionOccupiedByFood(pos));
        return pos;
    }

    private boolean isPositionOccupiedByFood(Point2D pos) {
        return foods.stream().anyMatch(f -> f.getPosition().equals(pos));
    }

    public Snake getSnake() { return snake; }
    public List<Food> getFoods() { return foods; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon() { return gameWon; }
    public void reset() {
        snake.reset(width / 2, height / 2);
        foods.clear();
        spawnFood();
        score = 0;
        gameOver = false;
        gameWon = false;
    }
}
package ru.nsu.fitkulin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;

/**
 * Represents the game state and manages all game entities.
 * Handles player snake, bots, food, collisions, and game rules.
 */
public class GameBoard {
    private final Snake playerSnake;
    private final List<Bot> bots;
    private final List<SimpleFood> foods;
    private final int width;
    private final int height;
    private final int winLength;
    private final int foodCount;
    private final int initialBotCount;
    private int score;
    private boolean gameOver;
    private boolean gameWon;
    private final Random random;

    /**
     * Creates new game board with specified level configuration.
     * @param level Contains game parameters (size, speed, etc)
     * @param botCount Number of AI opponents (0-2)
     */
    public GameBoard(Level level, int botCount) {
        this.playerSnake = new Snake(level.getWidth() / 2,
                level.getHeight() / 2, level.getBaseSpeed());
        this.bots = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.width = level.getWidth();
        this.height = level.getHeight();
        this.winLength = level.getWinLength();
        this.foodCount = level.getFoodCount();
        this.initialBotCount = botCount;
        this.score = 0;
        this.gameOver = false;
        this.gameWon = false;
        this.random = new Random();

        for (int i = 0; i < botCount; i++) {
            Point2D botPos;
            do {
                botPos = new Point2D(random.nextInt(width), random.nextInt(height));
            } while (playerSnake.getBody().contains(botPos));
            Bot bot = (i % 2 == 1)
                    ? new BotSmart((int) botPos.getX(), (int) botPos.getY(), level.getBaseSpeed())
                    : new BotStupid((int) botPos.getX(), (int) botPos.getY(), level.getBaseSpeed());
            bots.add(bot);
        }

        for (int i = 0; i < foodCount; i++) {
            spawnFood();
        }
    }

    /**
     * Updates game state - moves entities, checks collisions, handles scoring.
     * Called every game tick to progress the game.
     */
    public void update() {
        if (gameOver || gameWon) {
            return;
        }

        playerSnake.move();
        List<Snake> allSnakes = new ArrayList<>();
        allSnakes.add(playerSnake);
        allSnakes.addAll(bots);

        for (Bot bot : bots) {
            bot.update(foods, width, height, allSnakes);
        }

        Point2D playerHead = playerSnake.getHead();
        List<SimpleFood> foodsToRemove = new ArrayList<>();
        for (SimpleFood food : foods) {
            if (playerHead.equals(food.getPosition())) {
                foodsToRemove.add(food);
                playerSnake.grow();
                score += 10;
            }
        }

        foods.removeAll(foodsToRemove);
        for (int i = 0; i < foodsToRemove.size(); i++) {
            spawnFood();
        }

        for (Bot bot : bots) {
            Point2D botHead = bot.getHead();
            foodsToRemove.clear();
            for (SimpleFood food : foods) {
                if (botHead.equals(food.getPosition())) {
                    foodsToRemove.add(food);
                    bot.grow();
                }
            }
            foods.removeAll(foodsToRemove);
            for (int i = 0; i < foodsToRemove.size(); i++) {
                spawnFood();
            }
        }

        if (playerSnake.checkSelfCollision() || playerHead.getX() < 0 || playerHead.getX() >= width
                || playerHead.getY() < 0 || playerHead.getY() >= height
                || checkCollisionWithBots(playerHead)) {
            gameOver = true;
        }

        List<Bot> botsToRemove = new ArrayList<>();
        for (Bot bot : bots) {
            Point2D botHead = bot.getHead();

            for (Bot otherBot : bots) {
                if (bot != otherBot && otherBot.getBody().contains(botHead)) {
                    botsToRemove.add(bot);
                    break;
                }
            }

            if (bot.checkSelfCollision() || botHead.getX() < 0 || botHead.getX() >= width
                    || botHead.getY() < 0 || botHead.getY() >= height
                    || playerSnake.getBody().contains(botHead)) {
                botsToRemove.add(bot);
            }
        }
        bots.removeAll(botsToRemove);

        if (playerSnake.getLength() >= winLength || (initialBotCount > 0 && bots.isEmpty())) {
            gameWon = true;
        }
    }

    /**
     * Spawns new food at random unoccupied position.
     * Ensures food doesn't appear on snakes.
     */
    public void spawnFood() {
        if (foods.size() >= width * height - (playerSnake.getLength()
                + bots.stream().mapToInt(Snake::getLength).sum())) {
            return;
        }
        Point2D position;
        List<Snake> allSnakes = new ArrayList<>();
        allSnakes.add(playerSnake);
        allSnakes.addAll(bots);
        boolean isOccupied;
        do {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            position = new Point2D(x, y);
            isOccupied = false;

            for (Snake snake : allSnakes) {
                if (snake.getBody().contains(position)) {
                    isOccupied = true;
                    break;
                }
            }

            if (!isOccupied) {
                for (SimpleFood food : foods) {
                    if (food.getPosition().equals(position)) {
                        isOccupied = true;
                        break;
                    }
                }
            }
        } while (isOccupied);
        foods.add(new SimpleFood(position));
    }


    private boolean checkCollisionWithBots(Point2D playerHead) {
        for (Bot bot : bots) {
            if (bot.getBody().contains(playerHead)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resets game to initial state.
     */
    public void reset() {
        playerSnake.reset(width / 2, height / 2);
        bots.clear();
        for (int i = 0; i < initialBotCount; i++) {
            Point2D botPos;
            do {
                botPos = new Point2D(random.nextInt(width), random.nextInt(height));
            } while (playerSnake.getBody().contains(botPos));
            Bot bot = (i % 2 == 1)
                    ? new BotSmart((int) botPos.getX(), (int) botPos.getY(), playerSnake.getSpeed())
                    : new BotStupid((int) botPos.getX(),
                    (int) botPos.getY(), playerSnake.getSpeed());
            bots.add(bot);
        }
        foods.clear();
        for (int i = 0; i < foodCount; i++) {
            spawnFood();
        }
        score = 0;
        gameOver = false;
        gameWon = false;
    }

    public Snake getSnake() {
        return playerSnake;
    }

    public List<Bot> getBots() {
        return bots;
    }

    public List<SimpleFood> getFoods() {
        return foods;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
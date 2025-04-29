package ru.nsu.fitkulin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBoardTest {
    private GameBoard gameBoard;
    private final int width = 20;
    private final int height = 15;
    private final int baseSpeed = 10;
    private final int foodCount = 3;
    private final int winLength = 10;
    private final int botCount = 2;

    @BeforeEach
    void setUp() {
        Level level = new SimpleLevel(
                baseSpeed,
                foodCount,
                width,
                height,
                winLength
        );

        gameBoard = new GameBoard(level, botCount);
    }

    @Test
    void testGameInitialization() {
        Snake player = gameBoard.getSnake();
        assertEquals(new Point2D(width / 2, height / 2), player.getHead());
        assertEquals(Direction.RIGHT, player.getDirection());
        assertEquals(1, player.getLength());

        List<Bot> bots = gameBoard.getBots();
        assertEquals(botCount, bots.size());
        bots.forEach(bot -> {
            assertTrue(bot.getHead().getX() >= 0 && bot.getHead().getX() < width);
            assertTrue(bot.getHead().getY() >= 0 && bot.getHead().getY() < height);
            assertNotEquals(player.getHead(), bot.getHead());
        });

        assertEquals(foodCount, gameBoard.getFoods().size());
    }

    @Test
    void testPlayerMovement() {
        Point2D initialPosition = gameBoard.getSnake().getHead();
        gameBoard.update();
        Point2D newPosition = gameBoard.getSnake().getHead();

        assertEquals(initialPosition.getX() + 1, newPosition.getX());
        assertEquals(initialPosition.getY(), newPosition.getY());
    }

    @Test
    void testWallCollision() {
        for (int i = 0; i < width; i++) {
            gameBoard.update();
        }

        assertTrue(gameBoard.isGameOver());
    }

    @Test
    void testGameReset() {
        gameBoard.update();
        gameBoard.update();
        gameBoard.reset();

        assertEquals(new Point2D(width / 2, height / 2), gameBoard.getSnake().getHead());
        assertEquals(botCount, gameBoard.getBots().size());
        assertEquals(foodCount, gameBoard.getFoods().size());
        assertEquals(0, gameBoard.getScore());
        assertFalse(gameBoard.isGameOver());
        assertFalse(gameBoard.isGameWon());
    }

    @Test
    void testBotElimination() {
        gameBoard.getBots().forEach(bot ->
                bot.reset(-1, -1)
        );

        gameBoard.update();

        assertTrue(gameBoard.getBots().isEmpty());
        assertTrue(gameBoard.isGameWon());
    }

    @Test
    void testFoodSpawnMechanics() {
        gameBoard.getFoods().clear();
        gameBoard.spawnFood();

        Point2D foodPosition = gameBoard.getFoods().get(0).getPosition();
        assertFalse(isPositionOccupied(foodPosition));
    }

    private boolean isPositionOccupied(Point2D position) {
        if (gameBoard.getSnake().getBody().contains(position)) {
            return true;
        }

        for (Bot bot : gameBoard.getBots()) {
            if (bot.getBody().contains(position)) {
                return true;
            }
        }

        return false;
    }
}
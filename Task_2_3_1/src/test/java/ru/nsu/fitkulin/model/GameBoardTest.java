package ru.nsu.fitkulin.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameBoardIntegrationTest {
    private GameBoard gameBoard;
    private final int WIDTH = 20;
    private final int HEIGHT = 15;
    private final int BASE_SPEED = 10;
    private final int FOOD_COUNT = 3;
    private final int WIN_LENGTH = 10;
    private final int BOT_COUNT = 2;

    @BeforeEach
    void setUp() {
        Level level = new SimpleLevel(
                BASE_SPEED,
                FOOD_COUNT,
                WIDTH,
                HEIGHT,
                WIN_LENGTH
        );

        gameBoard = new GameBoard(level, BOT_COUNT);
    }

    @Test
    void testGameInitialization() {
        Snake player = gameBoard.getSnake();
        assertEquals(new Point2D(WIDTH/2, HEIGHT/2), player.getHead());
        assertEquals(Direction.RIGHT, player.getDirection());
        assertEquals(1, player.getLength());

        List<Bot> bots = gameBoard.getBots();
        assertEquals(BOT_COUNT, bots.size());
        bots.forEach(bot -> {
            assertTrue(bot.getHead().getX() >= 0 && bot.getHead().getX() < WIDTH);
            assertTrue(bot.getHead().getY() >= 0 && bot.getHead().getY() < HEIGHT);
            assertNotEquals(player.getHead(), bot.getHead());
        });

        assertEquals(FOOD_COUNT, gameBoard.getFoods().size());
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
        for(int i = 0; i < WIDTH; i++) {
            gameBoard.update();
        }

        assertTrue(gameBoard.isGameOver());
    }

    @Test
    void testGameReset() {
        gameBoard.update();
        gameBoard.update();
        gameBoard.reset();

        assertEquals(new Point2D(WIDTH/2, HEIGHT/2), gameBoard.getSnake().getHead());
        assertEquals(BOT_COUNT, gameBoard.getBots().size());
        assertEquals(FOOD_COUNT, gameBoard.getFoods().size());
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
        if(gameBoard.getSnake().getBody().contains(position)) return true;

        for(Bot bot : gameBoard.getBots()) {
            if(bot.getBody().contains(position)) return true;
        }

        return false;
    }
}
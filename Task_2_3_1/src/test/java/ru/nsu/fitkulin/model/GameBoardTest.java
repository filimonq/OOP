package ru.nsu.fitkulin.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardTest {
    private GameBoard gameBoard;
    private SimpleLevel level;

    @BeforeEach
    public void setUp() {
        level = new SimpleLevel(5, 1, 10, 10, 3);
        gameBoard = new GameBoard(level);
    }

    @Test
    public void testInitialSetup() {
        assertEquals(1, gameBoard.getFoods().size());
        assertEquals(0, gameBoard.getScore());
        assertFalse(gameBoard.isGameOver());
        assertFalse(gameBoard.isGameWon());
    }

    @Test
    public void testEatFood() {
        gameBoard.getFoods().get(0).setPosition(new Point2D(6, 5));
        gameBoard.update();
        assertEquals(10, gameBoard.getScore());
    }

    @Test
    public void testWallCollision() {
        for (int i = 0; i < 5; i++) {
            gameBoard.update();
        }
        assertTrue(gameBoard.isGameOver());
    }

    @Test
    public void testReset() {
        gameBoard.getFoods().get(0).setPosition(new Point2D(6, 5));
        gameBoard.update();
        gameBoard.reset();
        assertEquals(1, gameBoard.getSnake().getLength());
        assertEquals(0, gameBoard.getScore());
    }
}
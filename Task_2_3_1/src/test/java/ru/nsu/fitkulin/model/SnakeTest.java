package ru.nsu.fitkulin.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    private Snake snake;

    @BeforeEach
    public void setUp() {
        snake = new Snake(5, 5, 10);
    }

    @Test
    public void testInitialPosition() {
        Point2D head = snake.getHead();
        assertEquals(new Point2D(5, 5), head);
    }

    @Test
    public void testMoveRight() {
        snake.move();
        Point2D head = snake.getHead();
        assertEquals(new Point2D(6, 5), head);
        assertEquals(1, snake.getBody().size());
    }

    @Test
    public void testGrow() {
        snake.grow();
        snake.move();
        assertEquals(2, snake.getBody().size());
        assertEquals(new Point2D(6, 5), snake.getHead());
    }

    @Test
    public void testSelfCollision() {
        snake.move();
        snake.updateDirection(Direction.DOWN);
        snake.move();
        snake.updateDirection(Direction.LEFT);
        snake.move();
        snake.updateDirection(Direction.UP);
        snake.move();
        assertFalse(snake.checkSelfCollision());
    }

    @Test
    public void testDirectionChange() {
        snake.updateDirection(Direction.DOWN);
        snake.move();
        Point2D head = snake.getHead();
        assertEquals(new Point2D(5, 6), head);
    }
}
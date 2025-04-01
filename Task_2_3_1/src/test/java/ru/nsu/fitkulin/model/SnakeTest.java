package ru.nsu.fitkulin.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import javafx.geometry.Point2D;

public class SnakeTest {
    private Snake snake;

    @BeforeEach
    public void setUp() {
        snake = new Snake(5, 5, 10);
    }

    @Test
    public void testConstructor() {
        assertEquals(new Point2D(5, 5), snake.getHead());
        assertEquals(Direction.RIGHT, snake.getDirection());
        assertEquals(1, snake.getLength());
        assertEquals(10, snake.getSpeed());
    }

    @Test
    public void testUpdateDirectionOpposite() {
        snake.updateDirection(Direction.LEFT);
        assertEquals(Direction.RIGHT, snake.getDirection());
        snake.move();
        assertEquals(new Point2D(6, 5), snake.getHead());
    }

    @Test
    public void testMoveWithoutGrowth() {
        snake.move();
        assertEquals(new Point2D(6, 5), snake.getHead());
        assertEquals(1, snake.getLength());
    }

    @Test
    public void testMoveWithGrowth() {
        snake.grow();
        snake.move();
        assertEquals(new Point2D(6, 5), snake.getHead());
        assertEquals(2, snake.getLength());
        LinkedList<Point2D> body = snake.getBody();
        assertEquals(new Point2D(5, 5), body.getLast());
    }

    @Test
    public void testNoSelfCollision() {
        snake.grow();
        snake.move();
        assertFalse(snake.checkSelfCollision());
    }

    @Test
    public void testReset() {
        snake.grow();
        snake.move();
        snake.reset(3, 3);
        assertEquals(new Point2D(3, 3), snake.getHead());
        assertEquals(1, snake.getLength());
        assertEquals(Direction.RIGHT, snake.getDirection());
        assertEquals(10, snake.getSpeed());
    }

    @Test
    public void testChangeSpeed() {
        snake.changeSpeed(2.0);
        assertEquals(20, snake.getSpeed());
        snake.changeSpeed(0.5);
        assertEquals(5, snake.getSpeed());
    }

    @Test
    public void testShrink() {
        snake.grow();
        snake.move();
        assertEquals(2, snake.getLength());
        snake.shrink();
        assertEquals(1, snake.getLength());
    }

    @Test
    public void testShrinkWhenLengthOne() {
        assertEquals(1, snake.getLength());
        snake.shrink();
        assertEquals(1, snake.getLength());
    }

    @Test
    public void testGetBodyCopy() {
        snake.grow();
        snake.move();
        LinkedList<Point2D> bodyCopy = snake.getBody();
        assertEquals(2, bodyCopy.size());
        bodyCopy.clear();
        assertEquals(2, snake.getLength());
    }
}
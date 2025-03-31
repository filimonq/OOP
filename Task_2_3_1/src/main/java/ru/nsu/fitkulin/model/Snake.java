package ru.nsu.fitkulin.model;

import java.util.LinkedList;
import javafx.geometry.Point2D;

/**
 * logic of snake.
 */
public class Snake {
    private final LinkedList<Point2D> body;
    private Direction currentDirection;
    private Direction nextDirection;
    private boolean growNextMove;
    private int speed;
    private final int baseSpeed;

    public Snake(int startX, int startY, int baseSpeed) {
        this.body = new LinkedList<>();
        this.body.add(new Point2D(startX, startY));
        this.currentDirection = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;
        this.growNextMove = false;
        this.baseSpeed = baseSpeed;
        this.speed = baseSpeed;
    }

    public void updateDirection(Direction newDirection) {
        if (!isOppositeDirection(newDirection)) {
            this.nextDirection = newDirection;
        }
    }

    private boolean isOppositeDirection(Direction direction) {
        return (currentDirection == Direction.UP && direction == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && direction == Direction.UP) ||
                (currentDirection == Direction.LEFT && direction == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && direction == Direction.LEFT);
    }

    public void move() {
        currentDirection = nextDirection;
        Point2D newHead = getNextHeadPosition();
        body.addFirst(newHead);
        if (!growNextMove) {
            body.removeLast();
        } else {
            growNextMove = false;
        }
    }

    private Point2D getNextHeadPosition() {
        Point2D head = body.getFirst();
        double x = head.getX();
        double y = head.getY();

        switch (currentDirection) {
            case UP:    y--; break;
            case DOWN:  y++; break;
            case LEFT:  x--; break;
            case RIGHT: x++; break;
        }

        return new Point2D(x, y);
    }

    public void grow() {
        this.growNextMove = true;
    }

    public boolean checkSelfCollision() {
        Point2D head = getHead();
        return body.stream()
                .skip(1)
                .anyMatch(p -> p.equals(head));
    }

    public void reset(int startX, int startY) {
        body.clear();
        body.add(new Point2D(startX, startY));
        currentDirection = Direction.RIGHT;
        nextDirection = Direction.RIGHT;
        speed = baseSpeed;
    }

    public Point2D getHead() { return body.getFirst(); }
    public LinkedList<Point2D> getBody() { return new LinkedList<>(body); }
    public Direction getDirection() { return currentDirection; }
    public int getSpeed() { return speed; }
    public int getLength() { return body.size(); }

    public void changeSpeed(double multiplier) {
        speed = (int)(baseSpeed * multiplier);
    }

    public void shrink() {
        if (body.size() > 1) {
            body.removeLast();
        }
    }
}
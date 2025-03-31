package ru.nsu.fitkulin.model;

import javafx.geometry.Point2D;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class BotStupid extends Bot {
    private final Random random;

    public BotStupid(int startX, int startY, int baseSpeed) {
        super(startX, startY, baseSpeed);
        this.random = new Random();
    }

    @Override
    public void decideNextMove(List<SimpleFood> foods, int width, int height, List<Snake> allSnakes) {
        Direction currentDir = getDirection();
        List<Direction> possibleDirections = getPossibleDirections(currentDir);
        Collections.shuffle(possibleDirections, random);

        Direction newDirection = currentDir;
        boolean foundSafeMove = false;

        for (Direction dir : possibleDirections) {
            Point2D nextPos = getNextPosition(dir);
            if (!isWallCollision(nextPos, width, height)) {
                newDirection = dir;
                foundSafeMove = true;
                break;
            }
        }

        updateDirection(newDirection);
    }

    private List<Direction> getPossibleDirections(Direction current) {
        List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
        directions.remove(current.getOpposite());
        return directions;
    }

    private Point2D getNextPosition(Direction direction) {
        Point2D head = getHead();
        switch (direction) {
            case UP:    return new Point2D(head.getX(), head.getY() - 1);
            case DOWN:  return new Point2D(head.getX(), head.getY() + 1);
            case LEFT:  return new Point2D(head.getX() - 1, head.getY());
            case RIGHT: return new Point2D(head.getX() + 1, head.getY());
            default:    return head;
        }
    }

    private boolean isWallCollision(Point2D nextPos, int width, int height) {
        return nextPos.getX() < 0 || nextPos.getX() >= width
                || nextPos.getY() < 0 || nextPos.getY() >= height;
    }
}
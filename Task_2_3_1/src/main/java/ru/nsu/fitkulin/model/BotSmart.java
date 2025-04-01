package ru.nsu.fitkulin.model;

import java.util.List;
import javafx.geometry.Point2D;

public class BotSmart extends Bot {
    public BotSmart(int startX, int startY, int baseSpeed) {
        super(startX, startY, baseSpeed);
    }

    @Override
    public void decideNextMove(List<SimpleFood> foods,
                               int width, int height, List<Snake> allSnakes) {
        if (foods.isEmpty()){
            return;
        }

        Point2D head = getHead();
        SimpleFood target = findNearestFood(foods, head);

        double dx = target.getPosition().getX() - head.getX();
        double dy = target.getPosition().getY() - head.getY();

        Direction newDirection;
        if (Math.abs(dx) > Math.abs(dy)) {
            newDirection = dx > 0 && head.getX() < width - 1 ? Direction.RIGHT : Direction.LEFT;
        } else {
            newDirection = dy > 0 && head.getY() < height - 1 ? Direction.DOWN : Direction.UP;
        }

        Point2D nextPos = getNextPosition(newDirection);
        if (!isSafeMove(nextPos, allSnakes)) {
            for (Direction dir : Direction.values()) {
                nextPos = getNextPosition(dir);
                if (isSafeMove(nextPos, allSnakes) && !isWallCollision(nextPos, width, height)) {
                    newDirection = dir;
                    break;
                }
            }
        }

        updateDirection(newDirection);
    }

    private SimpleFood findNearestFood(List<SimpleFood> foods, Point2D head) {
        SimpleFood nearest = foods.get(0);
        double minDistance = head.distance(nearest.getPosition());
        for (SimpleFood food : foods) {
            double distance = head.distance(food.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = food;
            }
        }
        return nearest;
    }

    private Point2D getNextPosition(Direction direction) {
        Point2D head = getHead();
        switch (direction) {
            case UP: return new Point2D(head.getX(), head.getY() - 1);
            case DOWN: return new Point2D(head.getX(), head.getY() + 1);
            case LEFT: return new Point2D(head.getX() - 1, head.getY());
            case RIGHT: return new Point2D(head.getX() + 1, head.getY());
            default: return head;
        }
    }

    private boolean isSafeMove(Point2D nextPos, List<Snake> allSnakes) {
        for (Snake snake : allSnakes) {
            if (snake.getBody().contains(nextPos)) {
                return false;
            }
        }
        return true;
    }

    private boolean isWallCollision(Point2D nextPos, int width, int height) {
        return nextPos.getX() < 0 || nextPos.getX() >= width
                || nextPos.getY() < 0 || nextPos.getY() >= height;
    }
}
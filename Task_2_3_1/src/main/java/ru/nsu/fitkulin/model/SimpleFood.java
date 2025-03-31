package ru.nsu.fitkulin.model;

import javafx.geometry.Point2D;

public class SimpleFood implements Food {
    private Point2D position;

    public SimpleFood(Point2D position) {
        this.position = position;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
}
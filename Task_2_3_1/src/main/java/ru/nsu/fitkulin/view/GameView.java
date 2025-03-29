package ru.nsu.fitkulin.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.nsu.fitkulin.model.GameBoard;

public class GameView {
    private final Canvas canvas;
    private final int cellSize = 20;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
    }

    public void render(GameBoard gameBoard) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = gameBoard.getWidth() * cellSize;
        double height = gameBoard.getHeight() * cellSize;

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        gc.setFill(Color.GREEN);
        for (var point : gameBoard.getSnake().getBody()) {
            gc.fillRect(point.getX() * cellSize, point.getY() * cellSize, cellSize - 1, cellSize - 1);
        }

        gc.setFill(Color.RED);
        for (var food : gameBoard.getFoods()) {
            gc.fillRect(food.getPosition().getX() * cellSize, food.getPosition().getY() * cellSize, cellSize - 1, cellSize - 1);
        }

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score: " + gameBoard.getScore(), 10, 20);

        if (gameBoard.isGameOver()) {
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 30));
            gc.fillText("Game Over! Press R", width / 4, height / 2);
        } else if (gameBoard.isGameWon()) {
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 30));
            gc.fillText("You Won! Press R", width / 4, height / 2);
        }
    }
}
package ru.nsu.fitkulin.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import ru.nsu.fitkulin.model.GameBoard;
import ru.nsu.fitkulin.model.Snake;

public class GameView {
    private final Canvas canvas;
    private final int cellSize = 30;
    private final Image backgroundImage;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
        this.backgroundImage = new Image(getClass().getResourceAsStream("/back.png"));
    }

    public void render(GameBoard gameBoard) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = gameBoard.getWidth() * cellSize;
        double height = gameBoard.getHeight() * cellSize;

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.drawImage(backgroundImage, 0, 0, width, height);

        Snake snake = gameBoard.getSnake();
        for (int i = 0; i < snake.getBody().size(); i++) {
            var point = snake.getBody().get(i);
            if (i == 0) {
                gc.setFill(Color.LIME);
            } else {
                gc.setFill(Color.GREEN);
            }
            gc.fillRect(point.getX() * cellSize, point.getY() * cellSize, cellSize - 1, cellSize - 1);
        }

        gc.setFill(Color.RED);
        for (var food : gameBoard.getFoods()) {
            gc.fillRect(food.getPosition().getX() * cellSize,
                    food.getPosition().getY() * cellSize, cellSize - 1, cellSize - 1);
        }

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Score: " + gameBoard.getScore(), 10, 20);

        if (gameBoard.isGameOver()) {
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 30));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("Game Over! Press R", width / 2, height / 2);
        } else if (gameBoard.isGameWon()) {
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 30));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("You Won! Press R", width / 2, height / 2);
        }
    }

    public int getCellSize() {
        return cellSize;
    }
}
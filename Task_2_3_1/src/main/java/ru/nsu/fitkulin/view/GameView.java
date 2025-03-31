package ru.nsu.fitkulin.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import ru.nsu.fitkulin.model.GameBoard;
import ru.nsu.fitkulin.model.Snake;

public class GameView {
    private final Canvas canvas;
    private final int cellSize = 30;
    private final Image backgroundImage;
    private final Image snakeHeadImage;
    private final Image foodImage;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
        this.backgroundImage = new Image(getClass().getResourceAsStream("/back.png"));
        this.snakeHeadImage = new Image(getClass().getResourceAsStream("/head.png"));
        this.foodImage = new Image(getClass().getResourceAsStream("/simple_food.png"));
    }

    public void render(GameBoard gameBoard) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = gameBoard.getWidth() * cellSize;
        double height = gameBoard.getHeight() * cellSize;

        gc.drawImage(backgroundImage, 0, 0, width, height);

        LinearGradient snakeGradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.LIME), new Stop(1, Color.DARKGREEN));

        Snake snake = gameBoard.getSnake();
        for (int i = 0; i < snake.getBody().size(); i++) {
            var point = snake.getBody().get(i);
            if (i == 0) {
                gc.drawImage(snakeHeadImage, point.getX() * cellSize, point.getY() * cellSize,
                        cellSize, cellSize);
            } else {
                gc.setFill(snakeGradient);
                gc.fillRoundRect(point.getX() * cellSize, point.getY() * cellSize,
                        cellSize - 1, cellSize - 1, 10, 10);
            }
        }

        for (var food : gameBoard.getFoods()) {
            gc.drawImage(foodImage, food.getPosition().getX() * cellSize,
                    food.getPosition().getY() * cellSize, cellSize, cellSize);
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
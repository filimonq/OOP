package ru.nsu.fitkulin.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import ru.nsu.fitkulin.model.Bot;
import ru.nsu.fitkulin.model.GameBoard;
import ru.nsu.fitkulin.model.Snake;

/**
 * Handles visual representation of game state using JavaFX Canvas.
 * Renders snakes, bots, food, UI elements and sound effects.
 */
public class GameView {
    private final Canvas canvas;
    private final int cellSize = 30;
    private final Image backgroundImage;
    private final Image snakeHeadImage;
    private final Image foodImage;
    private final AudioClip gameOverSound;
    private final AudioClip winSound;
    private boolean gameOverPlayed;
    private boolean winPlayed;


    /**
     * Constructs.
     *
     * @param canvas JavaFX Canvas for drawing game elements.
     */

    public GameView(Canvas canvas) {
        this.canvas = canvas;
        this.backgroundImage = new Image(getClass().getResourceAsStream("/images/back.png"));
        this.snakeHeadImage = new Image(getClass().getResourceAsStream("/images/head.png"));
        this.foodImage = new Image(getClass().getResourceAsStream("/images/simple_food.png"));
        this.gameOverSound =
                new AudioClip(getClass().getResource("/sounds/game_over.wav").toString());
        this.winSound = new AudioClip(getClass().getResource("/sounds/win.wav").toString());
        this.gameOverPlayed = false;
        this.winPlayed = false;
    }

    /**
     * Renders current game state including:
     * - Player snake with gradient coloring
     * - Enemy bots with distinct visual style
     * - Food items with texture
     * - Score display
     * - Win/lose conditions overlay
     *
     * @param gameBoard Current game state to visualize
     */
    public void render(GameBoard gameBoard) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = gameBoard.getWidth() * cellSize;
        double height = gameBoard.getHeight() * cellSize;

        gc.drawImage(backgroundImage, 0, 0, width, height);

        LinearGradient playerGradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.LIME), new Stop(1, Color.DARKGREEN));
        Snake playerSnake = gameBoard.getSnake();
        for (int i = 0; i < playerSnake.getBody().size(); i++) {
            var point = playerSnake.getBody().get(i);
            if (i == 0) {
                gc.drawImage(snakeHeadImage,
                        point.getX() * cellSize, point.getY() * cellSize, cellSize, cellSize);
            } else {
                gc.setFill(playerGradient);
                gc.fillRoundRect(point.getX() * cellSize, point.getY() * cellSize,
                        cellSize - 1, cellSize - 1, 10, 10);
            }
        }

        LinearGradient botGradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED), new Stop(1, Color.DARKRED));
        for (Bot bot : gameBoard.getBots()) {
            for (int i = 0; i < bot.getBody().size(); i++) {
                var point = bot.getBody().get(i);
                if (i == 0) {
                    gc.setFill(Color.RED);
                    gc.fillOval(point.getX() * cellSize,
                            point.getY() * cellSize, cellSize, cellSize);
                } else {
                    gc.setFill(botGradient);
                    gc.fillRoundRect(point.getX() * cellSize, point.getY() * cellSize,
                            cellSize - 1, cellSize - 1, 10, 10);
                }
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
            if (!gameOverPlayed) {
                gameOverSound.play();
                gameOverPlayed = true;
            }
        } else if (gameBoard.isGameWon()) {
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 30));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("You Won! Press R", width / 2, height / 2);
            if (!winPlayed) {
                winSound.play();
                winPlayed = true;
            }
        } else {
            gameOverPlayed = false;
            winPlayed = false;
        }
    }

    public int getCellSize() {
        return cellSize;
    }
}
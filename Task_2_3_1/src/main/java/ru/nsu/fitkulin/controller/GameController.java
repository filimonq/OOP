package ru.nsu.fitkulin.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import ru.nsu.fitkulin.model.Direction;
import ru.nsu.fitkulin.model.GameBoard;
import ru.nsu.fitkulin.model.Level;
import ru.nsu.fitkulin.view.GameView;

/**
 * Main game controller handling business logic and user input.
 * Manages game loop, coordinates model updates and view rendering.
 */
public class GameController {
    @FXML
    private Canvas gameCanvas;

    private GameBoard gameBoard;
    private GameView gameView;
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;
    private long updateInterval;
    private final int cellSize = 30;
    private javafx.stage.Stage stage;

    private Level level;

    public void setStage(javafx.stage.Stage stage) {
        this.stage = stage;
    }

    public void setLevel(Level level, int botCount) {
        this.level = level;
        initializeGame(botCount);
    }

    /**
     * Initializes core game components and starts the game loop.
     * Creates a new GameBoard with the selected level and bot count, configures the game view,
     * sets up canvas dimensions based on level size, and starts the animation timer that drives
     * the game updates. The game loop runs at a speed determined by the snake's base speed.
     *
     * @param botCount The number of AI opponents to create (0-2). This determines how many
     *                 Bot instances will be added to the game board. Each bot will have its
     *                 own independent behavior and compete with the player.
     */
    private void initializeGame(int botCount) {
        gameBoard = new GameBoard(level, botCount);
        gameView = new GameView(gameCanvas);

        int speed = gameBoard.getSnake().getSpeed();
        updateInterval = 1_000_000_000 / speed;

        gameCanvas.setWidth(gameBoard.getWidth() * cellSize);
        gameCanvas.setHeight(gameBoard.getHeight() * cellSize);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= updateInterval) {
                    gameBoard.update();
                    gameView.render(gameBoard);
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Handles keyboard input for snake control and game restart.
     *
     * @param event Key press event from JavaFX scene.
     */
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                gameBoard.getSnake().updateDirection(Direction.UP);
                break;
            case S:
                gameBoard.getSnake().updateDirection(Direction.DOWN);
                break;
            case A:
                gameBoard.getSnake().updateDirection(Direction.LEFT);
                break;
            case D:
                gameBoard.getSnake().updateDirection(Direction.RIGHT);
                break;
            case R:
                if (gameBoard.isGameOver() || gameBoard.isGameWon()) {
                    gameBoard.reset();
                }
                break;
            default: break;
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
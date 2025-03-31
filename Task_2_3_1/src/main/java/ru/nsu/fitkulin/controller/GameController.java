package ru.nsu.fitkulin.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import ru.nsu.fitkulin.model.Direction;
import ru.nsu.fitkulin.model.GameBoard;
import ru.nsu.fitkulin.model.Level;
import ru.nsu.fitkulin.view.GameView;

public class GameController {
    @FXML
    private Canvas gameCanvas;

    private GameBoard gameBoard;
    private GameView gameView;
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;
    private long updateInterval;
    private final int cellSize = 30;

    private Level level;

    public void setLevel(Level level) {
        this.level = level;
        initializeGame();
    }

    private void initializeGame() {
        gameBoard = new GameBoard(level);
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

    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W: gameBoard.getSnake().updateDirection(Direction.UP); break;
            case S: gameBoard.getSnake().updateDirection(Direction.DOWN); break;
            case A: gameBoard.getSnake().updateDirection(Direction.LEFT); break;
            case D: gameBoard.getSnake().updateDirection(Direction.RIGHT); break;
            case R: if (gameBoard.isGameOver() || gameBoard.isGameWon()) gameBoard.reset(); break;
        }
    }
}
package ru.nsu.fitkulin.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import ru.nsu.fitkulin.model.Direction;
import ru.nsu.fitkulin.model.GameBoard;
import ru.nsu.fitkulin.model.SimpleLevel;
import ru.nsu.fitkulin.view.GameView;

public class GameController {
    @FXML
    private Canvas gameCanvas;

    private GameBoard gameBoard;
    private GameView gameView;
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;
    private final int updateInterval = 200_000_000; // 200мс

    @FXML
    public void initialize() {
        gameBoard = new GameBoard(new SimpleLevel(20, 20, 10));
        gameView = new GameView(gameCanvas);

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
            case UP:    gameBoard.getSnake().updateDirection(Direction.UP); break;
            case DOWN:  gameBoard.getSnake().updateDirection(Direction.DOWN); break;
            case LEFT:  gameBoard.getSnake().updateDirection(Direction.LEFT); break;
            case RIGHT: gameBoard.getSnake().updateDirection(Direction.RIGHT); break;
            case R:     if (gameBoard.isGameOver() || gameBoard.isGameWon()) gameBoard.reset(); break;
        }
    }
}
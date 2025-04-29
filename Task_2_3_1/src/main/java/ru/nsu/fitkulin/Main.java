package ru.nsu.fitkulin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.fitkulin.controller.GameController;
import ru.nsu.fitkulin.view.LevelSelector;

/**
 * Main application class for Snake game with bots.
 * Initializes JavaFX components and manages application lifecycle.
 */
public class Main extends Application {
    private GameController controller;

    /**
     * JavaFX application entry point. Configures level selection UI,
     * initializes game controller and sets up primary stage.
     *
     * @param primaryStage Main application window.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        LevelSelector levelSelector = new LevelSelector();
        levelSelector.selectLevelAndBots();

        var level = levelSelector.getSelectedLevel();
        int botCount = levelSelector.getSelectedBotCount();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setLevel(level, botCount);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(controller::handleKeyPress);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake with Bots");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public GameController getController() {
        return controller;
    }

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
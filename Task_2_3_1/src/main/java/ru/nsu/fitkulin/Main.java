package ru.nsu.fitkulin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.fitkulin.controller.GameController;
import ru.nsu.fitkulin.view.LevelSelector;

public class Main extends Application {
    private GameController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        LevelSelector levelSelector = new LevelSelector();
        levelSelector.selectLevelAndBots();

        var level = levelSelector.getSelectedLevel();
        int botCount = levelSelector.getSelectedBotCount();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setLevel(level, botCount);


        scene.setOnKeyPressed(controller::handleKeyPress);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake with Bots");
        primaryStage.show();
    }

    public GameController getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
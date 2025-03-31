package ru.nsu.fitkulin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.fitkulin.controller.GameController;
import ru.nsu.fitkulin.model.Level;
import ru.nsu.fitkulin.view.LevelSelector;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LevelSelector levelSelector = new LevelSelector();
        Level selectedLevel = levelSelector.selectLevel();

        if (selectedLevel != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
            Parent root = loader.load();
            GameController controller = loader.getController();
            controller.setLevel(selectedLevel);

            Scene scene = new Scene(root);
            scene.setOnKeyPressed(controller::handleKeyPress);
            primaryStage.setTitle("Snake Game");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.sizeToScene();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
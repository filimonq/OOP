package ru.nsu.fitkulin.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nsu.fitkulin.model.Level;
import ru.nsu.fitkulin.model.SimpleLevel;

public class LevelSelector {
    private Level selectedLevel;

    public LevelSelector() {
        selectedLevel = null;
    }

    public Level selectLevel() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Выбор уровня");

        VBox vbox = new VBox(10);
        Button level1Button = new Button("Уровень 1: 15x15, победа при 10");
        Button level2Button = new Button("Уровень 2: 20x20, победа при 20");

        level1Button.setOnAction(e -> {
            selectedLevel = new SimpleLevel(0, 3,15, 15, 10);
            stage.close();
        });

        level2Button.setOnAction(e -> {
            selectedLevel = new SimpleLevel(0, 10, 20, 20, 20);
            stage.close();
        });

        vbox.getChildren().addAll(level1Button, level2Button);
        Scene scene = new Scene(vbox, 250, 100);
        stage.setScene(scene);
        stage.showAndWait();

        return selectedLevel;
    }
}
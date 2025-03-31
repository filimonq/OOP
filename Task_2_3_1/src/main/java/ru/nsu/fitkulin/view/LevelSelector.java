package ru.nsu.fitkulin.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nsu.fitkulin.model.Level;
import ru.nsu.fitkulin.model.SimpleLevel;
import javafx.scene.text.Font;

public class LevelSelector {
    private Level selectedLevel;
    private int selectedBotCount;

    public LevelSelector() {
        selectedLevel = null;
        selectedBotCount = 0;
    }

    public Level getSelectedLevel() {
        return selectedLevel;
    }

    public int getSelectedBotCount() {
        return selectedBotCount;
    }

    public void selectLevelAndBots() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select Level and Bots");

        VBox vbox = new VBox(10);

        Button level1Button = new Button("Level 1");
        Button level2Button = new Button("Level 2");
        Button level3Button = new Button("Level 3");

        ComboBox<Integer> botCountCombo = new ComboBox<>();
        botCountCombo.getItems().addAll(0, 1, 2);
        botCountCombo.setValue(0);
        botCountCombo.setPromptText("Select number of bots");

        level1Button.setPrefWidth(300);
        level1Button.setPrefHeight(100);
        level2Button.setPrefWidth(300);
        level2Button.setPrefHeight(100);
        level3Button.setPrefWidth(300);
        level3Button.setPrefHeight(100);
        botCountCombo.setPrefWidth(300);

        Font font = new Font("Arial", 18);
        level1Button.setFont(font);
        level2Button.setFont(font);
        level3Button.setFont(font);

        level1Button.setOnAction(e -> {
            selectedLevel = new SimpleLevel(5, 3, 30, 15, 10);
            selectedBotCount = botCountCombo.getValue();
            stage.close();
        });

        level2Button.setOnAction(e -> {
            selectedLevel = new SimpleLevel(12, 10, 25, 20, 30);
            selectedBotCount = botCountCombo.getValue();
            stage.close();
        });

        level3Button.setOnAction(e -> {
            selectedLevel = new SimpleLevel(30, 1000, 50, 30, 100);
            selectedBotCount = botCountCombo.getValue();
            stage.close();
        });

        vbox.getChildren().addAll(level1Button, level2Button, level3Button, botCountCombo);
        Scene scene = new Scene(vbox, 300, 350);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
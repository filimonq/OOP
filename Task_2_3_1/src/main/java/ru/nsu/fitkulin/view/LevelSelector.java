package ru.nsu.fitkulin.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nsu.fitkulin.model.Level;
import ru.nsu.fitkulin.model.SimpleLevel;

/**
 * Provides UI for selecting game level configuration and number of bots.
 * Uses JavaFX components for user interaction.
 */
public class LevelSelector {
    private Level selectedLevel;
    private int selectedBotCount;

    public LevelSelector() {
        selectedLevel = null;
        selectedBotCount = 0;
    }

    /**
     * Creates modal dialog for level and bot selection.
     * Contains predefined level configurations and bot count options.
     */
    public void selectLevelAndBots() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select Level and Bots");

        Image logoImage = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(1000);
        logoView.setPreserveRatio(true);

        Button level1Button = new Button("Level 1");
        level1Button.setPrefWidth(1000);
        level1Button.setPrefHeight(100);
        Button level2Button = new Button("Level 2");
        level2Button.setPrefWidth(1000);
        level2Button.setPrefHeight(100);
        Button level3Button = new Button("Level 3");
        level3Button.setPrefWidth(1000);
        level3Button.setPrefHeight(100);

        ComboBox<Integer> botCountCombo = new ComboBox<>();
        botCountCombo.getItems().addAll(0, 1, 2);
        botCountCombo.setValue(1);
        botCountCombo.setPrefWidth(1000);
        botCountCombo.setPrefHeight(100);

        Font font = new Font("Arial", 18);
        level1Button.setFont(font);
        level2Button.setFont(font);
        level3Button.setFont(font);

        level1Button.setOnAction(e -> {
            selectedLevel = new SimpleLevel(5, 3, 10, 10, 10);
            selectedBotCount = botCountCombo.getValue();
            stage.close();
        });

        level2Button.setOnAction(e -> {
            selectedLevel = new SimpleLevel(12, 10, 25, 20, 30);
            selectedBotCount = botCountCombo.getValue();
            stage.close();
        });

        level3Button.setOnAction(e -> {
            selectedLevel = new SimpleLevel(30, 100, 50, 30, 100);
            selectedBotCount = botCountCombo.getValue();
            stage.close();
        });

        VBox vbox = new VBox(10);

        vbox.getChildren().addAll(logoView, level1Button, level2Button, level3Button, botCountCombo);
        Scene scene = new Scene(vbox, 1000, 1000);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public Level getSelectedLevel() {
        return selectedLevel;
    }

    public int getSelectedBotCount() {
        return selectedBotCount;
    }
}
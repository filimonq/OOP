package ru.nsu.fitkulin;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import ru.nsu.fitkulin.controller.GameController;
import ru.nsu.fitkulin.model.GameBoard;
import ru.nsu.fitkulin.model.Level;
import ru.nsu.fitkulin.model.SimpleLevel;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameIntegrationTest {

    @Test
    public void testGameLaunch() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Thread thread = new Thread(() -> {
            Platform.startup(() -> {
                try {
                    Stage stage = new Stage();

                    javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                            getClass().getResource("/game.fxml"));
                    javafx.scene.Parent root = loader.load();
                    GameController controller = loader.getController();
                    assertNotNull(controller);

                    Level level = new SimpleLevel(5, 1, 10, 10, 5);
                    controller.setStage(stage);
                    controller.setLevel(level);

                    assertNotNull(controller.getGameBoard());

                    javafx.scene.Scene scene = new javafx.scene.Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    Platform.runLater(() -> {
                        stage.close();
                        latch.countDown();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        thread.start();

        latch.await();
    }
}
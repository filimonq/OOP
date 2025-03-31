package ru.nsu.fitkulin;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import ru.nsu.fitkulin.model.Level;
import ru.nsu.fitkulin.model.SimpleLevel;
import ru.nsu.fitkulin.view.LevelSelector;

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

                    LevelSelector levelSelector = new MockLevelSelector();
                    Level selectedLevel = levelSelector.selectLevel();
                    assertNotNull(selectedLevel);

                    Main main = new Main();
                    main.start(stage);

                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        thread.start();

        latch.await();
    }

    private static class MockLevelSelector extends LevelSelector {
        @Override
        public Level selectLevel() {
            return new SimpleLevel(5, 1, 10, 10, 5);
        }
    }
}
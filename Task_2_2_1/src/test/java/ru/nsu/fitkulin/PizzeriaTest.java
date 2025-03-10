package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {
    @Test
    void testPizzeriaLifecycle() throws Exception {
        InputStream configStream = getClass().getResourceAsStream("/config.json");
        assertNotNull(configStream, "Конфигурационный файл не найден");

        Pizzeria pizzeria = new Pizzeria(configStream);

        Thread pizzeriaThread = new Thread(() -> {
            try {
                pizzeria.startWorkDay();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        long startTime = System.currentTimeMillis();
        pizzeriaThread.start();
        pizzeriaThread.join(120 * 1000L);

        long elapsed = System.currentTimeMillis() - startTime;

        assertFalse(pizzeriaThread.isAlive(), "Пиццерия не завершила работу вовремя");
        System.out.println("Пиццерия завершила работу за " + elapsed + " мс");
    }
}

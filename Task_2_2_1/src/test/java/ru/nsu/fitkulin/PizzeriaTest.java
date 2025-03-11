package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.util.Random;
import org.junit.jupiter.api.Test;

class PizzeriaTest {
    @Test
    void testPizzeriaLifecycle() throws Exception {
        InputStream configStream = getClass().getResourceAsStream("/config.json");
        assertNotNull(configStream, "Конфигурационный файл не найден");

        Pizzeria pizzeria = new Pizzeria(configStream);

        Thread additionalOrdersThread = getThread(pizzeria);

        Thread pizzeriaThread = new Thread(() -> {
            try {
                pizzeria.startWorkDay();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "PizzeriaThread");

        long startTime = System.currentTimeMillis();
        pizzeriaThread.start();
        pizzeriaThread.join(120 * 1000L);
        additionalOrdersThread.join(120 * 1000L);

        long elapsed = System.currentTimeMillis() - startTime;

        assertFalse(pizzeriaThread.isAlive(), "Пиццерия не завершила работу вовремя");
        System.out.println("Пиццерия завершила работу за " + elapsed + " мс");
    }

    private static Thread getThread(Pizzeria pizzeria) {
        Thread additionalOrdersThread = new Thread(() -> {
            Random random = new Random();
            int orderId = pizzeria.getInitialOrderId() + 1;
            while (pizzeria.getOrderQueue().isRunning()) {
                try {
                    long delay = random.nextInt(10_001);
                    Thread.sleep(delay);

                    if (!pizzeria.getOrderQueue().isRunning()) {
                        break;
                    }

                    int deliveryTime = random.nextInt(10) + 5;
                    int amount = random.nextInt(5) + 5;
                    System.out.println("Заказ " + orderId + " добавлен в очередь. Количество "
                            + "пицц: " + amount);
                    Order order = new Order(orderId++, deliveryTime, amount);
                    pizzeria.getOrderQueue().addOrder(order);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "AdditionalOrdersThread");

        additionalOrdersThread.start();
        return additionalOrdersThread;
    }
}

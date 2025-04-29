package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.List;

/**
 * class implementing a pizzeria. loads data from JSON and regulates the operation of the pizzeria.
 */
public class Pizzeria {
    private final OrderQueue<Order> orderQueue = new OrderQueue<>();
    private final Warehouse<Order> warehouse;
    private final int workTimeSeconds;
    private final List<Thread> bakerThreads = new ArrayList<>();
    private final List<Thread> courierThreads = new ArrayList<>();
    private int maxOrderId = 0;


    public Pizzeria(PizzeriaFactory config) {
        this.warehouse = new Warehouse<>(config.getWarehouseCapacity());
        this.workTimeSeconds = config.getWorkTimeSeconds();

        config.createBakers(orderQueue, warehouse).forEach(baker -> {
            Thread bakerThread = new Thread(baker, "Baker-" + bakerThreads.size());
            bakerThreads.add(bakerThread);
            bakerThread.start();
        });

        config.createCouriers(warehouse).forEach(courier -> {
            Thread courierThread = new Thread(courier, "Courier-" + courierThreads.size());
            courierThreads.add(courierThread);
            courierThread.start();
        });

        config.createOrders().forEach(order -> {
            orderQueue.addOrder(order);
            maxOrderId = Math.max(maxOrderId, order.getId());
        });
    }

    public void startWorkDay() throws InterruptedException {
        Thread.sleep(workTimeSeconds * 1000L);
        shutdown();
    }

    public void shutdown() {
        orderQueue.stop();
        System.out.println("Pizzeria is closed");

        while (!orderQueue.isEmpty() || !warehouse.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        for (Thread baker : bakerThreads) {
            try {
                baker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        warehouse.shutdown();

        for (Thread courier : courierThreads) {
            try {
                courier.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Pizzeria is finally closed");
    }

    public OrderQueue<Order> getOrderQueue() {
        return orderQueue;
    }

    public int getInitialOrderId() {
        return maxOrderId;
    }
}

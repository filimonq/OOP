package ru.nsu.fitkulin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Pizzeria {
    private final OrderQueue<Order> orderQueue = new OrderQueue<>();
    private final Warehouse<Order> warehouse;
    private final int workTimeSeconds;
    private final List<Thread> bakerThreads = new ArrayList<>();
    private final List<Thread> courierThreads = new ArrayList<>();

    public Pizzeria(String configPath) throws Exception {
        this(new ObjectMapper().readTree(new File(configPath)));
    }

    public Pizzeria(InputStream configStream) throws Exception {
        this(new ObjectMapper().readTree(configStream));
    }

    private Pizzeria(JsonNode config) {
        this.warehouse = new Warehouse<>(config.get("warehouseCapacity").asInt());
        this.workTimeSeconds = config.get("workTimeSeconds").asInt();

        config.get("bakers").forEach(bakerNode -> {
            int cookingTime = bakerNode.get("cookingTimeSec").asInt() * 1000;
            Baker baker = new Baker(orderQueue, warehouse, cookingTime);
            Thread bakerThread = new Thread(baker, "Baker-" + bakerThreads.size());
            bakerThreads.add(bakerThread);
            bakerThread.start();
        });

        config.get("couriers").forEach(courierNode -> {
            int capacity = courierNode.get("trunkCapacity").asInt();
            double speed = courierNode.get("speedCoefficient").asDouble();
            Courier courier = new Courier(warehouse, capacity, speed);
            Thread courierThread = new Thread(courier, "Courier-" + courierThreads.size());
            courierThreads.add(courierThread);
            courierThread.start();
        });

        config.get("orders").forEach(orderNode -> {
            int id = orderNode.get("id").asInt();
            int time = orderNode.get("time").asInt();
            int amount = orderNode.get("amount").asInt();
            orderQueue.addOrder(new Order(id, time, amount));
        });
    }

    public void startWorkDay() throws InterruptedException {
        Thread.sleep(workTimeSeconds * 1000L);
        shutdown();
    }

    public void shutdown() {
        orderQueue.stop();
        warehouse.shutdown();
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
}

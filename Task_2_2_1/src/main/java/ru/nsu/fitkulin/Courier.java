package ru.nsu.fitkulin;

import java.util.List;

public class Courier implements Runnable {
    private final Warehouse<Order> warehouse;
    private final int trunkCapacity;
    private final int deliveryTimeMs;

    public Courier(Warehouse<Order> warehouse, int trunkCapacity, int deliveryTimeMs) {
        this.warehouse = warehouse;
        this.trunkCapacity = trunkCapacity;
        this.deliveryTimeMs = deliveryTimeMs;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<Order> batch = warehouse.pollBatch(trunkCapacity);
                if (batch.isEmpty()) break;

                for (Order order : batch) {
                    order.setStatus(OrderStatus.DELIVERING);
                    System.out.println("Order " + order.getId() + " status: " + order.getStatus());
                }

                Thread.sleep(deliveryTimeMs);

                for (Order order : batch) {
                    order.setStatus(OrderStatus.DELIVERED);
                    System.out.println("Order " + order.getId() + " status: " + order.getStatus());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
package ru.nsu.fitkulin;

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

    }
}
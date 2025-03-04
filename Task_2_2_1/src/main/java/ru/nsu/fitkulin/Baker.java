package ru.nsu.fitkulin;

public class Baker implements Runnable {
    private final OrderQueue<Order> orderQueue;
    private final Warehouse<Order> warehouse;
    private final int cookingTimeMs;
    private Order currentOrder;

    public Baker(OrderQueue<Order> orderQueue, Warehouse<Order> warehouse, int cookingTimeMs) {
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.cookingTimeMs = cookingTimeMs;
    }

    @Override
    public void run() {

    }
}
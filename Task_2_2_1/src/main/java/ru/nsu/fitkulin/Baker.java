package ru.nsu.fitkulin;

public class Baker implements Runnable {
    private final OrderQueue<Order> orderQueue;
    private final Warehouse<Order> warehouse;
    private final int cookingTimeMs;

    public Baker(OrderQueue<Order> orderQueue, Warehouse<Order> warehouse, int cookingTimeMs) {
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.cookingTimeMs = cookingTimeMs;
    }

    @Override
    public void run() {
        while (orderQueue.isRunning() || !orderQueue.isEmpty()) {
            Order order = null;
            try {
                order = orderQueue.takeOrder();
            } catch (InterruptedException e) {
                if (!orderQueue.isRunning() && orderQueue.isEmpty()) {
                    break;
                }
                continue;
            }
            if (order == null) {
                if (!orderQueue.isRunning() && orderQueue.isEmpty()) {
                    break;
                }
                continue;
            }

            order.setStatus(OrderStatus.COOKING);
            try {
                Thread.sleep((long) cookingTimeMs * order.getAmount());
            } catch (InterruptedException e) {
                //
            }
            order.setStatus(OrderStatus.COOKED);
            try {
                warehouse.put(order);
            } catch (InterruptedException e) {
                //
            }
        }
    }
}

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
        try {
            while (!Thread.currentThread().isInterrupted() && orderQueue.isRunning()) {
                Order order = orderQueue.takeOrder();
                if (order == null) break;

                long startTime = System.currentTimeMillis();
                order.setStatus(OrderStatus.COOKING);
                Thread.sleep((long) cookingTimeMs * order.getAmount());
                long endTime = System.currentTimeMillis();

                order.setStatus(OrderStatus.COOKED);
                warehouse.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Baker interrupted");
        }
    }
}
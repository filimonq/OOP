package ru.nsu.fitkulin;

/**
 * The class represents a baker who prepares orders.
 */
public class Baker implements Runnable {
    private final OrderQueue<Order> orderQueue;
    private final Warehouse<Order> warehouse;
    private final int cookingTimeMs;

    /**
     * constructor.
     *
     * @param orderQueue queue for orders.
     * @param warehouse warehouse where bakers put pizza.
     * @param cookingTimeMs bakers speed.
     */
    public Baker(OrderQueue<Order> orderQueue, Warehouse<Order> warehouse, int cookingTimeMs) {
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.cookingTimeMs = cookingTimeMs;
    }

    /**
     * Starts the order preparation process.
     */
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
                System.out.println("Baker interrupted");
            }
            order.setStatus(OrderStatus.COOKED);
            try {
                warehouse.put(order);
            } catch (InterruptedException e) {
                System.out.println("Baker interrupted");
            }
        }
    }
}

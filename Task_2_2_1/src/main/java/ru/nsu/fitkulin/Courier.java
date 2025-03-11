package ru.nsu.fitkulin;

/**
 * The class represents a courier who delivers orders to customers.
 */
public class Courier implements Runnable {
    private final Warehouse<Order> warehouse;
    private final int trunkCapacity;
    private final double speedCoefficient;

    public Courier(Warehouse<Order> warehouse, int trunkCapacity, double speedCoefficient) {
        this.warehouse = warehouse;
        this.trunkCapacity = trunkCapacity;
        this.speedCoefficient = speedCoefficient;
    }

    /**
     * Starts the order delivery process.
     */
    @Override
    public void run() {
        while (!warehouse.isEmpty() || warehouse.isRunning()) {
            Order order = null;
            try {
                order = warehouse.poll();
            } catch (InterruptedException e) {
                if (!warehouse.isRunning() && warehouse.isEmpty()) {
                    break;
                }
                continue;
            }
            if (order == null) {
                break;
            }
            try {
                order.setStatus(OrderStatus.DELIVERING);
                int trips = (int) Math.ceil((double) order.getAmount() / trunkCapacity);
                int deliveryTime = (int) (order.getTime() * speedCoefficient * trips * 1000);
                Thread.sleep(deliveryTime);
                order.setStatus(OrderStatus.DELIVERED);
                System.out.println("Order " + order.getId() + " delivered in "
                        + deliveryTime + "ms (" + trips + " trips)");
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

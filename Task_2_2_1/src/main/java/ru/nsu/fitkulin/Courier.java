package ru.nsu.fitkulin;

public class Courier implements Runnable {
    private final Warehouse<Order> warehouse;
    private final int trunkCapacity;
    private final double speedCoefficient;

    public Courier(Warehouse<Order> warehouse, int trunkCapacity, double speedCoefficient) {
        this.warehouse = warehouse;
        this.trunkCapacity = trunkCapacity;
        this.speedCoefficient = speedCoefficient;
    }

    @Override
    public void run() {
        while (warehouse.isRunning() || !warehouse.isEmpty()) {
            Order order = null;
            try {
                order = warehouse.poll();
            } catch (InterruptedException e) {
                if (!warehouse.isRunning() && warehouse.isEmpty()) break;
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
                System.out.println("Order " + order.getId() + " delivered in " +
                        deliveryTime + "ms (" + trips + " trips)");
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

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
        try {
            while (!Thread.currentThread().isInterrupted() && warehouse.isRunning()) {
                Order order = warehouse.poll();
                if (order == null) break;

                processOrder(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Courier interrupted");
        }
    }

    private void processOrder(Order order) throws InterruptedException {
        order.setStatus(OrderStatus.DELIVERING);

        int trips = (int) Math.ceil((double) order.getAmount() / trunkCapacity);
        int deliveryTime = (int) (order.getTime() * speedCoefficient * trips);

        Thread.sleep(deliveryTime);

        order.setStatus(OrderStatus.DELIVERED);
        System.out.println("Order " + order.getId() + " delivered in "
                + (deliveryTime) + "ms (" + trips + " trips)");
    }
}
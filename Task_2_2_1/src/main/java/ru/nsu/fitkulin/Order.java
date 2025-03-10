package ru.nsu.fitkulin;

/**
 * class for info about order.
 */
public class Order {
    private final int id;
    private final int time;
    private final int amount;
    private OrderStatus status;

    public Order(int id, int time, int amount) {
        this.id = id;
        this.time = time;
        this.amount = amount;
        this.status = OrderStatus.QUEUE;
    }

    //
    public int getId() {
        return this.id;
    }

    public int getTime() {
        return this.time;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getStatus() {
        return this.status.toString();
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        System.out.println("Order " + id + " status: " + status);
    }
}
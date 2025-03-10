package ru.nsu.fitkulin;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The class represents a queue of orders in a pizzeria.
 */
public class OrderQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private volatile boolean isRunning = true;

    /**
     * Adds a new order to the queue.
     */
    public synchronized void addOrder(T order) {
        if (isRunning) {
            queue.add(order);
            notifyAll();
        }
    }

    /**
     * Retrieves an order from the queue. If the queue is empty, waits for a new order.
     */
    public synchronized T takeOrder() throws InterruptedException {
        while (queue.isEmpty() && isRunning) {
            wait();
        }

        if (!isRunning && queue.isEmpty()) {
            return null;
        }

        return queue.poll();
    }

    public synchronized void stop() {
        isRunning = false;
        notifyAll();
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

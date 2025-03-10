package ru.nsu.fitkulin;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private volatile boolean isRunning = true;

    public synchronized void addOrder(T order) {
        if (isRunning) {
            queue.add(order);
            notifyAll();
        }
    }

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

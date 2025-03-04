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
        return isRunning ? queue.poll() : null;
    }

    public synchronized void stop() {
        isRunning = true;
        notifyAll();
    }

    public synchronized boolean isStopped() {
        return isRunning;
    }
}
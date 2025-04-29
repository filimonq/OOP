package ru.nsu.fitkulin;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The class represents a warehouse where finished orders are stored.
 *
 * @param <T> queue element type
 */
public class Warehouse<T> {
    private final Queue<T> storage = new LinkedList<>();
    private final int capacity;
    private boolean isRunning = true;

    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }

    /**
     * Adds an item to the warehouse.
     * If the warehouse is full, the flow is blocked until space becomes available.
     */
    public synchronized void put(T item) throws InterruptedException {
        while (storage.size() >= capacity && isRunning) {
            wait();
        }

        if (!isRunning) {
            return;
        }

        storage.offer(item);
        notifyAll();
    }

    /**
     * Retrieves one item.
     * If the warehouse is empty, the thread blocks until items arrive.
     */
    public synchronized T poll() throws InterruptedException {
        while (storage.isEmpty() && isRunning) {
            wait();
        }

        if (!isRunning && storage.isEmpty()) {
            return null;
        }

        T item = storage.poll();
        notifyAll();
        return item;
    }

    /**
     * shuts down the warehouse, waking up all waiting threads.
     */
    public synchronized void shutdown() {
        isRunning = false;
        notifyAll();
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }
}

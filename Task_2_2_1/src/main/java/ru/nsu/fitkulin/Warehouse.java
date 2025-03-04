package ru.nsu.fitkulin;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @param <T> queue element type
 */
public class Warehouse<T> {
    private final Queue<T> storage = new LinkedList<>();
    private final int capacity; // Максимальная вместимость
    private boolean isRunning = true;

    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    public boolean isEmpty() {
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
            throw new InterruptedException("Warehouse is shut down");
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

        if (!isRunning) {
            return null;
        }

        T item = storage.poll();
        notifyAll();
        return item;
    }

    /**
     * Retrieves up to maxItems items.
     */
    public synchronized List<T> pollBatch(int maxItems) throws InterruptedException {
        List<T> batch = new LinkedList<>();
        while (storage.isEmpty() && isRunning) {
            wait();
        }

        if (!isRunning) {
            return batch;
        }

        int count = Math.min(maxItems, storage.size());
        for (int i = 0; i < count; i++) {
            batch.add(storage.poll());
        }

        notifyAll();
        return batch;
    }

    /**
     * Stops warehouse operations.
     */
    public synchronized void shutdown() {
        isRunning = false;
        notifyAll();
    }

    public synchronized boolean isActive() {
        return isRunning;
    }
}
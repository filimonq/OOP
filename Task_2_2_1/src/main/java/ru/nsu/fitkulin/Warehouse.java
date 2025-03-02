package ru.nsu.fitkulin;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Warehouse<T> {
    private final Queue<T> storage = new LinkedList<>();
    private final int capacity; // Максимальная вместимость
    private boolean isActive = true; // Флаг работы склада

    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    /**
     * Добавляет элемент на склад (аналог offer() в Queue).
     * Если склад полон, поток блокируется до появления места.
     */
    public synchronized void put(T item) throws InterruptedException {
        while (storage.size() >= capacity && isActive) {
            wait(); // Ждем свободного места
        }

        if (!isActive) {
            throw new InterruptedException("Warehouse is shut down");
        }

        storage.offer(item);
        notifyAll(); // Уведомляем ждущие потоки
    }

    /**
     * Извлекает один элемент (аналог poll() в Queue).
     * Если склад пуст, поток блокируется до появления элементов.
     */
    public synchronized T poll() throws InterruptedException {
        while (storage.isEmpty() && isActive) {
            wait(); // Ждем элементов
        }

        if (!isActive) {
            return null; // Склад остановлен
        }

        T item = storage.poll();
        notifyAll(); // Уведомляем ждущие потоки
        return item;
    }

    /**
     * Извлекает до maxItems элементов (расширенная логика для курьеров).
     */
    public synchronized List<T> pollBatch(int maxItems) throws InterruptedException {
        List<T> batch = new LinkedList<>();

        while (storage.isEmpty() && isActive) {
            wait(); // Ждем элементов
        }

        if (!isActive) {
            return batch; // Возвращаем пустой список
        }

        int count = Math.min(maxItems, storage.size());
        for (int i = 0; i < count; i++) {
            batch.add(storage.poll());
        }

        notifyAll(); // Уведомляем пекарей о свободном месте
        return batch;
    }

    /**
     * Останавливает работу склада.
     */
    public synchronized void shutdown() {
        isActive = false;
        notifyAll(); // Разблокируем все потоки
    }

    public synchronized boolean isActive() {
        return isActive;
    }
}
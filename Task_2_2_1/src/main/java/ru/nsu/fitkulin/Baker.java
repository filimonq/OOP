package ru.nsu.fitkulin;

public class Baker implements Runnable {
    private final OrderQueue orderQueue; // Очередь заказов
    private final Warehouse<Order> warehouse; // Склад для готовых пицц
    private final int cookingTimeMs; // Время готовки (в миллисекундах)
    private Order currentOrder; // Текущий обрабатываемый заказ

    public Baker(OrderQueue orderQueue, Warehouse<Order> warehouse, int cookingTimeMs) {
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.cookingTimeMs = cookingTimeMs;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // 1. Берем заказ из очереди
                currentOrder = orderQueue.takeOrder();
                if (currentOrder == null) break; // Очередь остановлена

                // 2. Меняем статус и готовим
                currentOrder.setStatus(OrderStatus.COOKING);
                Thread.sleep(cookingTimeMs); // Симуляция готовки

                // 3. Кладем на склад
                warehouse.put(currentOrder);
                currentOrder.setStatus(OrderStatus.COOKED);
                currentOrder = null; // Сбрасываем текущий заказ
            }
        } catch (InterruptedException e) {
            // 4. Обработка прерывания
            Thread.currentThread().interrupt();
            System.out.println("Baker was interrupted");
        }
    }

    // Для получения текущего заказа при сериализации состояния
    public Order getCurrentOrder() {
        return currentOrder;
    }
}
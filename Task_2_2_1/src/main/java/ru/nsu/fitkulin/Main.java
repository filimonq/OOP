package ru.nsu.fitkulin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        OrderQueue<Order> orderQueue = new OrderQueue<>();
        Warehouse<Order> warehouse = new Warehouse<>(10);

        Courier footCourier = new Courier(warehouse, 5, 1.0);
        Courier scooterCourier = new Courier(warehouse, 10, 0.5);
        Courier carCourier = new Courier(warehouse, 20, 0.2);

        Baker baker1 = new Baker(orderQueue, warehouse, 1000);
        Baker baker2 = new Baker(orderQueue, warehouse, 2000);

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(baker1);
        executor.execute(baker2);
        executor.execute(footCourier);
        executor.execute(scooterCourier);
        executor.execute(carCourier);

        orderQueue.addOrder(new Order(1, 10000, 10));
        orderQueue.addOrder(new Order(2, 10000, 15));
        orderQueue.addOrder(new Order(3, 10000, 20));

        Thread.sleep(60_000);

        orderQueue.stop();
        warehouse.shutdown();
        executor.shutdownNow();

        if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("\n=== Пиццерия закрыта ===");
            System.out.println("Осталось на складе: " +
                    (warehouse.isRunning() ? warehouse.pollBatch(10).size() : 0));
        }
    }
}
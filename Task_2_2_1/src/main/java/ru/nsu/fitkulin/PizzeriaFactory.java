package ru.nsu.fitkulin;

import java.util.List;

public interface PizzeriaFactory {
    List<Baker> createBakers(OrderQueue<Order> orderQueue, Warehouse<Order> warehouse);
    List<Courier> createCouriers(Warehouse<Order> warehouse);
    List<Order> createOrders();

    int getWarehouseCapacity();
    int getWorkTimeSeconds();
}
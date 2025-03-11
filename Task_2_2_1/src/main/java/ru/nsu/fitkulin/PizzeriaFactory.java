package ru.nsu.fitkulin;

import java.util.List;

/**
 * interface for creating bakers, couriers, and orders in a pizzeria.
 */
public interface PizzeriaFactory {
    List<Baker> createBakers(OrderQueue<Order> orderQueue, Warehouse<Order> warehouse);
    List<Courier> createCouriers(Warehouse<Order> warehouse);
    List<Order> createOrders();

    int getWarehouseCapacity();
    int getWorkTimeSeconds();
}
package ru.nsu.fitkulin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DefaultPizzeriaFactory implements PizzeriaFactory {
    private final JsonNode config;

    public DefaultPizzeriaFactory(String configPath) throws Exception {
        this(new ObjectMapper().readTree(new File(configPath)));
    }

    public DefaultPizzeriaFactory(InputStream configStream) throws Exception {
        this(new ObjectMapper().readTree(configStream));
    }

    private DefaultPizzeriaFactory(JsonNode config) {
        this.config = config;
    }

    @Override
    public List<Baker> createBakers(OrderQueue<Order> orderQueue, Warehouse<Order> warehouse) {
        List<Baker> bakers = new ArrayList<>();
        config.get("bakers").forEach(bakerNode -> {
            int cookingTime = bakerNode.get("cookingTimeSec").asInt() * 1000;
            bakers.add(new Baker(orderQueue, warehouse, cookingTime));
        });
        return bakers;
    }

    @Override
    public List<Courier> createCouriers(Warehouse<Order> warehouse) {
        List<Courier> couriers = new ArrayList<>();
        config.get("couriers").forEach(courierNode -> {
            int capacity = courierNode.get("trunkCapacity").asInt();
            double speed = courierNode.get("speedCoefficient").asDouble();
            couriers.add(new Courier(warehouse, capacity, speed));
        });
        return couriers;
    }

    @Override
    public List<Order> createOrders() {
        List<Order> orders = new ArrayList<>();
        config.get("orders").forEach(orderNode -> {
            int id = orderNode.get("id").asInt();
            int time = orderNode.get("time").asInt();
            int amount = orderNode.get("amount").asInt();
            orders.add(new Order(id, time, amount));
        });
        return orders;
    }

    public int getWarehouseCapacity() {
        return config.get("warehouseCapacity").asInt();
    }

    public int getWorkTimeSeconds() {
        return config.get("workTimeSeconds").asInt();
    }
}
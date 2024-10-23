package ru.nsu.fitkulin;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        IncidenceMatrixGraph<String> graph = new IncidenceMatrixGraph<>(5);

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "C");
        graph.addEdge("B", "A");

        System.out.println(graph.toString());
    }
}


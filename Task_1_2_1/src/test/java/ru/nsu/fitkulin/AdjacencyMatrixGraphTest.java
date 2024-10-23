package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {
    AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();
    @Test
    void graphTest() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "C");
        graph.addEdge("B", "A");
        graph.addEdge("E", "D");

        graph.removeVertex("A");
        graph.removeEdge("B", "C");

        String expectedOutput =
                """
                        Vertices: [B, C, D, E]
                        Adjacency List:
                        B:\s
                        C: C\s
                        D:\s
                        E: D\s
                        """;


        assertEquals(expectedOutput, graph.toString());
    }

    @Test
    void getNeighbors() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "C");
        graph.addEdge("A", "B");

        String expectedOutput = "[C, B]";
        assertEquals(expectedOutput, graph.getNeighbors("A"));

    }
}
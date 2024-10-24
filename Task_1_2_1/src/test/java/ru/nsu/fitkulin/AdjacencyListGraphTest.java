package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdjacencyListGraphTest {
    AdjacencyListGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph<>();
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
    }

    @Test
    void graphTest() {
        graph.removeVertex("A");
        graph.removeVertex("V");
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
        List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("C");
        expectedOutput.add("A");

        assertEquals(expectedOutput, graph.getNeighbors("B"));
    }
}
package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncidenceMatrixGraphTest {
    IncidenceMatrixGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrixGraph<>();
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
        graph.removeEdge("B", "C");

        String expectedOutput = // криво как то
                """
                        Vertices: [B, C, D, E]
                        Incidence Matrix:
                        B: 0 1\s
                        C: 2 0\s
                        D: 0 0\s
                        E: 0 0\s
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
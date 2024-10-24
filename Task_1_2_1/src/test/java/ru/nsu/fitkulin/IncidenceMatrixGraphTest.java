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
        graph.removeVertex("E");
        graph.removeVertex("V");
        graph.removeEdge("B", "C");

        String expectedOutput = // криво как то
                """
                        Vertices: [A, B, C, D]
                        Incidence Matrix:
                        A: 1 0 -1 0\s
                        B: -1 0 1 0\s
                        C: 0 2 0 0\s
                        D: 0 0 0 0\s
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
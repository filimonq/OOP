package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class GraphSortTest {
    private final AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

    @Test
    void testTopologicalSortComplex() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        GraphSort<String> graphSort = new GraphSort<>(graph);
        List<String> sorted = graphSort.topologicalSort();
        assertEquals(Arrays.asList("A", "B", "C", "D"), sorted);
    }

    /**
     * test for graph with cycle.
     */
    @Test
    void testTopologicalSortWithCycle() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        GraphSort<String> graphSort = new GraphSort<>(graph);

        Exception exception = assertThrows(IllegalStateException.class, graphSort::topologicalSort);
        assertEquals("Cycle in the graph - topological sort not possible.", exception.getMessage());
    }

    /**
     * test for empty graph.
     */
    @Test
    void testTopologicalSortEmptyGraph() {
        GraphSort<String> graphSort = new GraphSort<>(graph);
        List<String> sorted = graphSort.topologicalSort();

        assertTrue(sorted.isEmpty());
    }
}

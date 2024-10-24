package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * class for methods of sorting graph.
 */
public class GraphSort<T> {

    private final Graph<T> graph;

    /**
     * constructor.
     * @param graph is our graph representation
     */
    public GraphSort(Graph<T> graph) {
        this.graph = graph;
    }

    /**
     * topological sort for our graph.
     * @return sorted graph.
     */
    public List<T> topologicalSort() {
        Map<T, Integer> inDegree = new HashMap<>();
        for (T vertex : getVertices()) {
            inDegree.put(vertex, 0);
        }

        // считаем входящие степени вершин
        for (T vertex : getVertices()) {
            for (T neighbor : graph.getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<T> queue = new LinkedList<>();
        // добавляем все вершины с нулевой входящей степенью в очередь
        for (Map.Entry<T, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<T> sortedList = new ArrayList<>();

        while (!queue.isEmpty()) {
            T vertex = queue.poll();
            sortedList.add(vertex);

            // уменьшаем входящие степени соседей
            for (T neighbor : graph.getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // если количество отсортированных вершин меньше общего количества вершин,
        // значит граф имеет циклы
        if (sortedList.size() != inDegree.size()) {
            throw new IllegalStateException("Cycle in the graph - topological sort not possible.");
        }

        return sortedList;
    }

    private List<T> getVertices() {
        return new ArrayList<>(graph.getVertices());
    }

}

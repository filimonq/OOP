package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.List;

class AdjacencyMatrixGraph<T> implements Graph<T> {
    private final List<T> vertices;
    private final List<List<Boolean>> adjacencyMatrix;

    /**
     * constructor.
     */
    public AdjacencyMatrixGraph() {
        this.vertices = new ArrayList<>();
        this.adjacencyMatrix = new ArrayList<>();
    }

    @Override
    public void addVertex(T vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            int newIndex = vertices.size() - 1;

            for (List<Boolean> row : adjacencyMatrix) {
                row.add(false);
            }

            ArrayList<Boolean> newRow = new ArrayList<>();
            for (int i = 0; i <= newIndex; i++) {
                newRow.add(false);
            }
            adjacencyMatrix.add(newRow);
        }
    }

    @Override
    public void removeVertex(T vertex) {
        int index = vertices.indexOf(vertex);
        // indexOf возвращает -1, если элемент не найден
        if (index == -1) {
            return;
        }

        vertices.remove(index);
        adjacencyMatrix.remove(index);

        for (List<Boolean> row : adjacencyMatrix) {
            row.remove(index);
        }
    }

    @Override
    public void addEdge(T from, T to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (fromIndex != -1 && toIndex != -1) {
            adjacencyMatrix.get(fromIndex).set(toIndex, true);
        }
    }

    @Override
    public void removeEdge(T from, T to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (fromIndex != -1 && toIndex != -1) {
            adjacencyMatrix.get(fromIndex).set(toIndex, false);
        }
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        List<T> neighbors = new ArrayList<>();
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) {
            return neighbors;
        }
        for (int i = 0; i < vertices.size(); i++) {
            if (adjacencyMatrix.get(vertexIndex).get(i)) {
                neighbors.add(vertices.get(i));
            }
        }
        return neighbors;
    }

    public List<T> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Adjacency Matrix:\n");
        for (int i = 0; i < adjacencyMatrix.size(); i++) {
            sb.append(vertices.get(i)).append(": ");
            for (int j = 0; j < adjacencyMatrix.get(i).size(); j++) {
                sb.append(adjacencyMatrix.get(i).get(j) ? "1 " : "0 ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

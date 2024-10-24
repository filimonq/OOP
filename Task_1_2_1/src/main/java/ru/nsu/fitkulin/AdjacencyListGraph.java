package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.List;

class AdjacencyListGraph<T> implements Graph<T> {
    private final List<T> vertices;
    private final List<List<T>> adjacencyList;

    public AdjacencyListGraph() {
        this.vertices = new ArrayList<>();
        this.adjacencyList = new ArrayList<>();
    }

    @Override
    public void addVertex(T vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            adjacencyList.add(new ArrayList<>());
        }
    }

    @Override
    public void removeVertex(T vertex) {
        int index = vertices.indexOf(vertex);
        if (index == -1) {
            return;
        }

        vertices.remove(index);
        adjacencyList.remove(index);

        for (List<T> neighbors : adjacencyList) {
            neighbors.remove(vertex);
        }
    }

    @Override
    public void addEdge(T from, T to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (fromIndex != -1 && toIndex != -1) {
            adjacencyList.get(fromIndex).add(to);
        }
    }

    @Override
    public void removeEdge(T from, T to) {
        int fromIndex = vertices.indexOf(from);
        if (fromIndex != -1) {
            adjacencyList.get(fromIndex).remove(to);
        }
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) {
            return new ArrayList<>();
        }

        return new ArrayList<>(adjacencyList.get(vertexIndex));
    }

    public List<T> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Adjacency List:\n");
        for (int i = 0; i < adjacencyList.size(); i++) {
            sb.append(vertices.get(i)).append(": ");
            for (T neighbor : adjacencyList.get(i)) {
                sb.append(neighbor).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

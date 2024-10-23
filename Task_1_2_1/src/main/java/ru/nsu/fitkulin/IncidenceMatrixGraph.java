package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph<T> implements Graph<T> {
    private final List<T> vertices;
    private final List<List<Integer>> incidenceMatrix;
    private int edgeCount;

    public IncidenceMatrixGraph(int maxVertices) {
        this.vertices = new ArrayList<>();
        this.incidenceMatrix = new ArrayList<>();
        for (int i = 0; i < maxVertices; i++) {
            incidenceMatrix.add(new ArrayList<>());
            for (int j = 0; j < maxVertices; j++) {
                incidenceMatrix.get(i).add(0);
            }
        }
        this.edgeCount = 0;
    }

    @Override
    public void addVertex(T vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            for (List<Integer> row : incidenceMatrix) {
                row.add(0);
            }
            // Добавляем новый ряд для новой вершины
            incidenceMatrix.add(new ArrayList<>());
            for (int i = 0; i < edgeCount; i++) {
                incidenceMatrix.get(vertices.size() - 1).add(0);
            }
        }
    }

    @Override
    public void removeVertex(T vertex) {
        int index = vertices.indexOf(vertex);
        if (index == -1) {
            return;
        }

        vertices.remove(index);
        incidenceMatrix.remove(index);

        for (List<Integer> row : incidenceMatrix) {
            row.remove(index);
        }
    }

    @Override
    public void addEdge(T from, T to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (fromIndex != -1 && toIndex != -1) {
            edgeCount++;
            for (List<Integer> row : incidenceMatrix) {
                row.add(0);
            }
            incidenceMatrix.get(fromIndex).set(edgeCount - 1, 1);
            incidenceMatrix.get(toIndex).set(edgeCount - 1, -1);
        }
    }

    @Override
    public void removeEdge(T from, T to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (fromIndex != -1 && toIndex != -1) {
            for (int i = 0; i < edgeCount; i++) {
                if (incidenceMatrix.get(fromIndex).get(i) == 1 && incidenceMatrix.get(toIndex).get(i) == -1) {
                    incidenceMatrix.get(fromIndex).set(i, 0);
                    incidenceMatrix.get(toIndex).set(i, 0);
                    break;
                }
            }
        }
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        List<T> neighbors = new ArrayList<>();
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) {
            return neighbors;
        }

        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(vertexIndex).get(i) == 1) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (incidenceMatrix.get(j).get(i) == -1 && j != vertexIndex) {
                        neighbors.add(vertices.get(j));
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Incidence Matrix:\n");
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(vertices.get(i)).append(": ");
            for (int j = 0; j < edgeCount; j++) {
                sb.append(incidenceMatrix.get(i).get(j)).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}


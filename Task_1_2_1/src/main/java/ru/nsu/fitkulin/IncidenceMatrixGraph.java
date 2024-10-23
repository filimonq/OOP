package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IncidenceMatrixGraph<T> implements Graph<T> {
    private final List<T> vertices;
    private final List<List<Boolean>> incidenceMatrix;
    private int edgeCount;

    public IncidenceMatrixGraph(int maxVertices) {
        this.vertices = new ArrayList<>();
        this.incidenceMatrix = new ArrayList<>();
        for (int i = 0; i < maxVertices; i++) {
            incidenceMatrix.add(new ArrayList<>());
            for (int j = 0; j < maxVertices; j++) {
                incidenceMatrix.get(i).add(false);
            }
        }
        this.edgeCount = 0;
    }

    @Override
    public void addVertex(T vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            for (List<Boolean> row : incidenceMatrix) {
                row.add(false);
            }
            for (int i = 0; i < vertices.size(); i++) {
                incidenceMatrix.get(vertices.size() - 1).add(false);
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

        for (List<Boolean> row : incidenceMatrix) {
            row.remove(index);
        }
    }

    @Override
    public void addEdge(T from, T to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (fromIndex != -1 && toIndex != -1) {
            for (List<Boolean> row : incidenceMatrix) {
                row.add(false);
            }
            for (int j = 0; j < vertices.size(); j++) {
                incidenceMatrix.get(j).add(false);
            }
            incidenceMatrix.get(fromIndex).set(edgeCount, true);
            edgeCount++;
        }
    }

    @Override
    public void removeEdge(T from, T to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (fromIndex != -1 && toIndex != -1) {
            for (int i = 0; i < edgeCount; i++) {
                if (incidenceMatrix.get(fromIndex).get(i) && incidenceMatrix.get(toIndex).get(i)) {
                    incidenceMatrix.get(fromIndex).set(i, false);
                    incidenceMatrix.get(toIndex).set(i, false);
                    break; // Удаляем только первое найденное ребро
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
            if (incidenceMatrix.get(vertexIndex).get(i)) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (incidenceMatrix.get(j).get(i) && j != vertexIndex) {
                        neighbors.add(vertices.get(j));
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncidenceMatrixGraph<?> that)) {
            return false;
        }
        return edgeCount == that.edgeCount && Objects.equals(vertices, that.vertices)
                && Objects.equals(incidenceMatrix, that.incidenceMatrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, incidenceMatrix, edgeCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");

        sb.append("Incidence Matrix:\n");
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(vertices.get(i)).append(": ");
            for (int j = 0; j < edgeCount; j++) {
                sb.append(incidenceMatrix.get(i).get(j) ? "1 " : "0 ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}

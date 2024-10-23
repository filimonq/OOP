package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.List;

class IncidenceMatrixGraph<T> implements Graph<T> {
    private final List<T> vertices;
    private final List<List<Integer>> incidenceMatrix; // Изменено на Integer
    private final List<Edge<T>> edges;

    private static class Edge<T> {
        T from;
        T to;
        Edge(T from, T to) {
            this.from = from;
            this.to = to;
        }
    }

    public IncidenceMatrixGraph() {
        this.vertices = new ArrayList<>();
        this.incidenceMatrix = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public void addVertex(T vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            for (List<Integer> row : incidenceMatrix) {
                row.add(0);
            }
            incidenceMatrix.add(new ArrayList<>());
            for (int i = 0; i < edges.size(); i++) {
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

        edges.removeIf(edge -> edge.from.equals(vertex) || edge.to.equals(vertex));

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
            Edge<T> newEdge = new Edge<>(from, to);
            edges.add(newEdge);

            for (int i = 0; i < vertices.size(); i++) {
                incidenceMatrix.get(i).add(0);
            }

            if (from.equals(to)) {
                incidenceMatrix.get(fromIndex).set(edges.size() - 1, 2); // петля
            } else {
                incidenceMatrix.get(fromIndex).set(edges.size() - 1, 1);
                incidenceMatrix.get(toIndex).set(edges.size() - 1, -1);
            }
        }
    }

    @Override
    public void removeEdge(T from, T to) {
        int edgeIndex = -1;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).from.equals(from) && edges.get(i).to.equals(to)) {
                edgeIndex = i;
                break;
            }
        }

        if (edgeIndex != -1) {
            edges.remove(edgeIndex);
            for (List<Integer> row : incidenceMatrix) {
                row.remove(edgeIndex);
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

        for (int i = 0; i < edges.size(); i++) {
            if (incidenceMatrix.get(vertexIndex).get(i) == 1) {
                neighbors.add(edges.get(i).to);
            } else if (incidenceMatrix.get(vertexIndex).get(i) == 2) {
                neighbors.add(vertex);
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
            for (int j = 0; j < edges.size(); j++) {
                sb.append(incidenceMatrix.get(i).get(j)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

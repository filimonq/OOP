package ru.nsu.fitkulin;

import java.util.List;

/**
 * interface for parametrized graph.
 */
public interface Graph<T> {
    void addVertex(T vertex);

    void removeVertex(T vertex);

    void addEdge(T from, T to);

    void removeEdge(T from, T to);

    List<T> getNeighbors(T vertex);
}
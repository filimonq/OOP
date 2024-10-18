package ru.nsu.fitkulin;

import java.io.IOException;
import java.util.*;

public interface Graph<T> {
    void addVertex(T vertex);

    void removeVertex(T vertex);

    void addEdge(T from, T to);

    void removeEdge(T from, T to);

    List<T> getNeighbors(T vertex);

    // void readFromFile(String filename) throws IOException;
}
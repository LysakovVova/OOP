
package ru.nsu.lysakov.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Реализация ориентированного графа с помощью матрицы смежности.
 */
public class AdjacencyMatrixGraph extends AbstractGraph {

    private boolean[][] matrix = new boolean[0][0];
    private final List<Integer> vertices = new ArrayList<>();
    private final Map<Integer, Integer> indices = new HashMap<>();

    /**
     * Добавляет вершину в граф, если она ещё не существует.
     * При добавлении увеличивает размер матрицы смежности.
     *
     * @param vertex номер добавляемой вершины
     */
    @Override
    public void addVertex(int vertex) {
        if (indices.containsKey(vertex)) {
            return;
        }

        int size = vertices.size();
        boolean[][] newMatrix = new boolean[size + 1][size + 1];

        for (int i = 0; i < size; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, size);
        }

        matrix = newMatrix;

        vertices.add(vertex);
        indices.put(vertex, size);
    }

    /**
     * Удаляет вершину из графа вместе со всеми связанными с ней рёбрами.
     * При удалении уменьшает матрицу и обновляет индексы вершин.
     * Если вершина отсутствует, граф не изменяется.
     *
     * @param vertex номер удаляемой вершины
     */
    @Override
    public void removeVertex(int vertex) {
        Integer index = indices.get(vertex);

        if (index == null) {
            return;
        }

        int size = vertices.size();
        boolean[][] newMatrix = new boolean[size - 1][size - 1];

        int newRow = 0;

        for (int i = 0; i < size; i++) {
            if (i == index) {
                continue;
            }

            int newCol = 0;

            for (int j = 0; j < size; j++) {
                if (j == index) {
                    continue;
                }

                newMatrix[newRow][newCol] = matrix[i][j];
                newCol++;
            }

            newRow++;
        }

        matrix = newMatrix;

        vertices.remove((int) index);

        indices.clear();

        for (int i = 0; i < vertices.size(); i++) {
            indices.put(vertices.get(i), i);
        }
    }

    /**
     * Добавляет ориентированное ребро между двумя вершинами.
     * Если ребро уже существует, граф не изменяется.
     *
     * @param from начальная вершина ребра
     * @param to конечная вершина ребра
     * @throws IllegalArgumentException если одна из вершин отсутствует
     */
    @Override
    public void addEdge(int from, int to) {
        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);

        matrix[fromIndex][toIndex] = true;
    }

    /**
     * Удаляет ориентированное ребро между двумя вершинами.
     * Если ребро отсутствует, граф не изменяется.
     *
     * @param from начальная вершина ребра
     * @param to конечная вершина ребра
     * @throws IllegalArgumentException если одна из вершин отсутствует
     */
    @Override
    public void removeEdge(int from, int to) {
        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);

        matrix[fromIndex][toIndex] = false;
    }

    /**
     * Возвращает множество вершин, в которые ведут рёбра из заданной вершины.
     *
     * @param vertex номер вершины
     * @return множество соседних вершин
     * @throws IllegalArgumentException если вершина отсутствует
     */
    @Override
    public Set<Integer> getNeighbors(int vertex) {
        int index = getIndex(vertex);

        Set<Integer> neighbors = new HashSet<>();

        for (int i = 0; i < vertices.size(); i++) {
            if (matrix[index][i]) {
                neighbors.add(vertices.get(i));
            }
        }

        return neighbors;
    }

    /**
     * Возвращает множество всех вершин графа.
     *
     * @return множество номеров вершин
     */
    @Override
    public Set<Integer> getVertices() {
        return Set.copyOf(vertices);
    }

    /**
     * Возвращает индекс заданной вершины в матрице смежности.
     *
     * @param vertex номер вершины
     * @return индекс вершины в матрице
     * @throws IllegalArgumentException если вершина отсутствует
     */
    private int getIndex(int vertex) {
        Integer index = indices.get(vertex);

        if (index == null) {
            throw new IllegalArgumentException(
                    "Vertex does not exist: " + vertex
            );
        }

        return index;
    }
}

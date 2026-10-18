
package ru.nsu.lysakov.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Реализация ориентированного графа с помощью списка смежности.
 * Для каждой вершины хранится множество смежных с ней вершин.
 */
public class AdjacencyListGraph extends AbstractGraph {

    private final Map<Integer, Set<Integer>> adjacency = new HashMap<>();

    /**
     * Добавляет вершину в граф, если она ещё не существует.
     *
     * @param vertex номер добавляемой вершины
     */
    @Override
    public void addVertex(int vertex) {
        adjacency.putIfAbsent(vertex, new HashSet<>());
    }

    /**
     * Удаляет вершину из графа вместе со всеми связанными с ней рёбрами.
     * Если вершина отсутствует, граф не изменяется.
     *
     * @param vertex номер удаляемой вершины
     */
    @Override
    public void removeVertex(int vertex) {
        adjacency.remove(vertex);

        for (Set<Integer> neighbors : adjacency.values()) {
            neighbors.remove(vertex);
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
        validateVertices(from, to);
        adjacency.get(from).add(to);
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
        validateVertices(from, to);
        adjacency.get(from).remove(to);
    }

    /**
     * Проверяет существование двух вершин в графе.
     *
     * @param from первая проверяемая вершина
     * @param to вторая проверяемая вершина
     * @throws IllegalArgumentException если одна из вершин отсутствует
     */
    private void validateVertices(int from, int to) {
        if (!adjacency.containsKey(from) || !adjacency.containsKey(to)) {
            throw new IllegalArgumentException("Vertex does not exist");
        }
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
        Set<Integer> neighbors = adjacency.get(vertex);

        if (neighbors == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        return Set.copyOf(neighbors);
    }

    /**
     * Возвращает множество всех вершин графа.
     *
     * @return множество номеров вершин
     */
    @Override
    public Set<Integer> getVertices() {
        return Set.copyOf(adjacency.keySet());
    }
}

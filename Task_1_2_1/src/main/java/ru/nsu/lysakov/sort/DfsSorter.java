
package ru.nsu.lysakov.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import ru.nsu.lysakov.graph.Graph;

/**
 * Реализация топологической сортировки графа с помощью DFS.
 * Обходит вершины в глубину и добавляет их в результат
 * после завершения обработки всех исходящих рёбер.
 */
public class DfsSorter implements TopologicalSorter {

    /**
     * Выполняет топологическую сортировку ориентированного графа.
     * Исходный граф при этом не изменяется.
     *
     * @param graph граф для сортировки
     * @return список вершин в топологическом порядке
     * @throws IllegalStateException если граф содержит цикл
     */
    @Override
    public List<Integer> sort(Graph graph) {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> processing = new HashSet<>();
        List<Integer> result = new ArrayList<>();

        for (int vertex : new TreeSet<>(graph.getVertices())) {
            if (!visited.contains(vertex)) {
                dfs(graph, vertex, visited, processing, result);
            }
        }

        Collections.reverse(result);
        return result;
    }

    /**
     * Выполняет рекурсивный обход графа в глубину.
     *
     * @param graph граф для обхода
     * @param vertex текущая вершина
     * @param visited множество полностью обработанных вершин
     * @param processing множество вершин в процессе обработки
     * @param result список обработанных вершин
     * @throws IllegalStateException если обнаружен цикл
     */
    private void dfs(Graph graph, int vertex,
                     Set<Integer> visited,
                     Set<Integer> processing,
                     List<Integer> result) {
        if (processing.contains(vertex)) {
            throw new IllegalStateException("Graph contains a cycle");
        }

        if (visited.contains(vertex)) {
            return;
        }

        processing.add(vertex);

        for (int neighbor : new TreeSet<>(graph.getNeighbors(vertex))) {
            dfs(graph, neighbor, visited, processing, result);
        }

        processing.remove(vertex);
        visited.add(vertex);
        result.add(vertex);
    }
}

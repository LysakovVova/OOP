package ru.nsu.lysakov.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import ru.nsu.lysakov.graph.Graph;

/**
 * Реализация топологической сортировки графа алгоритмом Кана.
 * Последовательно удаляет вершины с нулевой входящей степенью.
 * Если после обработки остались вершины, граф содержит цикл.
 */
public class KahnSorter implements TopologicalSorter {

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
        Map<Integer, Integer> inDegree = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int vertex : graph.getVertices()) {
            inDegree.put(vertex, 0);
        }

        for (int vertex : graph.getVertices()) {
            for (int neighbor : graph.getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        for (int vertex : graph.getVertices()) {
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            result.add(vertex);

            for (int neighbor : graph.getNeighbors(vertex)) {
                int degree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, degree);

                if (degree == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle");
        }

        return result;
    }
}
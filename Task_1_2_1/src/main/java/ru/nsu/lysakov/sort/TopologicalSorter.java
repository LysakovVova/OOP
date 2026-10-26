
package ru.nsu.lysakov.sort;

import java.util.List;
import ru.nsu.lysakov.graph.Graph;

/**
 * Интерфейс алгоритма топологической сортировки ориентированного графа.
 */
public interface TopologicalSorter {

    /**
     * Выполняет топологическую сортировку вершин ориентированного графа.
     *
     * @param graph граф для сортировки
     * @return список вершин в топологическом порядке
     * @throws IllegalStateException если граф содержит цикл
     */
    List<Integer> sort(Graph graph);
}

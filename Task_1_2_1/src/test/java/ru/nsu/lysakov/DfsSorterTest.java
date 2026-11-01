
package ru.nsu.lysakov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import ru.nsu.lysakov.graph.AdjacencyListGraph;
import ru.nsu.lysakov.graph.AdjacencyMatrixGraph;
import ru.nsu.lysakov.graph.Graph;
import ru.nsu.lysakov.graph.IncidenceMatrixGraph;
import ru.nsu.lysakov.sort.DfsSorter;
import ru.nsu.lysakov.sort.TopologicalSorter;

/**
 * Тесты топологической сортировки графа с помощью DFS.
 */
class DfsSorterTest {

    /**
     * Создаёт графы с тремя способами хранения.
     *
     * @return список реализаций графа
     */
    private List<Graph> createGraphs() {
        return List.of(
                new AdjacencyListGraph(),
                new AdjacencyMatrixGraph(),
                new IncidenceMatrixGraph()
        );
    }

    /**
     * Проверяет сортировку линейного графа.
     */
    @Test
    void linearGraphShouldBeSorted() {
        TopologicalSorter sorter = new DfsSorter();

        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addVertex(3);

            graph.addEdge(1, 2);
            graph.addEdge(2, 3);

            assertEquals(List.of(1, 2, 3), sorter.sort(graph));
        }
    }

    /**
     * Проверяет правильность сортировки графа с ветвлениями.
     */
    @Test
    void branchingGraphShouldBeSorted() {
        TopologicalSorter sorter = new DfsSorter();

        for (Graph graph : createGraphs()) {
            for (int i = 1; i <= 4; i++) {
                graph.addVertex(i);
            }

            graph.addEdge(1, 2);
            graph.addEdge(1, 3);
            graph.addEdge(2, 4);
            graph.addEdge(3, 4);

            List<Integer> result = sorter.sort(graph);

            assertEquals(Set.of(1, 2, 3, 4), new HashSet<>(result));
            assertEquals(4, result.size());

            for (int vertex : graph.getVertices()) {
                for (int neighbor : graph.getNeighbors(vertex)) {
                    assertTrue(
                            result.indexOf(vertex)
                                    < result.indexOf(neighbor)
                    );
                }
            }
        }
    }

    /**
     * Проверяет обнаружение цикла в графе.
     */
    @Test
    void cyclicGraphShouldThrowException() {
        TopologicalSorter sorter = new DfsSorter();

        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addVertex(3);

            graph.addEdge(1, 2);
            graph.addEdge(2, 3);
            graph.addEdge(3, 1);

            assertThrows(IllegalStateException.class,
                    () -> sorter.sort(graph));
        }
    }

    /**
     * Проверяет обнаружение цикла из одной вершины.
     */
    @Test
    void selfLoopShouldThrowException() {
        TopologicalSorter sorter = new DfsSorter();

        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addEdge(1, 1);

            assertThrows(IllegalStateException.class,
                    () -> sorter.sort(graph));
        }
    }

    /**
     * Проверяет сортировку пустого графа.
     */
    @Test
    void emptyGraphShouldReturnEmptyList() {
        TopologicalSorter sorter = new DfsSorter();

        for (Graph graph : createGraphs()) {
            assertEquals(List.of(), sorter.sort(graph));
        }
    }

    /**
     * Проверяет сортировку графа без рёбер.
     */
    @Test
    void isolatedVerticesShouldBeSorted() {
        TopologicalSorter sorter = new DfsSorter();

        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addVertex(3);

            List<Integer> result = sorter.sort(graph);

            assertEquals(Set.of(1, 2, 3),
                    new HashSet<>(result));
            assertEquals(3, result.size());
        }
    }

    /**
     * Проверяет, что сортировка не изменяет исходный граф.
     */
    @Test
    void sortingShouldNotModifyGraph() {
        TopologicalSorter sorter = new DfsSorter();

        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addEdge(1, 2);

            sorter.sort(graph);

            assertEquals(Set.of(1, 2), graph.getVertices());
            assertEquals(Set.of(2), graph.getNeighbors(1));
            assertEquals(Set.of(), graph.getNeighbors(2));
        }
    }
}


package ru.nsu.lysakov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.lysakov.graph.AdjacencyListGraph;
import ru.nsu.lysakov.graph.AdjacencyMatrixGraph;
import ru.nsu.lysakov.graph.Graph;
import ru.nsu.lysakov.graph.IncidenceMatrixGraph;

/**
 * Тесты для различных представлений ориентированного графа.
 */
class GraphTest {

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
     * Проверяет добавление вершин и рёбер.
     */
    @Test
    void verticesAndEdgesShouldBeAdded() {
        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addVertex(3);

            graph.addEdge(1, 2);
            graph.addEdge(1, 3);

            assertEquals(Set.of(1, 2, 3), graph.getVertices());
            assertEquals(Set.of(2, 3), graph.getNeighbors(1));
            assertEquals(Set.of(), graph.getNeighbors(2));
        }
    }

    /**
     * Проверяет удаление ребра.
     */
    @Test
    void edgeShouldBeRemoved() {
        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addEdge(1, 2);

            graph.removeEdge(1, 2);

            assertEquals(Set.of(), graph.getNeighbors(1));
            assertEquals(Set.of(1, 2), graph.getVertices());
        }
    }

    /**
     * Проверяет удаление вершины и всех связанных с ней рёбер.
     */
    @Test
    void vertexAndItsEdgesShouldBeRemoved() {
        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addVertex(3);

            graph.addEdge(1, 2);
            graph.addEdge(2, 3);
            graph.addEdge(3, 2);
            graph.addEdge(1, 3);

            graph.removeVertex(2);

            assertEquals(Set.of(1, 3), graph.getVertices());
            assertEquals(Set.of(3), graph.getNeighbors(1));
            assertEquals(Set.of(), graph.getNeighbors(3));
        }
    }

    /**
     * Проверяет отсутствие дубликатов вершин и рёбер.
     */
    @Test
    void duplicateVerticesAndEdgesShouldBeIgnored() {
        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(1);
            graph.addVertex(2);

            graph.addEdge(1, 2);
            graph.addEdge(1, 2);

            assertEquals(Set.of(1, 2), graph.getVertices());
            assertEquals(Set.of(2), graph.getNeighbors(1));
        }
    }

    /**
     * Проверяет повторное удаление отсутствующих элементов.
     */
    @Test
    void removingMissingElementsShouldNotChangeGraph() {
        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(2);

            graph.removeEdge(1, 2);
            graph.removeVertex(3);
            graph.removeVertex(3);

            assertEquals(Set.of(1, 2), graph.getVertices());
            assertEquals(Set.of(), graph.getNeighbors(1));
        }
    }

    /**
     * Проверяет исключения при обращении к отсутствующим вершинам.
     */
    @Test
    void missingVertexShouldThrowException() {
        for (Graph graph : createGraphs()) {
            graph.addVertex(1);

            assertThrows(IllegalArgumentException.class,
                    () -> graph.addEdge(1, 2));

            assertThrows(IllegalArgumentException.class,
                    () -> graph.removeEdge(1, 2));

            assertThrows(IllegalArgumentException.class,
                    () -> graph.getNeighbors(2));
        }
    }

    /**
     * Проверяет поддержку петель в графе.
     */
    @Test
    void selfLoopShouldBeSupported() {
        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addEdge(1, 1);

            assertEquals(Set.of(1), graph.getNeighbors(1));

            graph.removeEdge(1, 1);

            assertEquals(Set.of(), graph.getNeighbors(1));
        }
    }

    /**
     * Проверяет равенство графов с разными способами хранения.
     */
    @Test
    void differentRepresentationsShouldBeEqual() {
        List<Graph> graphs = createGraphs();

        for (Graph graph : graphs) {
            graph.addVertex(3);
            graph.addVertex(1);
            graph.addVertex(2);

            graph.addEdge(1, 2);
            graph.addEdge(2, 3);
        }

        for (Graph first : graphs) {
            for (Graph second : graphs) {
                assertEquals(first, second);
                assertEquals(first.hashCode(), second.hashCode());
            }
        }
    }

    /**
     * Проверяет неравенство графов с разными рёбрами.
     */
    @Test
    void graphsWithDifferentEdgesShouldNotBeEqual() {
        Graph first = new AdjacencyListGraph();
        Graph second = new AdjacencyMatrixGraph();

        for (Graph graph : List.of(first, second)) {
            graph.addVertex(1);
            graph.addVertex(2);
        }

        first.addEdge(1, 2);

        assertNotEquals(first, second);
        assertNotEquals(second, first);
    }

    /**
     * Проверяет строковое представление графа.
     */
    @Test
    void graphShouldHaveCorrectStringRepresentation() {
        for (Graph graph : createGraphs()) {
            graph.addVertex(3);
            graph.addVertex(1);
            graph.addVertex(2);

            graph.addEdge(1, 3);
            graph.addEdge(1, 2);

            assertEquals(
                    "{1=[2, 3], 2=[], 3=[]}",
                    graph.toString()
            );
        }
    }

    /**
     * Проверяет чтение графа из файла.
     *
     * @param tempDir временный каталог для тестового файла
     * @throws IOException если произошла ошибка работы с файлом
     */
    @Test
    void graphShouldBeReadFromFile(@TempDir Path tempDir)
            throws IOException {
        Path file = tempDir.resolve("graph.txt");

        Files.writeString(file, "3 2\n0 1\n1 2\n");

        for (Graph graph : createGraphs()) {
            graph.addVertex(10);

            graph.readFromFile(file.toString());

            assertEquals(Set.of(0, 1, 2), graph.getVertices());
            assertEquals(Set.of(1), graph.getNeighbors(0));
            assertEquals(Set.of(2), graph.getNeighbors(1));
            assertEquals(Set.of(), graph.getNeighbors(2));
        }
    }

    /**
     * Проверяет обработку некорректного количества вершин.
     *
     * @param tempDir временный каталог для тестового файла
     * @throws IOException если произошла ошибка работы с файлом
     */
    @Test
    void invalidGraphSizeShouldThrowException(@TempDir Path tempDir)
            throws IOException {
        Path file = tempDir.resolve("invalid.txt");

        Files.writeString(file, "-1 2\n0 1\n");

        for (Graph graph : createGraphs()) {
            assertThrows(IllegalArgumentException.class,
                    () -> graph.readFromFile(file.toString()));
        }
    }

    /**
     * Проверяет чтение несуществующего файла.
     *
     * @param tempDir временный каталог для тестового файла
     */
    @Test
    void missingFileShouldThrowException(@TempDir Path tempDir) {
        Path file = tempDir.resolve("missing.txt");

        for (Graph graph : createGraphs()) {
            assertThrows(IOException.class,
                    () -> graph.readFromFile(file.toString()));
        }
    }

    /**
     * Проверяет, что рёбра ориентированы.
     */
    @Test
    void edgesShouldBeDirected() {
        for (Graph graph : createGraphs()) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addEdge(1, 2);

            assertTrue(graph.getNeighbors(1).contains(2));
            assertEquals(Set.of(), graph.getNeighbors(2));
        }
    }
}

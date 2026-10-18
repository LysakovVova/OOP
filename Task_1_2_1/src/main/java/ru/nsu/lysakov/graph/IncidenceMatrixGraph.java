
package ru.nsu.lysakov.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Реализация ориентированного графа с помощью матрицы инцидентности.
 * Строки матрицы соответствуют вершинам, а столбцы — рёбрам.
 * Значение 1 обозначает начало ребра, -1 — конец ребра,
 * 2 — петлю, 0 — отсутствие связи с ребром.
 */
public class IncidenceMatrixGraph extends AbstractGraph {

    private int[][] matrix = new int[0][0];

    private final List<Integer> vertices = new ArrayList<>();

    /**
     * Добавляет вершину в граф, если она ещё не существует.
     * При добавлении увеличивает количество строк матрицы.
     *
     * @param vertex номер добавляемой вершины
     */
    @Override
    public void addVertex(int vertex) {
        if (vertices.contains(vertex)) {
            return;
        }

        int rows = vertices.size();
        int cols = getEdgeCount();

        int[][] newMatrix = new int[rows + 1][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, cols);
        }

        matrix = newMatrix;
        vertices.add(vertex);
    }

    /**
     * Удаляет вершину вместе со всеми связанными с ней рёбрами.
     * При удалении исключает соответствующую строку матрицы
     * и столбцы всех рёбер, содержащих данную вершину.
     * Если вершина отсутствует, граф не изменяется.
     *
     * @param vertex номер удаляемой вершины
     */
    @Override
    public void removeVertex(int vertex) {
        int index = vertices.indexOf(vertex);

        if (index == -1) {
            return;
        }

        int rows = vertices.size();
        int cols = getEdgeCount();

        int removedEdges = 0;

        for (int j = 0; j < cols; j++) {
            if (matrix[index][j] != 0) {
                removedEdges++;
            }
        }

        int[][] newMatrix = new int[rows - 1][cols - removedEdges];

        int newRow = 0;

        for (int i = 0; i < rows; i++) {
            if (i == index) {
                continue;
            }

            int newCol = 0;

            for (int j = 0; j < cols; j++) {
                if (matrix[index][j] == 0) {
                    newMatrix[newRow][newCol++] = matrix[i][j];
                }
            }

            newRow++;
        }

        matrix = newMatrix;
        vertices.remove(index);
    }

    /**
     * Добавляет ориентированное ребро между двумя вершинами.
     * Для нового ребра создаётся дополнительный столбец матрицы.
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

        if (findEdge(fromIndex, toIndex) != -1) {
            return;
        }

        int rows = vertices.size();
        int cols = getEdgeCount();

        int[][] newMatrix = new int[rows][cols + 1];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, cols);
        }

        if (fromIndex == toIndex) {
            newMatrix[fromIndex][cols] = 2;
        } else {
            newMatrix[fromIndex][cols] = 1;
            newMatrix[toIndex][cols] = -1;
        }

        matrix = newMatrix;
    }

    /**
     * Удаляет ориентированное ребро между двумя вершинами.
     * При удалении исключает соответствующий столбец матрицы.
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

        int edge = findEdge(fromIndex, toIndex);

        if (edge == -1) {
            return;
        }

        int rows = vertices.size();
        int cols = getEdgeCount();

        int[][] newMatrix = new int[rows][cols - 1];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, edge);

            System.arraycopy(
                    matrix[i], edge + 1,
                    newMatrix[i], edge, cols - edge - 1
            );
        }

        matrix = newMatrix;
    }

    /**
     * Возвращает множество вершин, в которые ведут рёбра
     * из заданной вершины.
     * Поиск выполняется по столбцам матрицы инцидентности.
     *
     * @param vertex номер вершины
     * @return множество соседних вершин
     * @throws IllegalArgumentException если вершина отсутствует
     */
    @Override
    public Set<Integer> getNeighbors(int vertex) {
        int index = getIndex(vertex);

        Set<Integer> neighbors = new HashSet<>();

        for (int j = 0; j < getEdgeCount(); j++) {
            if (matrix[index][j] == 2) {
                neighbors.add(vertex);
            } else if (matrix[index][j] == 1) {
                for (int i = 0; i < vertices.size(); i++) {
                    if (matrix[i][j] == -1) {
                        neighbors.add(vertices.get(i));
                        break;
                    }
                }
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
     * Возвращает индекс заданной вершины в матрице инцидентности.
     *
     * @param vertex номер вершины
     * @return индекс строки матрицы, соответствующей вершине
     * @throws IllegalArgumentException если вершина отсутствует
     */
    private int getIndex(int vertex) {
        int index = vertices.indexOf(vertex);

        if (index == -1) {
            throw new IllegalArgumentException(
                    "Vertex does not exist: " + vertex
            );
        }

        return index;
    }

    /**
     * Возвращает количество рёбер графа.
     * Количество рёбер соответствует числу столбцов матрицы.
     *
     * @return количество рёбер графа
     */
    private int getEdgeCount() {
        if (vertices.isEmpty()) {
            return 0;
        }

        return matrix[0].length;
    }

    /**
     * Находит столбец матрицы, соответствующий заданному ребру.
     * Учитывает направление ребра и возможность наличия петли.
     *
     * @param fromIndex индекс начальной вершины в матрице
     * @param toIndex индекс конечной вершины в матрице
     * @return индекс столбца ребра или -1, если ребро отсутствует
     */
    private int findEdge(int fromIndex, int toIndex) {
        for (int j = 0; j < getEdgeCount(); j++) {
            if (fromIndex == toIndex) {
                if (matrix[fromIndex][j] == 2) {
                    return j;
                }
            } else if (matrix[fromIndex][j] == 1
                    && matrix[toIndex][j] == -1) {
                return j;
            }
        }

        return -1;
    }
}

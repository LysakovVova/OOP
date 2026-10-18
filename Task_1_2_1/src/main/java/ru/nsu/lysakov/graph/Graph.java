
package ru.nsu.lysakov.graph;

import java.io.IOException;
import java.util.Set;

/**
 * Интерфейс ориентированного графа.
 * Определяет основные операции с вершинами и рёбрами графа.
 * Не зависит от способа внутреннего представления графа.
 */
public interface Graph {

    /**
     * Добавляет вершину в граф.
     * Если вершина уже существует, граф не изменяется.
     *
     * @param vertex номер добавляемой вершины
     */
    void addVertex(int vertex);

    /**
     * Удаляет вершину вместе со всеми связанными с ней рёбрами.
     * Если вершина отсутствует, граф не изменяется.
     *
     * @param vertex номер удаляемой вершины
     */
    void removeVertex(int vertex);

    /**
     * Добавляет ориентированное ребро между двумя вершинами.
     * Если ребро уже существует, граф не изменяется.
     *
     * @param from начальная вершина ребра
     * @param to конечная вершина ребра
     * @throws IllegalArgumentException если одна из вершин отсутствует
     */
    void addEdge(int from, int to);

    /**
     * Удаляет ориентированное ребро между двумя вершинами.
     * Если ребро отсутствует, граф не изменяется.
     *
     * @param from начальная вершина ребра
     * @param to конечная вершина ребра
     * @throws IllegalArgumentException если одна из вершин отсутствует
     */
    void removeEdge(int from, int to);

    /**
     * Возвращает множество вершин, в которые ведут рёбра
     * из заданной вершины.
     *
     * @param vertex номер вершины
     * @return множество соседних вершин
     * @throws IllegalArgumentException если вершина отсутствует
     */
    Set<Integer> getNeighbors(int vertex);

    /**
     * Возвращает множество всех вершин графа.
     *
     * @return множество номеров вершин
     */
    Set<Integer> getVertices();

    /**
     * Считывает граф из файла фиксированного формата.
     * Первая строка содержит количество вершин и рёбер.
     * Следующие строки содержат пары вершин, задающие рёбра.
     * Вершины нумеруются от 0 до n - 1.
     *
     * @param filename путь к файлу с графом
     * @throws IOException если файл не удалось открыть
     * @throws IllegalArgumentException если данные графа некорректны
     */
    void readFromFile(String filename) throws IOException;
}

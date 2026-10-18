
package ru.nsu.lysakov.graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Абстрактный класс ориентированного графа.
 * Содержит общие методы, не зависящие от способа хранения графа.
 */
public abstract class AbstractGraph implements Graph {

    /**
     * Сравнивает два графа по множеству вершин и их соседям.
     *
     * @param obj объект для сравнения
     * @return true, если графы равны, иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Graph other)) {
            return false;
        }

        if (!getVertices().equals(other.getVertices())) {
            return false;
        }

        for (int vertex : getVertices()) {
            if (!getNeighbors(vertex).equals(other.getNeighbors(vertex))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Вычисляет хеш-код графа на основе его вершин и рёбер.
     *
     * @return хеш-код графа
     */
    @Override
    public int hashCode() {
        Map<Integer, Set<Integer>> representation = new HashMap<>();

        for (int vertex : getVertices()) {
            representation.put(vertex, getNeighbors(vertex));
        }

        return representation.hashCode();
    }

    /**
     * Возвращает строковое представление графа.
     * Вершины и их соседи располагаются по возрастанию.
     *
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        Map<Integer, Set<Integer>> representation = new TreeMap<>();

        for (int vertex : getVertices()) {
            representation.put(
                    vertex,
                    new TreeSet<>(getNeighbors(vertex))
            );
        }

        return representation.toString();
    }

    /**
     * Считывает граф из файла.
     * Первая строка содержит количество вершин и рёбер.
     * Последующие строки содержат пары вершин, задающие рёбра.
     * Вершины нумеруются от 0 до n - 1.
     *
     * @param filename путь к файлу с графом
     * @throws IOException если файл не удалось открыть
     * @throws IllegalArgumentException если размеры графа некорректны
     */
    @Override
    public void readFromFile(String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();

            if (n < 0 || m < 0) {
                throw new IllegalArgumentException("Invalid graph size");
            }

            for (int vertex : new ArrayList<>(getVertices())) {
                removeVertex(vertex);
            }

            for (int i = 0; i < n; i++) {
                addVertex(i);
            }

            for (int i = 0; i < m; i++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();

                addEdge(from, to);
            }
        }
    }
}

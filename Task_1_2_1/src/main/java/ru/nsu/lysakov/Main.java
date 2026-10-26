package ru.nsu.lysakov;

import java.io.IOException;
import ru.nsu.lysakov.graph.IncidenceMatrixGraph;
import ru.nsu.lysakov.graph.Graph;
import ru.nsu.lysakov.sort.DfsSorter;
import ru.nsu.lysakov.sort.TopologicalSorter;

/**
 * Главный класс программы.
 */
public class Main {

    /**
     * Точка входа в программу+.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Graph graph = new IncidenceMatrixGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        System.out.println(graph);

        TopologicalSorter sorter = new DfsSorter();

        System.out.println(sorter.sort(graph));
        System.out.println(graph.sorted(new DfsSorter()));


        try {
            graph.readFromFile("src/main/java/ru/nsu/lysakov/input.txt");

            System.out.println(graph);

            System.out.println(sorter.sort(graph));

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Ошибка графа: " + e.getMessage());
        }
    }
}
package ru.nsu.lysakov;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Пример запуска поиска подстроки в файле.
 */
public class Main {

    private Main() {
    }

    /**
     * Выводит индексы всех вхождений подстроки.
     *
     * @param args аргументы командной строки не используются
     * @throws IOException при ошибке чтения файла
     */
    public static void main(String[] args) throws IOException {
        String filename = "input.txt";
        String substring = "cad";

        // Позволяет запускать и из папки задачи, и из корня репозитория в IDE.
        Path file = Path.of(filename);

        List<Long> matches = SubstringSearch.find(file.toString(), substring);
        System.out.println(matches);
    }
}

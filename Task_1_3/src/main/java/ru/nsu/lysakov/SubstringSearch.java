package ru.nsu.lysakov;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Потоковый поиск подстроки в UTF-8 файле алгоритмом Кнута — Морриса — Пратта.
 */
public class SubstringSearch {

    private SubstringSearch() {
    }

    /**
     * Возвращает индексы всех вхождений, включая пересекающиеся.
     * Индексы считаются с нуля в кодовых точках Unicode, а не в байтах или char.
     * Время работы — O(n + m), память — O(m + k), где n — длина файла,
     * m — длина подстроки, k — количество совпадений.
     *
     * @param filename имя файла в кодировке UTF-8
     * @param substring непустая искомая подстрока
     * @return индексы вхождений в порядке возрастания
     * @throws IOException при ошибке чтения или некорректном UTF-8
     * @throws IllegalArgumentException если подстрока пуста
     */
    public static List<Long> find(String filename, String substring) throws IOException {
        Objects.requireNonNull(filename, "Имя файла не должно быть null");
        Objects.requireNonNull(substring, "Подстрока не должна быть null");
        int[] pattern = substring.codePoints().toArray();
        if (pattern.length == 0) {
            throw new IllegalArgumentException("Подстрока не должна быть пустой");
        }
        int[] prefix = prefixFunction(pattern);
        List<Long> matches = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(filename), StandardCharsets.UTF_8)) {
            long position = 0;
            int matched = 0;
            int symbol;
            while ((symbol = reader.read()) != -1) {
                // UTF-8 декодер выдаёт символы вне BMP как суррогатные пары.
                if (Character.isHighSurrogate((char) symbol)) {
                    symbol = Character.toCodePoint((char) symbol, (char) reader.read());
                }
                while (matched > 0 && symbol != pattern[matched]) {
                    matched = prefix[matched - 1];
                }
                if (symbol == pattern[matched]) {
                    matched++;
                }
                if (matched == pattern.length) {
                    matches.add(position - pattern.length + 1);
                    matched = prefix[matched - 1];
                }
                position++;
            }
        }
        return matches;
    }

    private static int[] prefixFunction(int[] pattern) {
        int[] prefix = new int[pattern.length];
        for (int i = 1; i < pattern.length; i++) {
            int matched = prefix[i - 1];
            while (matched > 0 && pattern[i] != pattern[matched]) {
                matched = prefix[matched - 1];
            }
            if (pattern[i] == pattern[matched]) {
                matched++;
            }
            prefix[i] = matched;
        }
        return prefix;
    }
}

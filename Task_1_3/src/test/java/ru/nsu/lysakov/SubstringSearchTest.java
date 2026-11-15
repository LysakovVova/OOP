package ru.nsu.lysakov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты потокового поиска подстроки.
 */
class SubstringSearchTest {
    @TempDir
    Path directory;

    private String write(String text) throws IOException {
        Path file = directory.resolve("input.txt");
        Files.writeString(file, text, StandardCharsets.UTF_8);
        return file.toString();
    }

    /** Проверяет пример из условия. */
    @Test
    void shouldFindExample() throws IOException {
        assertEquals(List.of(1L, 8L), SubstringSearch.find(write("абракадабра"), "бра"));
    }

    /** Проверяет вхождения на обоих концах файла и пересечения. */
    @Test
    void shouldFindOverlappingMatches() throws IOException {
        assertEquals(List.of(0L, 1L, 2L), SubstringSearch.find(write("ааааа"), "ааа"));
        assertEquals(List.of(0L), SubstringSearch.find(write("текст"), "текст"));
        assertEquals(List.of(0L, 1L, 2L), SubstringSearch.find(write("яяя"), "я"));
    }

    /** Проверяет случаи без совпадений. */
    @Test
    void shouldReturnEmptyListWithoutMatches() throws IOException {
        assertEquals(List.of(), SubstringSearch.find(write(""), "а"));
        assertEquals(List.of(), SubstringSearch.find(write("аб"), "абв"));
        assertEquals(List.of(), SubstringSearch.find(write("АБВ"), "абв"));
        assertEquals(List.of(), SubstringSearch.find(write("абв"), "г"));
    }

    /** Проверяет алфавиты с разной длиной UTF-8 представления. */
    @Test
    void shouldSupportDifferentAlphabets() throws IOException {
        String file = write("abc Ελληνικά 中文 العربية 中文");
        assertEquals(List.of(13L, 24L), SubstringSearch.find(file, "中文"));
        assertEquals(List.of(16L), SubstringSearch.find(file, "العربية"));
        assertEquals(List.of(4L), SubstringSearch.find(file, "Ελληνικά"));
    }

    /** Проверяет индексы кодовых точек для символов вне BMP. */
    @Test
    void shouldCountSupplementaryCharactersOnce() throws IOException {
        String file = write("😀𐐀я😀𐐀я");
        assertEquals(List.of(1L, 4L), SubstringSearch.find(file, "𐐀я"));
        assertEquals(List.of(0L, 3L), SubstringSearch.find(file, "😀"));
    }

    /** Проверяет совпадение с переводами строк и пробелами. */
    @Test
    void shouldPreserveWhitespace() throws IOException {
        assertEquals(List.of(1L, 6L),
                SubstringSearch.find(write("а \r\nба \r\nб"), " \r\nб"));
    }

    /** Проверяет совпадения и суррогатную пару на границе буфера чтения. */
    @Test
    void shouldMatchAcrossBufferBoundaries() throws IOException {
        String padding = "а".repeat(8191);
        String file = write(padding + "😀бра" + padding + "😀бра");
        assertEquals(List.of(8191L, 16386L), SubstringSearch.find(file, "😀бра"));
        assertEquals(List.of(8192L, 16387L), SubstringSearch.find(file, "бра"));
    }

    /** Проверяет подстроку, которая длиннее буфера. */
    @Test
    void shouldSupportLongPattern() throws IOException {
        String pattern = "а".repeat(20000) + "б";
        assertEquals(List.of(10000L),
                SubstringSearch.find(write("а".repeat(10000) + pattern), pattern));
    }

    /** Проверяет переходы по префиксам после частичного совпадения. */
    @Test
    void shouldRecoverAfterPartialMatch() throws IOException {
        assertEquals(List.of(4L, 11L),
                SubstringSearch.find(write("ababababacaababaca"), "ababaca"));
        assertEquals(List.of(4L), SubstringSearch.find(write("aaaaab"), "ab"));
    }

    /** Проверяет список всех вхождений подстроки. */
    @Test
    void shouldReturnAllMatches() throws IOException {
        List<Long> matches = SubstringSearch.find(write("бананана"), "ана");
        assertEquals(List.of(1L, 3L, 5L), matches);
    }

    /** Проверяет отклонение пустой подстроки и null аргументов. */
    @Test
    void shouldRejectInvalidArguments() throws IOException {
        String file = write("текст");
        assertThrows(IllegalArgumentException.class, () -> SubstringSearch.find(file, ""));
        assertThrows(NullPointerException.class, () -> SubstringSearch.find(file, null));
        assertThrows(NullPointerException.class, () -> SubstringSearch.find(null, "а"));
    }

    /** Проверяет передачу ошибок чтения вызывающему коду. */
    @Test
    void shouldReportFileErrors() throws IOException {
        assertThrows(NoSuchFileException.class,
                () -> SubstringSearch.find(directory.resolve("missing.txt").toString(), "а"));
        Path file = directory.resolve("invalid.txt");
        Files.write(file, new byte[]{(byte) 0xc3, 0x28});
        assertThrows(MalformedInputException.class,
                () -> SubstringSearch.find(file.toString(), "а"));
    }

    /** Сравнивает поиск с независимым перебором на случайных строках Unicode. */
    @Test
    void shouldMatchNaiveSearch() throws IOException {
        Random random = new Random(42);
        int[] alphabet = {'a', 'б', '中', 0x1f600, 0x10400};
        for (int attempt = 0; attempt < 200; attempt++) {
            int[] text = random.ints(100, 0, alphabet.length).map(i -> alphabet[i]).toArray();
            int length = 1 + random.nextInt(8);
            int start = random.nextInt(text.length - length + 1);
            String pattern = new String(text, start, length);
            List<Long> expected = new ArrayList<>();
            for (int i = 0; i <= text.length - length; i++) {
                if (new String(text, i, length).equals(pattern)) {
                    expected.add((long) i);
                }
            }
            assertEquals(expected,
                    SubstringSearch.find(write(new String(text, 0, text.length)), pattern));
        }
    }

    /** Генерирует файл 96 МиБ при ограничении кучи тестового JVM до 64 МиБ. */
    @Test
    void shouldSearchFileLargerThanHeap() throws IOException {
        Path file = directory.resolve("large.txt");
        String block = "я".repeat(8192);
        int blocks = 6144;
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (int i = 0; i < blocks; i++) {
                writer.write(block);
            }
            writer.write("конец😀");
        }
        assertTrue(Files.size(file) > Runtime.getRuntime().maxMemory());
        long expected = (long) block.length() * blocks;
        assertEquals(List.of(expected), SubstringSearch.find(file.toString(), "конец😀"));
    }
}

package ru.nsu.lysakov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Тест запуска программы без аргументов.
 */
class MainTest {
    private String run(String... args) throws IOException {
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream stream = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(stream);
            Main.main(args);
        } finally {
            System.setOut(original);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    /** Проверяет запуск примера без аргументов командной строки. */
    @Test
    void shouldPrintMatches() throws IOException {
        assertEquals("[4]", run());
    }
}

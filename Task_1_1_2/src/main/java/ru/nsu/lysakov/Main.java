package ru.nsu.lysakov;

import java.util.Scanner;
import ru.nsu.lysakov.game.Game;

/**
 * Главный класс, который запускает игру.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Game game = new Game();
        // Запуск игрового цикла.
        while (true) {
            game.nextStep();
        }
    }
}
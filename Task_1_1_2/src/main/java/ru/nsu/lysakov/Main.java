package ru.nsu.lysakov;

import java.util.Scanner;

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
        // Создание сканера для ввода количества колод.
        Scanner scanner = new Scanner(System.in);
        int count;

        // Ввод количества колод для игры.
        while (true) {
            System.out.println(
                    "Введите количество колод для игры (от 1 до 10):"
            );

            String s = scanner.nextLine();

            try {
                count = Integer.parseInt(s);

                if (count < 1 || count > 10) {
                    System.out.println("Введите число от 1 до 10.");
                    continue;
                }

                System.out.println("Количество колод: " + count);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести целое число.");
            }
        }

        // Создание игры с указанным количеством колод.
        Game game = new Game(count);

        // Запуск игрового цикла.
        while (true) {
            game.nextStep();
        }
    }
}
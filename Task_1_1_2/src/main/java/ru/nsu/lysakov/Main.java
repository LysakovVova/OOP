package ru.nsu.lysakov;
import java.util.Scanner;



/// Главный класс, который запускает игру
public class Main {
    public static void main(String[] args) {
        /// создание сканера для ввода количества колод
        Scanner scanner = new Scanner(System.in);
        int count;

        /// ввод количества колод для игры
        while (true) {
            System.out.println("Введите количество колод для игры(от 1 до 10):");

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

        /// создание игры с указанным количеством колод
        Game game;
        game = new Game(count);

        /// запуск игрового цикла
        while (true) {
            game.nextStep();
        }
    }
}
package ru.nsu.lysakov.ui;

import java.util.Scanner;

/**
 * Класс для получения ввода от пользователя.
 */
public class Input {
    private final Scanner scanner = new Scanner(System.in);
    private final Console console = new Console();

    /**
     * Возвращает действие игрока.
     *
     * @return 0 для остановки, 1 для взятия карты, -1 при ошибке
     */
    public int getPlayerAction() {

        console.printUserAction();
        String input = scanner.nextLine();

        if (input.equals("0")) {
            return 0;
        }

        if (input.equals("1")) {
            return 1;
        }

        return -1;
    }

    /**
     * Возвращает количество колод для новой игры.
     *
     * @return количество колод от 1 до 10, либо -1 при неверном вводе
     */
    public int getInitGameAction() {
        console.printInitGame();
        String input = scanner.nextLine();

        try {
            int cnt = Integer.parseInt(input);

            if (cnt < 1 || cnt > 10) {
                return -1;
            }

            return cnt;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Ожидает нажатия Enter.
     */
    public void waitEnter() {
        console.printActionEnter();
        scanner.nextLine();
    }
}

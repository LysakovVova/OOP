package ru.nsu.lysakov.ui;

import java.util.Scanner;

/**
 * Класс для получения ввода от пользователя.
 */
public class Input {
    private final Scanner scanner = new Scanner(System.in);
    private final Console console = new Console();

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

    public void waitEnter() {
        console.printActionEnter();
        scanner.nextLine();
    }
}

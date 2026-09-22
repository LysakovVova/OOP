package ru.nsu.lysakov.ui;

import ru.nsu.lysakov.game.GameScore;
import ru.nsu.lysakov.players.*;

/**
 * Класс для печати информации в консоль.
 */
public class Console {

    public void clear() {
        for (int i = 0; i < 100; ++i) {
            System.out.println();
        }
    }

    public void printState(Player player, Dealer dealer) {
        System.out.println("- Вы");
        System.out.print("\t");
        player.print();
        System.out.println("\n\tСумма карт: " + player.getNominal());

        System.out.println("- Диллер");
        System.out.print("\t");
        dealer.print();
        System.out.println("\n\tСумма карт: " + dealer.getNominal());
    }

    public void printBadUserInput() {
        System.out.println("Вы ввели не 0 или 1, повторите попытку.");
    }

    public void printUserAction() {
        System.out.println("0 - Закончить набор карт");
        System.out.println("1 - Взять ещё одну карту");
    }

    private void printScore(GameScore score) {
        if (score.getPlayerScore() > score.getDealerScore()) {
            System.out.print("Счёт стал равным ");
            System.out.print(score.getPlayerScore());
            System.out.print(" : ");
            System.out.print(score.getDealerScore());
            System.out.println(" в вашу пользу");
        }
        else if (score.getDealerScore() > score.getPlayerScore()) {
            System.out.print("Счёт стал равным ");
            System.out.print(score.getDealerScore());
            System.out.print(" : ");
            System.out.print(score.getPlayerScore());
            System.out.println(" в пользу диллера");
        } else {
            System.out.print("Счёт стал равным ");
            System.out.print(score.getDealerScore());
            System.out.print(" : ");
            System.out.print(score.getPlayerScore());
            System.out.println(" пока что ничья");
        }
    }

    public void printPlayerWin(GameScore score) {
        System.out.println("Вы выиграли раунд!");
        printScore(score);
    }

    public void printDealerWin(GameScore score) {
        System.out.println("Вы проиграли раунд!");
        printScore(score);
    }

    public void printDraw(GameScore score) {
        System.out.println("Ничья!");
        printScore(score);
    }

    public void printActionEnter() {
        System.out.println("Нажмите Enter для продолжения");
    }

    public void printInitGame() {
        System.out.println("Введите количество колод для игры (от 1 до 10):");
    }
    public void printBadInitGame() {
        System.out.println("Введите число от 1 до 10.");
    }
}
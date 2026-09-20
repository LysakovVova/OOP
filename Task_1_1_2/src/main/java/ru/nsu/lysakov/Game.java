package ru.nsu.lysakov;

import java.util.Random;
import java.util.Scanner;

/**
 * Класс для управления игрой.
 */
public class Game {
    private int cntDeck;
    private int sizeDecks;
    private Deck[] decks;
    private Gambler diller;
    private Gambler user;
    private final Random random = new Random();
    private GameStateClass state;
    private Myconsole console;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Выдача карты из колод.
     *
     * @return карта
     */
    private Card giveCard() {
        while (true) {
            int index = random.nextInt(sizeDecks);
            Card card = decks[index].takeCard();

            if (card != null) {
                return card;
            }

            Deck d = decks[index];
            decks[index] = decks[sizeDecks - 1];
            decks[sizeDecks - 1] = d;
            sizeDecks--;

            if (sizeDecks == 0) {
                sizeDecks = cntDeck;

                for (int i = 0; i < sizeDecks; ++i) {
                    decks[i].reset();
                }
            }
        }
    }

    /**
     * Начало новой игры.
     */
    private void newGame() {
        user.reset();
        diller.reset();

        diller.addCard(giveCard());
        user.addCard(giveCard());
        diller.addCard(giveCard());
        user.addCard(giveCard());

        diller.addCard(giveCard());
        diller.hideLastCard();

        state.setStateGame(GameState.USER_MOVE);
    }

    /**
     * Конструктор игры.
     *
     * @param cnt количество колод
     */
    Game(int cnt) {
        sizeDecks = cnt;
        cntDeck = cnt;
        decks = new Deck[10];
        diller = new Gambler();
        user = new Gambler();
        console = new Myconsole();
        state = new GameStateClass();

        state.clear();

        for (int i = 0; i < cntDeck; ++i) {
            decks[i] = new Deck();
        }

        state.setStateGame(GameState.START_GAME);
        nextStep();
    }

    /**
     * Режим пользователя - пользователь выбирает брать карту или остановиться.
     */
    private void userMode() {
        console.clearConsole();
        console.printState(user, diller);

        if (user.getNominal() == 21) {
            state.setStateGame(GameState.END_GAME);
            return;
        }

        boolean error = false;

        while (true) {
            if (error) {
                console.clearConsole();
                System.out.println(
                        "Вы ввели не цифру 0 или 1. Повторите попытку"
                );
                console.printState(user, diller);
            }

            System.out.println(
                    "Ваш ход, введите:\n"
                            + "0: Остановить набор карт\n"
                            + "1: Получить ещё одну карту"
            );

            String s = scanner.nextLine();

            if (s.length() != 1
                    || (s.charAt(0) != '0' && s.charAt(0) != '1')) {
                error = true;
                continue;
            }

            error = false;

            if (s.charAt(0) == '1') {
                console.clearConsole();
                user.addCard(giveCard());

                if (user.getNominal() >= 21) {
                    state.setStateGame(GameState.END_GAME);
                    console.printState(user, diller);
                    return;
                }

                console.printState(user, diller);
            } else {
                console.clearConsole();
                console.printState(user, diller);
                state.setStateGame(GameState.DILLER_MOVE);
                return;
            }
        }
    }

    /**
     * Режим диллера - диллер набирает карты,
     * пока сумма карт меньше 17.
     */
    private void dillerMode() {
        diller.openLastCard();
        console.clearConsole();
        console.printState(user, diller);

        if (diller.getNominal() < 17) {
            stopGame();
        }

        while (diller.getNominal() < 17) {
            console.clearConsole();
            diller.addCard(giveCard());

            if (diller.getNominal() >= 21) {
                break;
            }

            console.printState(user, diller);
            stopGame();

            if (diller.getNominal() >= 17) {
                break;
            }
        }

        state.setStateGame(GameState.END_GAME);
    }

    /**
     * Выигрыш пользователя.
     */
    private void winUser() {
        console.clearConsole();
        console.printState(user, diller);
        state.incScoreUser();
        System.out.print("Вы выиграли раунд! Счёт  ");
        state.printScore();
    }

    /**
     * Выигрыш диллера.
     */
    private void winDiller() {
        console.clearConsole();
        console.printState(user, diller);
        state.incScoreDiller();
        System.out.print("Вы проиграли этот раунд! Счёт  ");
        state.printScore();
    }

    /**
     * Ничья.
     */
    private void noWin() {
        console.clearConsole();
        console.printState(user, diller);
        System.out.print("Этот раунд закончился в ничью! Счёт ");
        state.printScore();
    }

    /**
     * Завершение игры - определение победителя и вывод счёта.
     */
    private void endGame() {
        console.clearConsole();

        int userScore = user.getNominal();
        int dillerScore = diller.getNominal();

        if (userScore > 21) {
            winDiller();
        } else if (dillerScore > 21) {
            winUser();
        } else if (userScore > dillerScore) {
            winUser();
        } else if (userScore < dillerScore) {
            winDiller();
        } else {
            noWin();
        }

        stopGame();
        state.setStateGame(GameState.START_GAME);
    }

    /**
     * Ожидание нажатия Enter для продолжения игры.
     */
    private void stopGame() {
        System.out.println("Нажмите enter для продолжения");
        scanner.nextLine();
    }

    /**
     * Основной метод, который определяет текущий режим игры
     * и вызывает соответствующий метод.
     */
    void nextStep() {
        GameState st = state.getState();

        if (st == GameState.USER_MOVE) {
            userMode();
        }

        if (st == GameState.DILLER_MOVE) {
            dillerMode();
        }

        if (st == GameState.END_GAME) {
            endGame();
        }

        if (st == GameState.START_GAME) {
            newGame();
        }

        if (st == GameState.STOP_GAME) {
            stopGame();
        }
    }
}
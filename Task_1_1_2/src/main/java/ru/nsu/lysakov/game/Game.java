package ru.nsu.lysakov.game;

import ru.nsu.lysakov.players.Dealer;
import ru.nsu.lysakov.players.Player;
import ru.nsu.lysakov.ui.Console;
import ru.nsu.lysakov.ui.Input;

/**
 * Класс для управления игрой.
 */
public class Game {
    private final Player player;
    private final Dealer dealer;
    private final Shoe shoe;
    private final BlackjackRules rules;
    private final Console console;
    private final Input input;
    private final GameScore score;

    private GameState state;

    /**
     * Конструктор класса Game.
     * Инициализирует игрока, дилера, колоду карт, правила игры, консоль для вывода информации,
     * обработчик ввода и счет игры.
     * Запрашивает у пользователя количество колод для игры и создает колоду карт.
     * Устанавливает начальное состояние игры.
     */

    public Game() {
        player = new Player();
        dealer = new Dealer();
        rules = new BlackjackRules();
        console = new Console();
        input = new Input();
        score = new GameScore();

        int cnt = input.getInitGameAction();
        while (cnt == -1) {
            console.clear();
            console.printBadInitGame();
            cnt = input.getInitGameAction();
        }
        shoe = new Shoe(cnt);
        state = GameState.START_GAME;
    }

    /**
     * Метод для перехода к следующему шагу игры в зависимости от текущего состояния.
     * В зависимости от состояния игры вызывает соответствующие методы для начала раунда,
     * хода игрока, хода дилера или завершения раунда.
     */
    public boolean nextStep() {
        switch (state) {
            case START_GAME -> {
                startRound();
                return false;
            }
            case USER_MOVE -> {
                playerTurn();
                return false;
            }
            case DEALER_MOVE -> {
                dealerTurn();
                return false;
            }
            case END_GAME -> {
                endRound();
                return false;
            }
            case STOP_GAME -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private void startRound() {
        player.reset();
        dealer.reset();

        player.addCard(shoe.takeCard());
        dealer.addCard(shoe.takeCard());

        player.addCard(shoe.takeCard());
        dealer.addCard(shoe.takeCard());
        dealer.addCard(shoe.takeCard());

        dealer.hideLastCard();

        state = GameState.USER_MOVE;
    }

    private void playerTurn() {
        console.clear();
        console.printState(player, dealer);

        if (player.canTakeCard()) {
            int usAction = input.getPlayerAction();
            while (usAction == -1) {
                console.clear();
                console.printBadUserInput();
                console.printState(player, dealer);
                usAction = input.getPlayerAction();
            }
            if (usAction == 0) {
                state = GameState.DEALER_MOVE;
            } else if (usAction == 2) {
                state = GameState.STOP_GAME;
                return;
            } else {
                player.addCard(shoe.takeCard());
            }
        } else {
            state = GameState.DEALER_MOVE;
        }
    }

    private void dealerTurn() {

        console.clear();

        if (!player.canTakeCard()) {
            state = GameState.END_GAME;
            return;
        }

        dealer.openLastCard();
        console.printState(player, dealer);

        while (dealer.needCard()) {
            input.waitEnter();
            dealer.addCard(shoe.takeCard());

            console.clear();
            console.printState(player, dealer);
        }

        state = GameState.END_GAME;
    }

    private void endRound() {

        console.clear();
        console.printState(player, dealer);

        RoundResult result = rules.getResult(player, dealer);

        switch (result) {
            case PLAYER_WIN:
                score.playerWin();
                console.printPlayerWin(score);
                break;
            case DEALER_WIN:
                score.dealerWin();
                console.printDealerWin(score);
                break;
            case DRAW:
                console.printDraw(score);
                break;
            default:
                break;
        }
        input.waitEnter();

        state = GameState.START_GAME;
    }

    public static RoundResult checkWinner(Player player, Dealer dealer) {
        int playerScore = player.getNominal();
        int dealerScore = dealer.getNominal();

        if (playerScore > 21) {
            return RoundResult.DEALER_WIN; // проигрыш игрока
        }

        if (dealerScore > 21) {
            return RoundResult.PLAYER_WIN; // победа игрока
        }

        if (playerScore > dealerScore) {
            return RoundResult.PLAYER_WIN;
        }

        if (playerScore < dealerScore) {
            return RoundResult.DEALER_WIN;
        }

        return RoundResult.DRAW; // ничья
    }
}
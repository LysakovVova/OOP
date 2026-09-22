package ru.nsu.lysakov.game;

import ru.nsu.lysakov.players.*;

/**
 * Класс, определяющий правила игры в блэкджек.
 */
public class BlackjackRules {

    /**
     * Определение результата раунда.
     *
     * @param player игрок
     * @param dealer диллер
     * @return результат раунда
     */
    public RoundResult getResult(Player player, Dealer dealer) {
        int playerScore = player.getNominal();
        int dealerScore = dealer.getNominal();

        if (playerScore > 21) {
            return RoundResult.DEALER_WIN;
        }

        if (dealerScore > 21) {
            return RoundResult.PLAYER_WIN;
        }

        if (playerScore > dealerScore) {
            return RoundResult.PLAYER_WIN;
        }

        if (playerScore < dealerScore) {
            return RoundResult.DEALER_WIN;
        }

        return RoundResult.DRAW;
    }
}

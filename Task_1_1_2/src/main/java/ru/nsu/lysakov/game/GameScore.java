package ru.nsu.lysakov.game;

/**
 * Класс для хранения счета игры.
 */
public class GameScore {
    private int playerScore;
    private int dealerScore;

    public void playerWin() {
        playerScore++;
    }

    public void dealerWin() {
        dealerScore++;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }
}

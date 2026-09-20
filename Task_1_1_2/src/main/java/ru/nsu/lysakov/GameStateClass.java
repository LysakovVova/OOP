package ru.nsu.lysakov;
/**
 * класс для хранения состояния игры
 */
public class GameStateClass {
    private int scoreUser;
    private int scoreDiller;
    private GameState state;
    /// инициализация состояния игры
    GameStateClass() {
        scoreDiller = 0;
        scoreUser = 0;
        state = GameState.START_GAME;
    }
    /// очистка состояния игры
    void clear() {
        scoreDiller = 0;
        scoreUser = 0;
        state = GameState.START_GAME;
    }
    /// установка состояния игры
    /// @param newState новое состояние игры
    void setStateGame(GameState newState) {
        state = newState;
    }
    /// получение состояния игры
    /// @return текущее состояние игры
    GameState getState() {
        return state;
    }
    /// увеличение очков пользователя
    void incScoreUser () {
        scoreUser++;
    }
    /// увеличение очков диллера
    void incScoreDiller() {
        scoreDiller++;
    }

    /// печать счета игры
    void printScore() {
        if (scoreUser == scoreDiller) {
            System.out.println(scoreUser + " : " + scoreDiller);
        }
        if (scoreUser > scoreDiller) {
            System.out.println(scoreUser + " : " + scoreDiller + " в вашу пользу");
        }
        if (scoreUser < scoreDiller) {
            System.out.println(scoreDiller + " : " + scoreUser + " в пользу диллера");
        }
    }
}
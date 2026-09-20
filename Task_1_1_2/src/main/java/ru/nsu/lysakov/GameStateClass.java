package ru.nsu.lysakov;

/**
 * Класс для хранения состояния игры.
 */
public class GameStateClass {
    private int scoreUser;
    private int scoreDiller;
    private GameState state;

    /**
     * Инициализация состояния игры.
     */
    GameStateClass() {
        scoreDiller = 0;
        scoreUser = 0;
        state = GameState.START_GAME;
    }

    /**
     * Очистка состояния игры.
     */
    void clear() {
        scoreDiller = 0;
        scoreUser = 0;
        state = GameState.START_GAME;
    }

    /**
     * Установка состояния игры.
     *
     * @param newState новое состояние игры
     */
    void setStateGame(GameState newState) {
        state = newState;
    }

    /**
     * Получение состояния игры.
     *
     * @return текущее состояние игры
     */
    GameState getState() {
        return state;
    }

    /**
     * Увеличение очков пользователя.
     */
    void incScoreUser() {
        scoreUser++;
    }

    /**
     * Увеличение очков диллера.
     */
    void incScoreDiller() {
        scoreDiller++;
    }

    /**
     * Печать счёта игры.
     */
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
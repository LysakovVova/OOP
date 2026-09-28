package ru.nsu.lysakov.players;

/**
 * Класс игрока.
 */
public class Player extends Gambler {

    /**
     * Проверка возможности продолжить набор карт.
     *
     * @return true, если можно брать карту
     */
    public boolean canTakeCard() {
        return getNominal() < 21;
    }
}

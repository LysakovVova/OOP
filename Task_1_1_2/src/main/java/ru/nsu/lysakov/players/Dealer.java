package ru.nsu.lysakov.players;

/**
 * Класс дилера, наследуется от игрока.
 */
public class Dealer extends Gambler {

    /**
     * Проверка необходимости взять ещё одну карту.
     *
     * @return true, если сумма меньше 17
     */
    public boolean needCard() {
        return getNominal() < 17;
    }

    public void hideLastCard() {
        hand.get(hand.size() - 1).hide();
    }

    public void openLastCard() {
        hand.get(hand.size() - 1).open();
    }
}

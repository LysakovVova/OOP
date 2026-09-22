package ru.nsu.lysakov.players;

import java.util.ArrayList;
import ru.nsu.lysakov.cards.*;

/**
 * Класс игрока, который хранит карты в руке.
 */
public class Gambler {
    protected final ArrayList<Card> hand = new ArrayList<>();

    /**
     * Добавление карты в руку.
     *
     * @param card карта
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Очистка руки.
     */
    public void reset() {
        for (Card card : hand) {
            card.open();
        }

        hand.clear();
    }

    /**
     * Получение номинала руки.
     *
     * @return номинал руки
     */
    public int getNominal() {
        int sum = 0;
        int aceCount = 0;

        for (Card card : hand) {
            if (card.isClosed()) {
                continue;
            }

            if (card.isAce()) {
                sum += 11;
                aceCount++;
            } else {
                sum += card.getNominal();
            }
        }

        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }

        return sum;
    }

    /**
     * Печать карт в руке.
     */
    public void print() {
        for (Card card : hand) {
            card.print();
            System.out.print(" ");
        }
    }
}
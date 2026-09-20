package ru.nsu.lysakov;

import java.util.ArrayList;

/**
 * Класс игрока, который хранит карты в руке и умеет их открывать и закрывать.
 */
public class Gambler {
    private ArrayList<Card> hand;

    /**
     * Конструктор игрока.
     */
    Gambler() {
        hand = new ArrayList<>();
    }

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
     * Закрытие последней карты в руке.
     */
    public void hideLastCard() {
        hand.get(hand.size() - 1).hide();
    }

    /**
     * Открытие последней карты в руке.
     */
    public void openLastCard() {
        hand.get(hand.size() - 1).open();
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
            if (card.closed) {
                continue;
            }

            if (card instanceof AceCard) {
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
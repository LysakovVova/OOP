package ru.nsu.lysakov;

import java.util.ArrayList;

/// класс игрока, который хранит карты в руке и умеет их открывать/закрывать
public class Gambler {
    private ArrayList<Card> hand;
    /// конструктор
    Gambler() {
        hand = new ArrayList<>();
    }

    /// добавление карты в руку
    /// @param card карта
    public void addCard(Card card) {
        hand.add(card);
    }
    /// очистка руки
    public void reset() {
        for (Card card : hand) {
            card.open();
        }

        hand.clear();
    }
    /// закрытие последней карты в руке
    public void hideLastCard() {
        hand.get(hand.size() - 1).hide();
    }
    /// открытие последней карты в руке
    public void openLastCard() {
        hand.get(hand.size() - 1).open();
    }

    /// получение номинала руки
    /// @return номинал руки
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
    /// печать карт в руке
    public void print() {
        for (Card card : hand) {
            card.print();
            System.out.print(" ");
        }
    }

}
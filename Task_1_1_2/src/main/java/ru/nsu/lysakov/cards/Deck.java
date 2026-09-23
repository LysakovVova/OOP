package ru.nsu.lysakov.cards;

import java.util.Random;

/**
 * Класс колоды карт.
 */
public class Deck {
    private Card[] deck;
    private int size;
    private final Random random = new Random();
    private static final CardEnum[] cardSymbols = {
        CardEnum.TWO, CardEnum.THREE, CardEnum.FOUR, CardEnum.FIVE,
        CardEnum.SIX, CardEnum.SEVEN, CardEnum.EIGHT, CardEnum.NINE,
        CardEnum.TEN, CardEnum.JACK, CardEnum.QUEEN, CardEnum.KING,
        CardEnum.ACE
    };

    /**
     * Создание колоды карт.
     */
    public Deck() {
        deck = new Card[52];
        size = 52;

        for (int i = 0; i < 4; ++i) {
            Suit suit = Suit.values()[i];

            for (int j = 0; j < 13; ++j) {
                CardEnum symbol = cardSymbols[j];

                if (symbol == CardEnum.ACE) {
                    deck[i * 13 + j] = new AceCard(symbol, suit);
                } else if (symbol == CardEnum.JACK
                        || symbol == CardEnum.QUEEN
                        || symbol == CardEnum.KING) {
                    deck[i * 13 + j] = new PictureCard(symbol, suit);
                } else {
                    deck[i * 13 + j] = new NumberCard(symbol, suit);
                }
            }
        }
    }

    /**
     * Сброс колоды.
     */
    public void reset() {
        size = 52;
    }

    /**
     * Взятие карты из колоды.
     *
     * @return случайная карта из колоды или null, если колода пуста
     */
    public Card takeCard() {
        if (size == 0) {
            return null;
        }

        int index = random.nextInt(size);
        Card ans = deck[index];

        deck[index] = deck[size - 1];
        deck[size - 1] = ans;

        size--;
        ans.open();

        return ans;
    }
}
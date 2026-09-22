package ru.nsu.lysakov.cards;

import java.util.Random;

/**
 * Класс колоды карт.
 */
public class Deck {
    private Card[] deck;
    private int size;
    private final Random random = new Random();

    /**
     * Создание колоды карт.
     */
    public Deck() {
        deck = new Card[52];
        size = 52;

        for (int i = 0; i < 4; ++i) {
            Suit suit = Suit.values()[i];

            for (int j = 2; j <= 10; ++j) {
                deck[i * 13 + j - 2] =
                        new NumberCard(String.valueOf(j), suit);
            }

            deck[i * 13 + 9] = new PictureCard("J", suit);
            deck[i * 13 + 10] = new PictureCard("Q", suit);
            deck[i * 13 + 11] = new PictureCard("K", suit);
            deck[i * 13 + 12] = new AceCard("A", suit);
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
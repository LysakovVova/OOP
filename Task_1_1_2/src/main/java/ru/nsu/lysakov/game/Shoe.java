package ru.nsu.lysakov.game;

import java.util.Random;
import ru.nsu.lysakov.cards.Card;
import ru.nsu.lysakov.cards.Deck;

/**
 * Класс, представляющий колоды карт (Shoe).
 * Содержит несколько колод и обеспечивает выдачу случайных карт.
 */
public class Shoe {
    private final Deck[] decks;
    private final int countDecks;
    private int activeDecks;
    private final Random random = new Random();

    /**
     * Создаёт шулеру с указанным числом колод.
     *
     * @param countDecks количество колод
     */
    public Shoe(int countDecks) {
        this.countDecks = countDecks;
        this.activeDecks = countDecks;
        this.decks = new Deck[countDecks];

        for (int i = 0; i < countDecks; ++i) {
            decks[i] = new Deck();
        }
    }

    /**
     * Выдача случайной карты.
     *
     * @return карта
     */
    public Card takeCard() {
        while (true) {
            int index = random.nextInt(activeDecks);
            Card card = decks[index].takeCard();

            if (card != null) {
                return card;
            }

            Deck temp = decks[index];
            decks[index] = decks[activeDecks - 1];
            decks[activeDecks - 1] = temp;

            activeDecks--;

            if (activeDecks == 0) {
                reset();
            }
        }
    }

    /**
     * Сброс всех колод.
     */
    private void reset() {
        activeDecks = countDecks;

        for (Deck deck : decks) {
            deck.reset();
        }
    }
}

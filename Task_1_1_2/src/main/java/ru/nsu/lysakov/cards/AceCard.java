package ru.nsu.lysakov.cards;

/**
 * Класс карты с тузом.
 */
public class AceCard extends Card {

    /**
     * Конструктор карты с тузом.
     *
     * @param symbol символ карты
     * @param suit масть карты
     */
    public AceCard(String symbol, Suit suit) {
        super(symbol, suit);
    }

    /**
     * Получение номинала карты.
     *
     * @return номинал карты
     */
    public int getNominal() {
        if (closed) {
            return 0;
        }

        return 11;
    }
}
package ru.nsu.lysakov;

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
    AceCard(String symbol, Suit suit) {
        super(symbol, suit);
    }

    /**
     * Получение номинала карты.
     *
     * @return номинал карты
     */
    @Override
    int getNominal() {
        if (closed) {
            return 0;
        }

        return 11;
    }
}
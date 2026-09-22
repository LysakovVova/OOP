package ru.nsu.lysakov.cards;

/**
 * Класс карты с картинным номиналом.
 */
public class PictureCard extends Card {

    /**
     * Конструктор карты с картинным номиналом.
     *
     * @param symbol символ карты
     * @param suit масть карты
     */
    public PictureCard(String symbol, Suit suit) {
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

        return 10;
    }
}
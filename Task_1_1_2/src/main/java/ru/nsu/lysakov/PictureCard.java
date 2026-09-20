package ru.nsu.lysakov;

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
    PictureCard(String symbol, Suit suit) {
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

        return 10;
    }
}
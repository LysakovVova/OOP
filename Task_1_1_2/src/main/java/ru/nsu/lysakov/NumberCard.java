package ru.nsu.lysakov;

/**
 * Класс карты с числовым номиналом.
 */
public class NumberCard extends Card {
    private int numberNominal;

    /**
     * Конструктор карты с числовым номиналом.
     *
     * @param symbol символ карты
     * @param suit масть карты
     */
    NumberCard(String symbol, Suit suit) {
        super(symbol, suit);
        numberNominal = Integer.parseInt(symbol);
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

        return numberNominal;
    }
}
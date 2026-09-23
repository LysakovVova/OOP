package ru.nsu.lysakov.cards;

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
    public NumberCard(CardEnum symbol, Suit suit) {
        super(symbol, suit);
        numberNominal = Integer.parseInt(symbol.toSymbol());
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

        return numberNominal;
    }
}
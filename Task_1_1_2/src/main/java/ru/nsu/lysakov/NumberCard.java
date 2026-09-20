package ru.nsu.lysakov;

/// класс карты с числовым номиналом
public class NumberCard extends Card {
    private int numberNominal;

    /// конструктор карты с числовым номиналом
    /// @param symbol символ карты
    /// @param suit масть карты
    NumberCard(String symbol, Suit suit) {
        super(symbol, suit);
        numberNominal = Integer.parseInt(symbol);
    }
    /// получение номинала карты
    /// @return номинал карты
    @Override
    int getNominal() {
        if (closed) return 0;
        return numberNominal;
    }
}
package ru.nsu.lysakov;

/// класс карты с картинным номиналом
public class PictureCard extends Card {
    /// конструктор карты с картинным номиналом
    /// @param symbol символ карты
    /// @param suit масть карты
    PictureCard(String symbol, Suit suit) {
        super(symbol, suit);
    }
    /// получение номинала карты
    /// @return номинал карты
    @Override
    int getNominal() {
        if (closed) return 0;
        return 10;
    }
}
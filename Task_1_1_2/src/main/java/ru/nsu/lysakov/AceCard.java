package ru.nsu.lysakov;

/// класс карты с тузом
public class AceCard extends Card {

    /// конструктор карты с тузом
    /// @param symbol
    /// @param suit
    AceCard(String symbol, Suit suit) {
        super(symbol, suit);
    }

    /// получение номинала карты
    /// @return
    
    @Override
    int getNominal() {
        if (closed) return 0;
        return 11;
    }
}
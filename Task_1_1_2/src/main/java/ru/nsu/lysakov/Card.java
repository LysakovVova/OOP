package ru.nsu.lysakov;
/// абстрактный класс карты
public abstract class Card {
    protected String symbol;
    protected Suit suit;
    protected Boolean closed = false;

    /// конструктор карты
    /// @param symbol символ карты
    /// @param suit масть карты
    Card(String  symbol, Suit suit) {
        this.symbol = symbol;
        this.suit = suit;
    }

    /// печать карты в консоль
    void print() {
        if (closed) {
            System.out.println("закрытая карта");
            return;
        }
        System.out.print(symbol + "" +  suit.toSymbol());
    }

    /// закрытие карты
    void hide() {
        closed = true;
    }

    /// открытие карты
    void open() {
        closed = false;
    }

    /// получение номинала карты
    abstract int getNominal();
}

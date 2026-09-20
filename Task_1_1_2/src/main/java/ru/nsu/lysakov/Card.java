package ru.nsu.lysakov;

/**
 * Абстрактный класс карты.
 */
public abstract class Card {
    protected String symbol;
    protected Suit suit;
    protected boolean closed = false;

    /**
     * Конструктор карты.
     *
     * @param symbol символ карты
     * @param suit масть карты
     */
    Card(String symbol, Suit suit) {
        this.symbol = symbol;
        this.suit = suit;
    }

    /**
     * Печать карты в консоль.
     */
    void print() {
        if (closed) {
            System.out.println("закрытая карта");
            return;
        }

        System.out.print(symbol + suit.toSymbol());
    }

    /**
     * Закрытие карты.
     */
    void hide() {
        closed = true;
    }

    /**
     * Открытие карты.
     */
    void open() {
        closed = false;
    }

    /**
     * Получение номинала карты.
     *
     * @return номинал карты
     */
    abstract int getNominal();
}
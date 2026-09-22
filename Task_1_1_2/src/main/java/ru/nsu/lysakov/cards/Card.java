package ru.nsu.lysakov.cards;

/**
 * Класс карты.
 */
public class Card {
    protected String symbol;
    protected Suit suit;
    protected boolean closed = false;

    /**
     * Конструктор карты.
     *
     * @param symbol символ карты
     * @param suit масть карты
     */
    public Card(String symbol, Suit suit) {
        this.symbol = symbol;
        this.suit = suit;
    }

    /**
     * Печать карты в консоль.
     */
    public void print() {
        if (closed) {
            System.out.print("закрытая карта");
            return;
        }

        System.out.print(symbol + suit.toSymbol());
    }

    /**
     * Закрытие карты.
     */
    public void hide() {
        closed = true;
    }

    /**
     * Открытие карты.
     */
    public void open() {
        closed = false;
    }


    /**
    * Получение статуса карты.
    *
    * @return статус карты
    */
    public boolean isClosed() {
        return closed;
    }

    /**
    * Проверяет, является ли карта тузом.
    *
    * @return true, если это туз
    */
    public boolean isAce() {
        return "A".equals(symbol);
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

        if (isAce()) {
            return 11;
        }
        if ("J".equals(symbol) || "Q".equals(symbol) || "K".equals(symbol)) {
            return 10;
        }

        return Integer.parseInt(symbol);
    }
}
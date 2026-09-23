package ru.nsu.lysakov.cards;

/**
 * Класс карты.
 */
public class Card {
    protected CardEnum symbol;
    protected Suit suit;
    protected boolean closed = false;

    /**
     * Конструктор карты.
     *
     * @param symbol символ карты
     * @param suit масть карты
     */
    public Card(CardEnum symbol, Suit suit) {
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

        System.out.print(symbol.toSymbol() + suit.toSymbol());
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
        return symbol == CardEnum.ACE;
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
        if (symbol == CardEnum.JACK || symbol == CardEnum.QUEEN || symbol == CardEnum.KING) {
            return 10;
        }

        return Integer.parseInt(symbol.toString());
    }
}
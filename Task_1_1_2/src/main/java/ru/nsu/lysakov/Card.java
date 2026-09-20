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

/// класс карты с числовым номиналом
class NumberCard extends Card {
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
/// класс карты с картинным номиналом
class PictureCard extends Card {
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
/// класс карты с тузом
class AceCard extends Card {
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

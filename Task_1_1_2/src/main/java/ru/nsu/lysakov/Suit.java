package ru.nsu.lysakov;

/**
 * возможные масти карт
 * также метод для получения символа масти
 */
public enum Suit {
    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES;

    public String toSymbol() {
        return switch (this) {
            case HEARTS -> "♥";
            case DIAMONDS -> "♦";
            case CLUBS -> "♣";
            case SPADES -> "♠";
        };
    }
}
package ru.nsu.lysakov;

/**
 * Возможные масти карт.
 * Также содержит метод для получения символа масти.
 */
public enum Suit {
    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES;

    /**
     * Получение символа масти.
     *
     * @return символ масти
     */
    public String toSymbol() {
        return switch (this) {
            case HEARTS -> "♥";
            case DIAMONDS -> "♦";
            case CLUBS -> "♣";
            case SPADES -> "♠";
        };
    }
}
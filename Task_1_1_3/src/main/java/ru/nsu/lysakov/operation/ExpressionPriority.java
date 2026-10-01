package ru.nsu.lysakov.operation;

/**
 * Приоритеты различных видов выражений.
 */
public enum ExpressionPriority {

    ADD_SUB(1),
    MUL_DIV(2),
    NEG(3),
    POW(4),
    ATOM(5);

    private final int value;

    ExpressionPriority(int value) {
        this.value = value;
    }

    /**
     * Возвращает числовое значение приоритета.
     *
     * @return приоритет
     */
    public int getValue() {
        return value;
    }
}
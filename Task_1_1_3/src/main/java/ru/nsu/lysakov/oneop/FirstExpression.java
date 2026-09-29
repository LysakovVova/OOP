package ru.nsu.lysakov.oneop;

import ru.nsu.lysakov.base.Expression;

/**
 * Абстрактный базовый класс для унарных математических операций.
 *
 * <p>Хранит единственный операнд выражения.
 */
public abstract class FirstExpression extends Expression {

    protected Expression value;

    /**
     * Создаёт унарное выражение с заданным операндом.
     *
     * @param value операнд выражения
     */
    public FirstExpression(Expression value) {
        this.value = value;
    }
}
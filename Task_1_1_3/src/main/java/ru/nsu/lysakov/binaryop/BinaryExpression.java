package ru.nsu.lysakov.binaryop;

import ru.nsu.lysakov.base.Expression;

/**
 * Абстрактный базовый класс для бинарных математических операций.
 *
 * <p>Хранит левый и правый операнды выражения и предоставляет
 * общий метод для сравнения операндов.
 */
public abstract class BinaryExpression extends Expression {

    protected Expression left;
    protected Expression right;

    /**
     * Создаёт бинарное выражение с двумя операндами.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Проверяет, совпадают ли операнды двух бинарных выражений.
     *
     * @param other другое бинарное выражение
     * @return {@code true}, если левый и правый операнды совпадают,
     *         иначе {@code false}
     */
    protected boolean sameOperands(BinaryExpression other) {
        return left.equals(other.left)
                && right.equals(other.right);
    }
}
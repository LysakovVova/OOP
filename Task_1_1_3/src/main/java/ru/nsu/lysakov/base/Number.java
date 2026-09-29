package ru.nsu.lysakov.base;

import ru.nsu.lysakov.operation.ExpressionPriority;

/**
 * Класс, представляющий числовую константу в математическом выражении.
 */
public class Number extends Expression {

    private final Double value;

    /**
     * Создаёт числовое выражение с заданным значением.
     *
     * @param value значение числа
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Возвращает значение числа.
     *
     * @return значение числа
     */
    public Double getValue() {
        return value;
    }

    /**
     * Сравнивает текущее число с другим объектом.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если объекты равны, иначе {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Number other)) {
            return false;
        }

        return value.equals(other.value);
    }

    /**
     * Упрощает числовое выражение.
     *
     * <p>Число уже является простейшим выражением,
     * поэтому возвращается текущий объект.
     *
     * @return текущее числовое выражение
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Вычисляет производную числа по заданной переменной.
     *
     * <p>Производная любой константы равна нулю.
     *
     * @param varName имя переменной, по которой берётся производная
     * @return числовое выражение со значением {@code 0}
     */
    @Override
    public Expression derivative(String varName) {
        return new Number(0);
    }

    /**
     * Вычисляет значение числового выражения.
     *
     * @param signification строка со значениями переменных
     * @return значение числа
     */
    @Override
    public Double evaluate(String signification) {
        return value;
    }

    /**
     * Возвращает строковое представление числа.
     *
     * @return строковое представление числа
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Возвращает приоритет числового выражения.
     *
     * @return приоритет выражения
     */
    @Override
    public int getPriority() {
        return ExpressionPriority.ATOM.getValue();
    }
}
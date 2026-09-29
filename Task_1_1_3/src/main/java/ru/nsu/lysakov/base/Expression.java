package ru.nsu.lysakov.base;

/**
 * Абстрактный базовый класс для математических выражений.
 *
 * <p>Определяет основные операции над выражениями:
 * вычисление значения, получение производной, упрощение
 * и определение приоритета операции.
 */
public abstract class Expression {

    /**
     * Вычисляет значение выражения при заданных значениях переменных.
     *
     * @param signification строка со значениями переменных
     * @return вычисленное значение выражения
     */
    public abstract Double evaluate(String signification);

    /**
     * Возвращает приоритет операции выражения.
     *
     * @return приоритет операции
     */
    public abstract int getPriority();

    /**
     * Вычисляет производную выражения по заданной переменной.
     *
     * @param varName имя переменной
     * @return выражение, представляющее производную
     */
    public abstract Expression derivative(String varName);

    /**
     * Упрощает текущее выражение.
     *
     * @return упрощённое выражение
     */
    public abstract Expression simplify();

    /**
     * Сравнивает текущее выражение с другим объектом.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если объекты равны, иначе {@code false}
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Возвращает строковое представление выражения.
     *
     * @return строковое представление выражения
     */
    @Override
    public abstract String toString();
}
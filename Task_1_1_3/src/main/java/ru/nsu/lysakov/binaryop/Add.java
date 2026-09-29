package ru.nsu.lysakov.binaryop;

import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.base.Number;

/**
 * Класс, представляющий операцию сложения двух выражений.
 */
public class Add extends BinaryExpression {

    /**
     * Создаёт выражение сложения двух операндов.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Add(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Сравнивает текущее выражение сложения с другим объектом.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если выражения равны, иначе {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Add other)) {
            return false;
        }

        return sameOperands(other);
    }

    /**
     * Упрощает выражение сложения.
     *
     * <p>Выполняет вычисление суммы чисел, удаляет сложение с нулём
     * и преобразует сумму одинаковых выражений в умножение на два.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression leftAns = left.simplify();
        Expression rightAns = right.simplify();

        if (leftAns.equals(rightAns)) {
            return new Mul(new Number(2), leftAns).simplify();
        }

        if (leftAns instanceof Number leftNumber
                && rightAns instanceof Number rightNumber) {
            return new Number(
                    leftNumber.evaluate("")
                            + rightNumber.evaluate("")
            );
        }

        if (rightAns instanceof Number rightNumber
                && rightNumber.evaluate("") == 0) {
            return leftAns;
        }

        if (leftAns instanceof Number leftNumber
                && leftNumber.evaluate("") == 0) {
            return rightAns;
        }

        return new Add(leftAns, rightAns);
    }

    /**
     * Вычисляет производную суммы двух выражений.
     *
     * <p>Производная суммы равна сумме производных её операндов.
     *
     * @param varName имя переменной, по которой берётся производная
     * @return производная выражения или {@code null},
     *         если одну из производных вычислить невозможно
     */
    @Override
    public Expression derivative(String varName) {
        Expression leftAns = left.derivative(varName);
        Expression rightAns = right.derivative(varName);

        if (leftAns == null || rightAns == null) {
            return null;
        }

        return new Add(leftAns, rightAns);
    }

    /**
     * Вычисляет значение суммы двух выражений.
     *
     * @param signification строка со значениями переменных
     * @return сумма значений операндов или {@code null},
     *         если значение одного из них вычислить невозможно
     */
    @Override
    public Double evaluate(String signification) {
        Double leftAns = left.evaluate(signification);
        Double rightAns = right.evaluate(signification);

        if (leftAns == null || rightAns == null) {
            return null;
        }

        return leftAns + rightAns;
    }

    /**
     * Возвращает строковое представление операции сложения.
     *
     * @return строковое представление выражения
     */
    @Override
    public String toString() {
        return left + " + " + right;
    }

    /**
     * Возвращает приоритет операции сложения.
     *
     * @return приоритет операции
     */
    @Override
    public int getPriority() {
        return 1;
    }
}
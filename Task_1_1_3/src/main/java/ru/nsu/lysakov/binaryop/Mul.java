package ru.nsu.lysakov.binaryop;

import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.base.Number;
import ru.nsu.lysakov.base.Variable;
import ru.nsu.lysakov.operation.ExpressionPriority;

/**
 * Класс, представляющий операцию умножения двух выражений.
 */
public class Mul extends BinaryExpression {

    /**
     * Создаёт выражение умножения двух операндов.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Сравнивает текущее выражение умножения с другим объектом.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если выражения равны, иначе {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Mul other)) {
            return false;
        }

        return sameOperands(other);
    }

    /**
     * Упрощает выражение умножения.
     *
     * <p>Выполняет умножение числовых констант, обрабатывает
     * умножение на ноль и единицу, а также переставляет число
     * перед переменной.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression leftAns = left.simplify();
        Expression rightAns = right.simplify();

        if (leftAns instanceof Number leftNumber
                && rightAns instanceof Number rightNumber) {
            return new Number(
                    leftNumber.evaluate("") * rightNumber.evaluate("")
            );
        }

        if (rightAns instanceof Number rightNumber
                && rightNumber.evaluate("") == 0) {
            return new Number(0);
        }

        if (leftAns instanceof Number leftNumber
                && leftNumber.evaluate("") == 0) {
            return new Number(0);
        }

        if (rightAns instanceof Number rightNumber
                && rightNumber.evaluate("") == 1) {
            return leftAns;
        }

        if (leftAns instanceof Number leftNumber
                && leftNumber.evaluate("") == 1) {
            return rightAns;
        }

        if (rightAns instanceof Number
                && leftAns instanceof Variable) {
            return new Mul(rightAns, leftAns);
        }

        return new Mul(leftAns, rightAns);
    }

    /**
     * Вычисляет производную произведения двух выражений.
     *
     * <p>Используется правило:
     * (u * v)' = u' * v + u * v'.
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

        return new Add(
                new Mul(leftAns, right),
                new Mul(left, rightAns)
        );
    }

    /**
     * Вычисляет значение произведения двух выражений.
     *
     * @param signification строка со значениями переменных
     * @return произведение значений операндов или {@code null},
     *         если значение одного из них вычислить невозможно
     */
    @Override
    public Double evaluate(String signification) {
        Double leftAns = left.evaluate(signification);
        Double rightAns = right.evaluate(signification);

        if (leftAns == null || rightAns == null) {
            return null;
        }

        return leftAns * rightAns;
    }

    /**
     * Возвращает строковое представление операции умножения.
     *
     * <p>Если приоритет одного из операндов ниже приоритета умножения,
     * этот операнд заключается в скобки.
     *
     * @return строковое представление выражения
     */
    @Override
    public String toString() {
        String leftStr = left.toString();
        String rightStr = right.toString();

        if (left.getPriority() < getPriority()) {
            leftStr = "(" + leftStr + ")";
        }

        if (right.getPriority() < getPriority()) {
            rightStr = "(" + rightStr + ")";
        }

        return leftStr + " * " + rightStr;
    }

    /**
     * Возвращает приоритет операции умножения.
     *
     * @return приоритет операции
     */
    @Override
    public int getPriority() {
        return ExpressionPriority.MUL_DIV.getValue();
    }
}
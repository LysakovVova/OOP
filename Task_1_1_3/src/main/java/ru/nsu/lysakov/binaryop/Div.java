package ru.nsu.lysakov.binaryop;

import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.base.Number;
import ru.nsu.lysakov.operation.ExpressionPriority;

/**
 * Класс, представляющий операцию деления двух выражений.
 */
public class Div extends BinaryExpression {

    /**
     * Создаёт выражение деления двух операндов.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Вычисляет производную частного двух выражений.
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

        return new Div(
                new Sub(
                        new Mul(leftAns, right),
                        new Mul(left, rightAns)
                ),
                new Mul(
                        right,
                        right
                )
        );
    }

    /**
     * Сравнивает текущее выражение деления с другим объектом.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если выражения равны, иначе {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Div other)) {
            return false;
        }

        return sameOperands(other);
    }

    /**
     * Упрощает выражение деления.
     *
     * <p>Если оба операнда являются числами, вычисляет результат.
     * Деление на единицу заменяется левым операндом.
     *
     * @return упрощённое выражение или {@code null},
     *         если происходит деление на ноль
     */
    @Override
    public Expression simplify() {
        Expression leftAns = left.simplify();
        Expression rightAns = right.simplify();

        if (leftAns instanceof Number leftNumber
                && rightAns instanceof Number rightNumber) {

            if (rightNumber.evaluate("") == 0) {
                return null;
            }

            return new Number(
                    leftNumber.evaluate("") / rightNumber.evaluate("")
            );
        }

        if (rightAns instanceof Number rightNumber
                && rightNumber.evaluate("") == 1) {
            return leftAns;
        }

        return new Div(leftAns, rightAns);
    }

    /**
     * Вычисляет значение частного двух выражений.
     *
     * @param signification строка со значениями переменных
     * @return результат деления или {@code null},
     *         если значение одного из операндов неизвестно
     *         или происходит деление на ноль
     */
    @Override
    public Double evaluate(String signification) {
        Double leftAns = left.evaluate(signification);
        Double rightAns = right.evaluate(signification);

        if (leftAns == null || rightAns == null) {
            return null;
        }

        if (rightAns == 0) {
            return null;
        }

        return leftAns / rightAns;
    }

    /**
     * Возвращает строковое представление операции деления.
     *
     * <p>При необходимости операнды заключаются в скобки
     * в соответствии с приоритетами операций.
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

        if (right.getPriority() <= getPriority()) {
            rightStr = "(" + rightStr + ")";
        }

        return leftStr + " / " + rightStr;
    }

    /**
     * Возвращает приоритет операции деления.
     *
     * @return приоритет операции
     */
    @Override
    public int getPriority() {
        return ExpressionPriority.MUL_DIV.getValue();
    }
}
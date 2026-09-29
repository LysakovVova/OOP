package ru.nsu.lysakov.binaryop;

import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.base.Number;
import ru.nsu.lysakov.base.Variable;
import ru.nsu.lysakov.operation.ExpressionPriority;

/**
 * Класс, представляющий операцию возведения выражения в степень.
 */
public class Pow extends BinaryExpression {

    /**
     * Создаёт выражение возведения в степень.
     *
     * @param var основание степени
     * @param pow показатель степени
     */
    public Pow(Expression var, Expression pow) {
        super(var, pow);
    }

    /**
     * Сравнивает текущее выражение с другим объектом.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если выражения равны, иначе {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pow other)) {
            return false;
        }

        return sameOperands(other);
    }

    /**
     * Упрощает выражение возведения в степень.
     *
     * <p>Если основание и показатель являются числами,
     * результат вычисляется сразу. Также обрабатываются
     * степени ноль и один и основания ноль и один.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression ansLeft = left.simplify();
        Expression ansRight = right.simplify();

        if (ansLeft instanceof Number leftNumber
                && ansRight instanceof Number rightNumber) {
            return new Number(
                    Math.pow(leftNumber.getValue(), rightNumber.getValue())
            );
        }

        if (ansRight instanceof Number number
                && number.evaluate("") == 0) {
            return new Number(1);
        }

        if (ansRight instanceof Number number
                && number.evaluate("") == 1) {
            return ansLeft;
        }

        if (ansLeft instanceof Number number
                && number.evaluate("") == 1) {
            return new Number(1);
        }

        if (ansLeft instanceof Number number
                && number.evaluate("") == 0) {
            return new Number(0);
        }

        return new Pow(ansLeft, ansRight);
    }

    /**
     * Вычисляет производную выражения возведения в степень.
     *
     * <p>Если показатель степени не зависит от переменной,
     * используется правило:
     * (u^n)' = n * u^(n - 1) * u'.
     *
     * @param varName имя переменной, по которой берётся производная
     * @return производная выражения или {@code null},
     *         если показатель зависит от переменной
     */
    @Override
    public Expression derivative(String varName) {
        Expression rightDerivative = right.derivative(varName);

        if (rightDerivative.equals(new Number(0))) {
            return new Mul(
                    new Mul(
                            right,
                            new Pow(
                                    left,
                                    new Sub(
                                            right,
                                            new Number(1)
                                    )
                            )
                    ),
                    left.derivative(varName)
            );
        }

        return null;
    }

    /**
     * Вычисляет значение выражения возведения в степень.
     *
     * @param signification строка со значениями переменных
     * @return результат возведения в степень или {@code null},
     *         если один из операндов вычислить невозможно
     */
    @Override
    public Double evaluate(String signification) {
        Double ansLeft = left.evaluate(signification);
        Double ansRight = right.evaluate(signification);

        if (ansLeft == null || ansRight == null) {
            return null;
        }

        return Math.pow(ansLeft, ansRight);
    }

    /**
     * Возвращает строковое представление операции возведения в степень.
     *
     * <p>Составные выражения заключаются в скобки.
     *
     * @return строковое представление выражения
     */
    @Override
    public String toString() {
        String leftStr = left.toString();
        String rightStr = right.toString();

        if (!(left instanceof Number || left instanceof Variable)) {
            leftStr = "(" + leftStr + ")";
        }

        if (!(right instanceof Number || right instanceof Variable)) {
            rightStr = "(" + rightStr + ")";
        }

        return leftStr + "^" + rightStr;
    }

    /**
     * Возвращает приоритет операции возведения в степень.
     *
     * @return приоритет операции
     */
    @Override
    public int getPriority() {
        return ExpressionPriority.POW.getValue();
    }
}
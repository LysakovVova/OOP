package ru.nsu.lysakov.binaryop;

import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.base.Number;
import ru.nsu.lysakov.oneop.Neg;
import ru.nsu.lysakov.operation.ExpressionPriority;

/**
 * Класс, представляющий операцию вычитания двух выражений.
 */
public class Sub extends BinaryExpression {

    /**
     * Создаёт выражение вычитания.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Сравнивает текущее выражение с другим объектом.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если выражения равны, иначе {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sub other)) {
            return false;
        }

        return sameOperands(other);
    }

    /**
     * Упрощает выражение вычитания.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression leftAns = left.simplify();
        Expression rightAns = right.simplify();

        if (leftAns.equals(rightAns)) {
            return new Number(0);
        }

        if (leftAns instanceof Number leftNumber
                && rightAns instanceof Number rightNumber) {
            return new Number(
                    leftNumber.evaluate("")
                            - rightNumber.evaluate("")
            );
        }

        if (rightAns instanceof Number rightNumber
                && rightNumber.evaluate("") == 0) {
            return leftAns;
        }

        if (leftAns instanceof Number leftNumber
                && leftNumber.evaluate("") == 0) {
            return new Neg(rightAns);
        }

        return new Sub(leftAns, rightAns);
    }

    /**
     * Вычисляет производную разности.
     *
     * @param varName имя переменной, по которой берётся производная
     * @return производная выражения
     */
    @Override
    public Expression derivative(String varName) {
        Expression leftAns = left.derivative(varName);
        Expression rightAns = right.derivative(varName);

        return new Sub(leftAns, rightAns);
    }

    /**
     * Вычисляет значение выражения.
     *
     * @param signification строка со значениями переменных
     * @return результат вычисления или {@code null},
     *         если один из операндов вычислить невозможно
     */
    @Override
    public Double evaluate(String signification) {
        Double leftAns = left.evaluate(signification);
        Double rightAns = right.evaluate(signification);

        if (leftAns == null || rightAns == null) {
            return null;
        }

        return leftAns - rightAns;
    }

    /**
     * Возвращает строковое представление выражения.
     *
     * @return строковое представление выражения
     */
    @Override
    public String toString() {
        return left + " - " + right;
    }

    /**
     * Возвращает приоритет операции вычитания.
     *
     * @return приоритет операции
     */
    @Override
    public int getPriority() {
        return ExpressionPriority.ADD_SUB.getValue();
    }
}
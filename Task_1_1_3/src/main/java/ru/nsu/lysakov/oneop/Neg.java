package ru.nsu.lysakov.oneop;

import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.base.Number;
import ru.nsu.lysakov.operation.ExpressionPriority;

/**
 * Класс, представляющий операцию унарного отрицания.
 */
public class Neg extends FirstExpression {

    /**
     * Создаёт выражение унарного отрицания.
     *
     * @param value выражение, знак которого необходимо изменить
     */
    public Neg(Expression value) {
        super(value);
    }

    /**
     * Сравнивает текущее выражение с другим объектом.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если выражения равны, иначе {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Neg other)) {
            return false;
        }

        return value.equals(other.value);
    }

    /**
     * Упрощает выражение унарного отрицания.
     *
     * <p>Если операнд является числом, возвращается число
     * с противоположным знаком. Двойное отрицание удаляется.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression ans = value.simplify();

        if (ans instanceof Number number) {
            return new Number(-number.evaluate(""));
        }

        if (ans instanceof Neg neg) {
            return neg.value;
        }

        return new Neg(ans);
    }

    /**
     * Вычисляет производную выражения с унарным отрицанием.
     *
     * @param varName имя переменной, по которой берётся производная
     * @return производная выражения с противоположным знаком
     */
    @Override
    public Expression derivative(String varName) {
        Expression ans = value.derivative(varName);
        return new Neg(ans);
    }

    /**
     * Вычисляет значение выражения с противоположным знаком.
     *
     * @param signification строка со значениями переменных
     * @return значение выражения с противоположным знаком
     *         или {@code null}, если значение вычислить невозможно
     */
    @Override
    public Double evaluate(String signification) {
        Double val = value.evaluate(signification);

        if (val == null) {
            return null;
        }

        return -val;
    }

    /**
     * Возвращает строковое представление отрицательного выражения.
     *
     * <p>Если приоритет внутреннего выражения ниже,
     * оно заключается в скобки.
     *
     * @return строковое представление выражения
     */
    @Override
    public String toString() {
        if (value.getPriority() < getPriority()) {
            return "-(" + value + ")";
        }

        return "-" + value;
    }

    /**
     * Возвращает приоритет операции унарного отрицания.
     *
     * @return приоритет операции
     */
    @Override
    public int getPriority() {
        return ExpressionPriority.NEG.getValue();
    }
}
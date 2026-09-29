package ru.nsu.lysakov.operation;

import java.util.function.BiFunction;
import java.util.function.Function;
import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.binaryop.Add;
import ru.nsu.lysakov.binaryop.Div;
import ru.nsu.lysakov.binaryop.Mul;
import ru.nsu.lysakov.binaryop.Pow;
import ru.nsu.lysakov.binaryop.Sub;
import ru.nsu.lysakov.oneop.Neg;

/**
 * Список поддерживаемых математических операций.
 */
public enum Operation {

    ADD(
            '+',
            ExpressionPriority.ADD_SUB,
            Add::new
    ),

    SUB(
            '-',
            ExpressionPriority.ADD_SUB,
            Sub::new
    ),

    MUL(
            '*',
            ExpressionPriority.MUL_DIV,
            Mul::new
    ),

    DIV(
            '/',
            ExpressionPriority.MUL_DIV,
            Div::new
    ),

    POW(
            '^',
            ExpressionPriority.POW,
            Pow::new
    ),

    NEG(
            '-',
            ExpressionPriority.NEG,
            Neg::new
    );

    private final char symbol;
    private final ExpressionPriority priority;

    private final BiFunction<Expression, Expression, Expression> binaryCreator;
    private final Function<Expression, Expression> unaryCreator;

    Operation(
            char symbol,
            ExpressionPriority priority,
            BiFunction<Expression, Expression, Expression> creator
    ) {
        this.symbol = symbol;
        this.priority = priority;
        this.binaryCreator = creator;
        this.unaryCreator = null;
    }

    Operation(
            char symbol,
            ExpressionPriority priority,
            Function<Expression, Expression> creator
    ) {
        this.symbol = symbol;
        this.priority = priority;
        this.binaryCreator = null;
        this.unaryCreator = creator;
    }

    /**
     * Возвращает символ операции.
     *
     * @return символ операции
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Возвращает приоритет операции.
     *
     * @return приоритет
     */
    public int getPriority() {
        return priority.getValue();
    }

    /**
     * Создаёт бинарное выражение.
     *
     * @param left левый операнд
     * @param right правый операнд
     * @return созданное выражение
     */
    public Expression createBinary(
            Expression left,
            Expression right
    ) {
        return binaryCreator.apply(left, right);
    }

    /**
     * Создаёт унарное выражение.
     *
     * @param value операнд
     * @return созданное выражение
     */
    public Expression createUnary(Expression value) {
        return unaryCreator.apply(value);
    }

    /**
     * Проверяет, является ли операция унарной.
     *
     * @return {@code true}, если операция унарная
     */
    public boolean isUnary() {
        return unaryCreator != null;
    }

    /**
     * Проверяет, является ли символ бинарной операцией.
     *
     * @param symbol символ операции
     * @return операция, соответствующая символу, или {@code null},
     *         если операция не найдена
     */
    public static Operation findBinary(char symbol) {
        for (Operation operation : values()) {
            if (operation.symbol == symbol && !operation.isUnary()) {
                return operation;
            }
        }

        return null;
    }
}
package ru.nsu.lysakov.parse;

import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.base.Number;
import ru.nsu.lysakov.base.Variable;
import ru.nsu.lysakov.operation.Operation;

/**
 * Класс для преобразования строки с математическим выражением
 * в дерево объектов {@link Expression}.
 */
public class ExpressionParser {

    private String stringExpr;
    private int ind;

    /**
     * Создаёт новый парсер математических выражений.
     */
    public ExpressionParser() {
        stringExpr = "";
        ind = 0;
    }

    /**
     * Преобразует строку с математическим выражением
     * в объект {@link Expression}.
     *
     * @param expression строковое математическое выражение
     * @return дерево математического выражения
     */
    public Expression parse(String expression) {
        ind = 0;
        expression = expression.replace(" ", "");
        stringExpr = expression + " ";

        return parseExpr(1);
    }

    /**
     * Проверяет, может ли символ входить в число или имя переменной.
     *
     * @param symbol проверяемый символ
     * @return {@code true}, если символ может входить в число
     *         или имя переменной, иначе {@code false}
     */
    private boolean isNumberOrVariableSymbol(char symbol) {
        return symbol != '('
                && symbol != ')'
                && Operation.findBinary(symbol) == null
                && symbol != Operation.NEG.getSymbol()
                && symbol != ' ';
    }

    /**
     * Разбирает число или переменную.
     *
     * @return полученное выражение
     */
    private Expression parseNumberOrVariable() {
        StringBuilder value = new StringBuilder();
        boolean hasPoint = false;

        while (isNumberOrVariableSymbol(stringExpr.charAt(ind))) {
            char symbol = stringExpr.charAt(ind);

            if (symbol == '.') {
                if (hasPoint) {
                    return null;
                }

                hasPoint = true;
            }

            value.append(symbol);
            ind++;
        }

        String stringValue = value.toString();

        try {
            return new Number(Double.parseDouble(stringValue));
        } catch (NumberFormatException exception) {
            return new Variable(stringValue);
        }
    }

    /**
     * Разбирает первичное выражение.
     *
     * <p>Первичным выражением является число, переменная,
     * выражение в скобках или унарное отрицание.
     *
     * @return разобранное выражение
     */
    private Expression parsePrimary() {
        char symbol = stringExpr.charAt(ind);

        if (symbol == Operation.NEG.getSymbol()) {
            ind++;

            Expression value = parsePrimary();

            return Operation.NEG.createUnary(value);
        }

        if (symbol == '(') {
            ind++;

            Expression value = parseExpr(1);

            if (stringExpr.charAt(ind) == ')') {
                ind++;
            }

            return value;
        }

        return parseNumberOrVariable();
    }

    /**
     * Разбирает выражение с учётом приоритетов операций.
     *
     * @param minPriority минимальный допустимый приоритет операции
     * @return разобранное выражение
     */
    private Expression parseExpr(int minPriority) {
        Expression lhs = parsePrimary();

        if (lhs == null) {
            return null;
        }

        while (true) {
            char symbol = stringExpr.charAt(ind);

            Operation operation = Operation.findBinary(symbol);

            if (operation == null) {
                break;
            }

            int priority = operation.getPriority();

            if (priority < minPriority) {
                break;
            }

            ind++;

            Expression rhs = parseExpr(priority + 1);

            lhs = operation.createBinary(lhs, rhs);
        }

        return lhs;
    }
}

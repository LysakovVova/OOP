package ru.nsu.lysakov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import ru.nsu.lysakov.exception.ExpressionException;
import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.base.Number;
import ru.nsu.lysakov.base.Variable;
import ru.nsu.lysakov.binaryop.Add;
import ru.nsu.lysakov.binaryop.Div;
import ru.nsu.lysakov.binaryop.Mul;
import ru.nsu.lysakov.binaryop.Pow;
import ru.nsu.lysakov.binaryop.Sub;
import ru.nsu.lysakov.oneop.Neg;
import ru.nsu.lysakov.operation.ExpressionPriority;
import ru.nsu.lysakov.operation.Operation;
import ru.nsu.lysakov.parse.ExpressionParser;

/**
 * Тесты для классов математических выражений.
 */
class MainTest {
    /**
     * Проверяет равенство чисел.
     */
    @Test
    void numbersShouldBeEqual() throws ExpressionException {
        Number first = new Number(5);
        Number second = new Number(5);
        Number third = new Number(10);

        assertEquals(first, second);
        assertFalse(first.equals(third));
    }

    /**
     * Проверяет упрощение числа.
     */
    @Test
    void numberShouldSimplifyToItself() throws ExpressionException {
        Number number = new Number(5);

        assertEquals(number, number.simplify());
    }

    /**
     * Проверяет производную числа.
     */
    @Test
    void numberDerivativeShouldBeZero() throws ExpressionException {
        Number number = new Number(10);

        assertEquals(new Number(0), number.derivative("x"));
    }

    /**
     * Проверяет вычисление переменной с заданным значением.
     */
    @Test
    void variableShouldEvaluateStoredValue() throws ExpressionException {
        Variable variable = new Variable("x", 5);

        assertEquals(5.0, variable.evaluate(""));
    }

    /**
     * Проверяет изменение значения переменной.
     */
    @Test
    void variableShouldSetValue() throws ExpressionException {
        Variable variable = new Variable("x");

        variable.setValue(15);

        assertEquals(15.0, variable.evaluate(""));
    }

    /**
     * Проверяет получение значения переменной из строки.
     */
    @Test
    void variableShouldEvaluateFromSignification() throws ExpressionException {
        Variable variable = new Variable("x");

        assertEquals(
                10.0,
                variable.evaluate("x = 10;")
        );
    }

    /**
     * Проверяет получение нескольких значений переменных из строки.
     */
    @Test
    void variablesShouldEvaluateFromSignification() throws ExpressionException {
        Variable x = new Variable("x");
        Variable y = new Variable("y");

        String signification = "x = 5; y = 7;";

        assertEquals(5.0, x.evaluate(signification));
        assertEquals(7.0, y.evaluate(signification));
    }

    /**
     * Проверяет случай отсутствия значения переменной.
     */
    @Test
    void variableWithoutValueShouldReturnNull() throws ExpressionException {
        Variable variable = new Variable("x");

        assertNull(variable.evaluate(""));
    }

    /**
     * Проверяет производную переменной по самой себе.
     */
    @Test
    void variableDerivativeByItselfShouldBeOne() throws ExpressionException {
        Variable variable = new Variable("x");

        assertEquals(
                new Number(1),
                variable.derivative("x")
        );
    }

    /**
     * Проверяет производную переменной по другой переменной.
     */
    @Test
    void variableDerivativeByAnotherVariableShouldBeZero() throws ExpressionException {
        Variable variable = new Variable("x");

        assertEquals(
                new Number(0),
                variable.derivative("y")
        );
    }

    /**
     * Проверяет равенство переменных.
     */
    @Test
    void variablesWithSameNameShouldBeEqual() throws ExpressionException {
        Variable first = new Variable("x");
        Variable second = new Variable("x");
        Variable third = new Variable("y");

        assertEquals(first, second);
        assertFalse(first.equals(third));
    }

    /**
     * Проверяет операцию отрицания.
     */
    @Test
    void negShouldEvaluateCorrectly() throws ExpressionException {
        Neg neg = new Neg(new Number(5));

        assertEquals(-5.0, neg.evaluate(""));
    }

    /**
     * Проверяет упрощение двойного отрицания.
     */
    @Test
    void doubleNegShouldSimplify() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Neg(new Neg(x));

        assertEquals(x, expression.simplify());
    }

    /**
     * Проверяет строковое представление отрицания.
     */
    @Test
    void negShouldUseParenthesesWhenNeeded() throws ExpressionException {
        Expression expression = new Neg(
                new Add(
                        new Variable("x"),
                        new Number(1)
                )
        );

        assertEquals("-(x + 1.0)", expression.toString());
    }

    /**
     * Проверяет вычисление суммы.
     */
    @Test
    void addShouldEvaluateCorrectly() throws ExpressionException {
        Expression expression = new Add(
                new Number(2),
                new Number(3)
        );

        assertEquals(5.0, expression.evaluate(""));
    }

    /**
     * Проверяет упрощение суммы чисел.
     */
    @Test
    void addNumbersShouldSimplify() throws ExpressionException {
        Expression expression = new Add(
                new Number(2),
                new Number(3)
        );

        assertEquals(
                new Number(5),
                expression.simplify()
        );
    }

    /**
     * Проверяет сложение выражения с нулём.
     */
    @Test
    void addZeroRightShouldSimplify() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Add(
                x,
                new Number(0)
        );

        assertEquals(x, expression.simplify());
    }

    /**
     * Проверяет упрощение суммы одинаковых выражений.
     */
    @Test
    void addSameExpressionsShouldSimplify() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Add(x, x);

        assertEquals(
                "2.0 * x",
                expression.simplify().toString()
        );
    }

    /**
     * Проверяет производную суммы.
     */
    @Test
    void addDerivativeShouldBeCorrect() throws ExpressionException {
        Expression expression = new Add(
                new Variable("x"),
                new Number(5)
        );

        assertEquals(
                new Number(1),
                expression.derivative("x").simplify()
        );
    }

    /**
     * Проверяет вычисление разности.
     */
    @Test
    void subShouldEvaluateCorrectly() throws ExpressionException {
        Expression expression = new Sub(
                new Number(10),
                new Number(3)
        );

        assertEquals(7.0, expression.evaluate(""));
    }

    /**
     * Проверяет упрощение одинаковых выражений при вычитании.
     */
    @Test
    void subSameExpressionsShouldBeZero() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Sub(x, x);

        assertEquals(
                new Number(0),
                expression.simplify()
        );
    }

    /**
     * Проверяет вычитание нуля.
     */
    @Test
    void subZeroShouldSimplify() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Sub(
                x,
                new Number(0)
        );

        assertEquals(x, expression.simplify());
    }

    /**
     * Проверяет вычитание выражения из нуля.
     */
    @Test
    void zeroSubExpressionShouldBecomeNeg() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Sub(
                new Number(0),
                x
        );

        assertInstanceOf(
                Neg.class,
                expression.simplify()
        );
    }

    /**
     * Проверяет производную разности.
     */
    @Test
    void subDerivativeShouldBeCorrect() throws ExpressionException {
        Expression expression = new Sub(
                new Variable("x"),
                new Number(5)
        );

        assertEquals(
                new Number(1),
                expression.derivative("x").simplify()
        );
    }

    /**
     * Проверяет вычисление произведения.
     */
    @Test
    void mulShouldEvaluateCorrectly() throws ExpressionException {
        Expression expression = new Mul(
                new Number(4),
                new Number(5)
        );

        assertEquals(20.0, expression.evaluate(""));
    }

    /**
     * Проверяет упрощение произведения чисел.
     */
    @Test
    void mulNumbersShouldSimplify() throws ExpressionException {
        Expression expression = new Mul(
                new Number(4),
                new Number(5)
        );

        assertEquals(
                new Number(20),
                expression.simplify()
        );
    }

    /**
     * Проверяет умножение нуля на выражение.
     */
    @Test
    void zeroMulExpressionShouldBeZero() throws ExpressionException {
        Expression expression = new Mul(
                new Number(0),
                new Variable("x")
        );

        assertEquals(
                new Number(0),
                expression.simplify()
        );
    }

    /**
     * Проверяет умножение на единицу.
     */
    @Test
    void mulByOneShouldSimplify() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Mul(
                x,
                new Number(1)
        );

        assertEquals(x, expression.simplify());
    }

    /**
     * Проверяет перестановку числа перед переменной.
     */
    @Test
    void mulShouldPutNumberBeforeVariable() throws ExpressionException {
        Expression expression = new Mul(
                new Variable("x"),
                new Number(2)
        );

        assertEquals(
                "2.0 * x",
                expression.simplify().toString()
        );
    }

    /**
     * Проверяет правило производной произведения.
     */
    @Test
    void mulDerivativeShouldBeCorrect() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Mul(x, x);

        Expression derivative = expression.derivative("x");

        assertEquals(4.0, derivative.evaluate("x = 2;"));
    }

    /**
     * Проверяет вычисление частного.
     */
    @Test
    void divShouldEvaluateCorrectly() throws ExpressionException {
        Expression expression = new Div(
                new Number(10),
                new Number(2)
        );

        assertEquals(5.0, expression.evaluate(""));
    }

    /**
     * Проверяет деление на ноль.
     */
    @Test
    void divisionByZeroShouldReturnNull() throws ExpressionException {
        Expression expression = new Div(
                new Number(10),
                new Number(0)
        );

        assertNull(expression.evaluate(""));
    }

    /**
     * Проверяет упрощение числового деления.
     */
    @Test
    void divNumbersShouldSimplify() throws ExpressionException {
        Expression expression = new Div(
                new Number(10),
                new Number(2)
        );

        assertEquals(
                new Number(5),
                expression.simplify()
        );
    }

    /**
     * Проверяет деление на единицу.
     */
    @Test
    void divByOneShouldSimplify() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Div(
                x,
                new Number(1)
        );

        assertEquals(x, expression.simplify());
    }

    /**
     * Проверяет правило производной частного.
     */
    @Test
    void divDerivativeShouldBeCorrect() throws ExpressionException {
        Expression expression = new Div(
                new Variable("x"),
                new Number(2)
        );

        Expression derivative = expression.derivative("x");

        assertEquals(0.5, derivative.evaluate("x = 5;"));
    }

    /**
     * Проверяет скобки при делении суммы.
     */
    @Test
    void divShouldUseParentheses() throws ExpressionException {
        Expression expression = new Div(
                new Add(
                        new Variable("x"),
                        new Number(1)
                ),
                new Variable("x")
        );

        assertEquals(
                "(x + 1.0) / x",
                expression.toString()
        );
    }

    /**
     * Проверяет вычисление степени.
     */
    @Test
    void powShouldEvaluateCorrectly() throws ExpressionException {
        Expression expression = new Pow(
                new Number(2),
                new Number(3)
        );

        assertEquals(8.0, expression.evaluate(""));
    }

    /**
     * Проверяет упрощение числовой степени.
     */
    @Test
    void powNumbersShouldSimplify() throws ExpressionException {
        Expression expression = new Pow(
                new Number(2),
                new Number(3)
        );

        assertEquals(
                new Number(8),
                expression.simplify()
        );
    }

    /**
     * Проверяет нулевую степень.
     */
    @Test
    void powZeroShouldBeOne() throws ExpressionException {
        Expression expression = new Pow(
                new Variable("x"),
                new Number(0)
        );

        assertEquals(
                new Number(1),
                expression.simplify()
        );
    }

    /**
     * Проверяет первую степень.
     */
    @Test
    void powOneShouldReturnBase() throws ExpressionException {
        Variable x = new Variable("x");

        Expression expression = new Pow(
                x,
                new Number(1)
        );

        assertEquals(x, expression.simplify());
    }

    /**
     * Проверяет единицу в основании степени.
     */
    @Test
    void onePowExpressionShouldBeOne() throws ExpressionException {
        Expression expression = new Pow(
                new Number(1),
                new Variable("x")
        );

        assertEquals(
                new Number(1),
                expression.simplify()
        );
    }

    /**
     * Проверяет производную степени.
     */
    @Test
    void powDerivativeShouldBeCorrect() throws ExpressionException {
        Expression expression = new Pow(
                new Variable("x"),
                new Number(3)
        );

        Expression derivative = expression.derivative("x");

        assertEquals(
                12.0,
                derivative.evaluate("x = 2;")
        );
    }

    /**
     * Проверяет равенство операций сложения.
     */
    @Test
    void addExpressionsShouldBeEqual() throws ExpressionException {
        Expression first = new Add(
                new Variable("x"),
                new Number(2)
        );

        Expression second = new Add(
                new Variable("x"),
                new Number(2)
        );

        assertEquals(first, second);
    }

    /**
     * Проверяет неравенство различных операций.
     */
    @Test
    void differentExpressionsShouldNotBeEqual() throws ExpressionException {
        Expression add = new Add(
                new Variable("x"),
                new Number(2)
        );

        Expression mul = new Mul(
                new Variable("x"),
                new Number(2)
        );

        assertFalse(add.equals(mul));
    }

    /**
     * Проверяет приоритет сложения и вычитания.
     */
    @Test
    void addSubPriorityShouldBeCorrect() throws ExpressionException {
        assertEquals(
                ExpressionPriority.ADD_SUB.getValue(),
                new Add(new Number(1), new Number(2)).getPriority()
        );

        assertEquals(
                ExpressionPriority.ADD_SUB.getValue(),
                new Sub(new Number(1), new Number(2)).getPriority()
        );
    }

    /**
     * Проверяет приоритет умножения и деления.
     */
    @Test
    void mulDivPriorityShouldBeCorrect() throws ExpressionException {
        assertEquals(
                ExpressionPriority.MUL_DIV.getValue(),
                new Mul(new Number(1), new Number(2)).getPriority()
        );

        assertEquals(
                ExpressionPriority.MUL_DIV.getValue(),
                new Div(new Number(1), new Number(2)).getPriority()
        );
    }

    /**
     * Проверяет приоритет степени.
     */
    @Test
    void powPriorityShouldBeCorrect() throws ExpressionException {
        assertEquals(
                ExpressionPriority.POW.getValue(),
                new Pow(new Number(1), new Number(2)).getPriority()
        );
    }

    /**
     * Проверяет приоритет отрицания.
     */
    @Test
    void negPriorityShouldBeCorrect() throws ExpressionException {
        assertEquals(
                ExpressionPriority.NEG.getValue(),
                new Neg(new Number(1)).getPriority()
        );
    }

    /**
     * Проверяет поиск бинарной операции по символу.
     */
    @Test
    void shouldFindBinaryOperationBySymbol() throws ExpressionException {
        assertEquals(Operation.ADD, Operation.findBinary('+'));
        assertEquals(Operation.SUB, Operation.findBinary('-'));
        assertEquals(Operation.MUL, Operation.findBinary('*'));
        assertEquals(Operation.DIV, Operation.findBinary('/'));
        assertEquals(Operation.POW, Operation.findBinary('^'));
    }

    /**
     * Проверяет отсутствие операции для неизвестного символа.
     */
    @Test
    void unknownOperationShouldReturnNull() throws ExpressionException {
        assertNull(Operation.findBinary('%'));
    }

    /**
     * Проверяет создание операции через enum.
     */
    @Test
    void operationShouldCreateBinaryExpression() throws ExpressionException {
        Expression expression = Operation.ADD.createBinary(
                new Number(2),
                new Number(3)
        );

        assertInstanceOf(Add.class, expression);
        assertEquals(5.0, expression.evaluate(""));
    }

    /**
     * Проверяет разбор числа парсером.
     */
    @Test
    void parserShouldParseNumber() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("5");

        assertEquals(5.0, expression.evaluate(""));
    }

    /**
     * Проверяет разбор переменной парсером.
     */
    @Test
    void parserShouldParseVariable() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("x");

        assertEquals(10.0, expression.evaluate("x = 10;"));
    }

    /**
     * Проверяет разбор сложения.
     */
    @Test
    void parserShouldParseAddition() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("2 + 3");

        assertEquals(5.0, expression.evaluate(""));
    }

    /**
     * Проверяет приоритет умножения над сложением.
     */
    @Test
    void parserShouldRespectOperationPriority() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("2 + 3 * 4");

        assertEquals(14.0, expression.evaluate(""));
    }

    /**
     * Проверяет работу скобок.
     */
    @Test
    void parserShouldParseParentheses() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("(2 + 3) * 4");

        assertEquals(20.0, expression.evaluate(""));
    }

    /**
     * Проверяет разбор унарного минуса.
     */
    @Test
    void parserShouldParseNegation() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("-x");

        assertEquals(-5.0, expression.evaluate("x = 5;"));
    }

    /**
     * Проверяет отрицание выражения в скобках.
     */
    @Test
    void parserShouldParseNegatedParentheses() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("-(x + 2)");

        assertEquals(-7.0, expression.evaluate("x = 5;"));
    }

    /**
     * Проверяет отрицательную переменную после умножения.
     */
    @Test
    void parserShouldParseNegationAfterOperation() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("2 * -x");

        assertEquals(-10.0, expression.evaluate("x = 5;"));
    }

    /**
     * Проверяет сложное математическое выражение.
     */
    @Test
    void parserShouldParseComplexExpression() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression =
                parser.parse("2 + x * 3 - 4 / 2");

        assertEquals(
                15.0,
                expression.evaluate("x = 5;")
        );
    }

    /**
     * Проверяет выражение со степенью.
     */
    @Test
    void parserShouldParsePower() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("x^3");

        assertEquals(
                8.0,
                expression.evaluate("x = 2;")
        );
    }

    /**
     * Проверяет производную выражения, полученного парсером.
     */
    @Test
    void parsedExpressionDerivativeShouldBeCorrect() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("x^2 + 3*x");

        Expression derivative = expression.derivative("x");

        assertEquals(
                7.0,
                derivative.evaluate("x = 2;")
        );
    }

    /**
     * Проверяет упрощение выражения, полученного парсером.
     */
    @Test
    void parsedExpressionShouldSimplify() throws ExpressionException {
        ExpressionParser parser = new ExpressionParser();

        Expression expression = parser.parse("x + 0");

        assertEquals(
                new Variable("x"),
                expression.simplify()
        );
    }
}

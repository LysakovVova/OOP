package ru.nsu.lysakov;


import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.parse.ExpressionParser;

/**
 * Главный класс программы.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();

        String[] listExpr = {
            "3 * X",
            "X * 3",
            "(5 + 2) / 2",
            "(5 + 2) / X",
            "(5 + 2) / (3 + 2)",
            "(2 * X) / (X * 3)",
            "(2 * X) * (3 * X)",
            "(5 + 3) ^ (2 + 4)",
            "(X + 2)^3",
            "X * X * X * X * X * 3 * 6 * 3",
            "0 + 0 + 1 * 0"
        };
        Expression expr;
        Expression d;
        Double f;
        for (String x : listExpr) {
            expr = parser.parse(x);
            System.out.println(expr);

            expr = expr.simplify();
            System.out.println(expr);

            d = expr.derivative("X");
            System.out.println(d);
            if (d != null) {
                d = d.simplify();
                System.out.println(d);
            }

            f = expr.evaluate("");
            System.out.println(f);
            System.out.print("\n");
        }

    }
}
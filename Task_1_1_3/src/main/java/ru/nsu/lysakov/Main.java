package ru.nsu.lysakov;


import ru.nsu.lysakov.base.Number;
import ru.nsu.lysakov.base.Expression;
import ru.nsu.lysakov.base.Add;
import ru.nsu.lysakov.base.Mul;
import ru.nsu.lysakov.base.Div;
import ru.nsu.lysakov.parse.ExpressionParser;


public class Main {

    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();

        Expression expr1 = parser.parse("(5 + 3) / X");

        Expression expr2 = parser.parse("(X * 2) / (X * 3)");

        Expression expr3 = parser.parse("2 + (((Y * (-X + 5)");

        System.out.print(expr1);
        System.out.print("\n");
        System.out.println(expr1.evaluate("X = 1;"));
        System.out.print("\n");

        System.out.println(expr2);
        Expression expr4 = expr2.derivative("X");
        System.out.println(expr4);
        System.out.println(expr4.simplify());
        System.out.print("\n");


        System.out.print(expr3);
        System.out.print("\n");
        System.out.println(expr3.evaluate("X = 5; Y = 4;"));

    }
}
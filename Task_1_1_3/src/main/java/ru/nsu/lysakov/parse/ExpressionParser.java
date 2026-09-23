package ru.nsu.lysakov.parse;

import ru.nsu.lysakov.base.*;
import ru.nsu.lysakov.base.Number;

import java.util.InputMismatchException;

public class ExpressionParser {

    private Expression expr;
    private String Sexpr;
    private int ind;

    public ExpressionParser() {
        expr = null;
        Sexpr = "";
        ind = 0;
    }

    public Expression parse (String expr) {
        ind = 0;
        expr = expr.replace(" ", "");
        Sexpr = expr + " ";
        while (true) {
            this.expr = parseExpr(1);
            if (ind == expr.length()) {
                break;
            }
        }
        return this.expr;
    }

    private boolean isNoNumberAndVariable(char x) {
        if (x == '(' ||
            x == ')' ||
            x == '+' ||
            x == '-' ||
            x == '*' ||
            x == '/' ||
            x == ' ') {
            return false;
        }
        return true;
    }
    private boolean isOp(char x) {
        if (x == '+' ||
            x == '-' ||
            x == '*' ||
            x == '/') {
            return true;
        }
        return false;
    }
    private int getPrioOp(char x) {
        if (x == '+') {
            return 1;
        }
        if (x == '-') {
            return 1;
        }
        if (x == '*') {
            return 2;
        }
        if (x == '/') {
            return 2;
        }
        return 1;
    }

    private Expression parseNumber() {
        String val = "";
        boolean neg = false;
        if (Sexpr.charAt(ind) == '-') {
            ind++;
            neg = true;
        }
        boolean flag = false;
        while (isNoNumberAndVariable(Sexpr.charAt(ind))) {
            val += Sexpr.charAt(ind);
            if (Sexpr.charAt(ind) == '.') {
                if (flag) {
                    return null;
                } else{
                    flag = true;
                }
            }
            ind++;
        }
        Double number;
        try {
            number = Double.parseDouble(val);
            if (neg) {
                number = -number;
            }
            return new Number(number);
        } catch (NumberFormatException  e) {
            if (neg) {
                return new Neg(new Variable(val));
            } else {
                return new Variable(val);
            }
        }
    }

    private Expression parsePrimary() {
        if (Sexpr.charAt(ind) == '(') {
            ind++;
            Expression val = parseExpr(1);
            if (Sexpr.charAt(ind) == ')') {
                ind++;
            }
            return val;
        }
        return parseNumber();
    }

    private Expression parseExpr(int minPrio) {
        Expression lhs = parsePrimary();
        if (lhs == null) {
            return null;
        }

        while (true) {
            char op = Sexpr.charAt(ind);
            if (!isOp(op)) {break;}

            int prio = getPrioOp(op);
            if (prio < minPrio) {break;}

            ind++;
            Expression rhs = parseExpr(prio + 1);

            if (op == '+') {lhs = new Add(lhs, rhs);}
            if (op == '-') {lhs = new Sub(lhs, rhs);}
            if (op == '*') {lhs = new Mul(lhs, rhs);}
            if (op == '/') {lhs = new Div(lhs, rhs);}
        }

        return lhs;
    }
}

package ru.nsu.lysakov.base;

public class Div extends BinaryExpression {
    public Div (Expression left, Expression right) {
        super(left, right);
    }

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Div other)) {
            return false;
        }

        return sameOperands(other);
    }

    @Override
    public Expression simplify() {
        Expression leftAns = left.simplify();
        Expression rightAns = right.simplify();

        if (leftAns instanceof Number leftNumber &&
                rightAns instanceof Number rightNumber) {

            if (rightNumber.evaluate("") == 0) {
                return null;
            }

            return new Number(
                    leftNumber.evaluate("") / rightNumber.evaluate("")
            );
        }

        if (rightAns instanceof Number rightNumber &&
                rightNumber.evaluate("") == 1) {
            return leftAns;
        }

        return new Div(leftAns, rightAns);
    }

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

    @Override
    public String toString() {
        String leftStr = left.toString();
        String rightStr = right.toString();

        if (left.getPriority() < this.getPriority()) {
            leftStr = "(" + leftStr + ")";
        }

        if (right.getPriority() <= this.getPriority()) {
            rightStr = "(" + rightStr + ")";
        }

        return leftStr + " / " + rightStr;
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
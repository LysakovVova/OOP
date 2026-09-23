package ru.nsu.lysakov.base;

public class Sub extends BinaryExpression {
    public Sub (Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sub other)) {
            return false;
        }

        return sameOperands(other);
    }

    @Override
    public Expression simplify() {
        Expression leftAns = left.simplify();
        Expression rightAns = right.simplify();

        if (leftAns.equals(rightAns)) {
            return new Number(0);
        }

        if ( leftAns instanceof Number leftNumber &&
            rightAns instanceof Number rightNumber) {
            return new Number(leftNumber.evaluate(null) - rightNumber.evaluate(null));
        }

        if (rightAns instanceof Number rightNumber &&
                rightNumber.evaluate("") == 0) {
            return leftAns;
        }

        if (leftAns instanceof Number leftNumber &&
                leftNumber.evaluate("") == 0) {
            return new Neg(rightAns);
        }

        return new Sub(leftAns, rightAns);
    }

    @Override
    public Expression derivative(String varName) {
        Expression leftAns = left.derivative(varName);
        Expression rightAns = right.derivative(varName);
        return new Sub(leftAns, rightAns);
    }

    @Override
    public Double evaluate(String signification) {
        Double leftAns = left.evaluate(signification);
        Double rightAns = right.evaluate(signification);
        if (leftAns == null || rightAns == null) {
            return null;
        }
        return leftAns - rightAns;
    }

    @Override
    public String toString() {
        return left + " - " + right;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
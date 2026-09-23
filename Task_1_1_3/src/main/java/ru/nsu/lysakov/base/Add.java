package ru.nsu.lysakov.base;

public class Add extends BinaryExpression {
    public Add (Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Add other)) {
            return false;
        }

        return sameOperands(other);
    }

    @Override
    public Expression simplify() {
        Expression leftAns = left.simplify();
        Expression rightAns = right.simplify();

        if (leftAns.equals(rightAns)) {
            return new Mul(new Number(2), leftAns);
        }

        if (leftAns instanceof Number leftNumber &&
                rightAns instanceof Number rightNumber) {
            return new Number(
                    leftNumber.evaluate("")
                          + rightNumber.evaluate("")
            );
        }

        if (rightAns instanceof Number rightNumber &&
                rightNumber.evaluate("") == 0) {
            return leftAns;
        }

        if (leftAns instanceof Number leftNumber &&
                leftNumber.evaluate("") == 0) {
            return rightAns;
        }

        return new Add(leftAns, rightAns);
    }

    @Override
    public Expression derivative(String varName) {
        Expression leftAns = left.derivative(varName);
        Expression rightAns = right.derivative(varName);
        if (leftAns == null || rightAns == null) {
            return null;
        }
        return new Add(leftAns, rightAns);
    }

    @Override
    public Double evaluate(String signification ) {
        Double leftAns = left.evaluate(signification);
        Double rightAns = right.evaluate(signification);
        if (leftAns == null || rightAns == null) {
            return null;
        }
        return leftAns + rightAns;
    }

    @Override
    public String toString() {
        return left + " + " + right;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
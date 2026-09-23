package ru.nsu.lysakov.base;

public abstract class BinaryExpression extends Expression {
    protected Expression left;
    protected Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    protected boolean sameOperands(BinaryExpression other) {
        return left.equals(other.left)
                && right.equals(other.right);
    }
}

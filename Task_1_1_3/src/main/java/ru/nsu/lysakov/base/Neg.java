package ru.nsu.lysakov.base;

public class Neg extends Expression {
    private final Expression value;

    public Neg(Expression value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Neg other)) {
            return false;
        }

        return value.equals(other.value);
    }

    @Override
    public Expression simplify() {
        Expression ans = value.simplify();

        if (ans instanceof Number number) {
            return new Number(-number.evaluate(""));
        }

        if (ans instanceof Neg neg) {
            return neg.value;
        }

        return new Neg(ans);
    }

    @Override
    public Expression derivative(String varName) {
        Expression ans = value.derivative(varName);
        return new Neg(ans);
    }

    @Override
    public Double evaluate(String signification) {
        Double val = value.evaluate(signification);

        if (val == null) {
            return null;
        }

        return -val;
    }

    @Override
    public String toString() {
        if (value.getPriority() < getPriority()) {
            return "-(" + value + ")";
        }

        return "-" + value;
    }

    @Override
    public int getPriority() {
        return 3;
    }
}

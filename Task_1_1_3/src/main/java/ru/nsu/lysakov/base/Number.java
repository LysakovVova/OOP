package ru.nsu.lysakov.base;

public class Number extends Expression {
    private final Double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Number other)) {
            return false;
        }

        return value.equals(other.value);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public Expression derivative(String varName) {
        return new Number(0);
    }
    @Override
    public Double evaluate(String signification) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
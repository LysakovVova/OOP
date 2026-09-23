package ru.nsu.lysakov.base;

public class Variable extends Expression {
    private Double value;
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public Variable(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Variable other)) {
            return false;
        }

        return name.equals(other.name);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public Expression derivative(String varName) {
        if (varName.equals(name)) {
            return new Number(1);
        }

        return new Number(0);
    }

    @Override
    public Double evaluate(String signification) {
        int pos = signification.indexOf(name);

        if (pos == -1) {
            return value;
        }

        int equalPos = signification.indexOf('=', pos);

        if (equalPos == -1) {
            return value;
        }

        int endPos = signification.indexOf(';', equalPos);

        String val;

        if (endPos == -1) {
            val = signification.substring(equalPos + 1);
        } else {
            val = signification.substring(equalPos + 1, endPos);
        }

        val = val.trim();

        value = Double.parseDouble(val);
        return value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return name;
        }

        return value.toString();
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
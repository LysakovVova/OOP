package ru.nsu.lysakov.base;

public abstract class Expression {
    public abstract Double evaluate(String Signification);
    protected abstract int getPriority();

    public abstract Expression derivative(String varName);

    public abstract Expression simplify();

    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();

}

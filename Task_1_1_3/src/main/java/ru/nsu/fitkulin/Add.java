package ru.nsu.fitkulin;

public abstract class Add extends Expression {

    @Override
    public void print() {

    }

    @Override
    public Expression derivative(String variable) {
        return null;
    }

    @Override
    public int eval(String assignments) {
        return 0;
    }
}

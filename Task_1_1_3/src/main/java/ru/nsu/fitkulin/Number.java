package ru.nsu.fitkulin;

public class Number extends Expression {

    int value;
    public Number(int value) {
        super();
        this.value = value;
    }

    @Override
    public void print() {

    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int eval(String assignments) {
        return value;
    }
}

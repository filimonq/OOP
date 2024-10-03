package ru.nsu.fitkulin;

public class Number extends Expression {
    double value;
    public Number(int value) {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        if (this.value % 1 == 0) {
            return "" + (int) this.value;
        } else {
            return "" + this.value;
        }
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public double eval(String assignments) {
        return value;
    }
}

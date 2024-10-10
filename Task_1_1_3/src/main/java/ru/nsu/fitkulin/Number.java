package ru.nsu.fitkulin;

/**
 * class responsible for the number.
 */
public class Number extends Expression {
    double value;
    public Number(double value) {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        if (this.value % 1 == 0) {
            return "" + (int) this.value; // если число целое, то приводим к инту
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

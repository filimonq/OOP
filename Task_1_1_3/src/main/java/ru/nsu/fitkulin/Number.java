package ru.nsu.fitkulin;

/**
 * class responsible for the number.
 */
public class Number extends Expression {
    double value;

    /**
     * constructor.
     */
    public Number(double value) {
        super();
        this.value = value;
    }

    /**
     * string for number.
     * @return int if there is no fractional part.
     */
    @Override
    public String toString() {
        if (this.value % 1 == 0) {
            return "" + (int) this.value;
        } else {
            return "" + this.value;
        }
    }

    /**
     * derivative of number.
     * @param variable variable for derivative.
     * @return always zero.
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * just number`s value.
     * @param assignments assignation string.
     * @return value.
     */
    @Override
    public double eval(String assignments) {
        return value;
    }
}

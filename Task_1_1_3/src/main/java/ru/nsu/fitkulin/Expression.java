package ru.nsu.fitkulin;

/**
 * abstract class Expression.
 */
public abstract class Expression {
    /**
     * print expression.
     */
    public void print() {
        System.out.println(this.toString());
    }

    /**
     * get derivative of expression.
     *
     * @param variable variable for derivative.
     *
     * @return a new expression after taking the derivative.
     */
    public abstract Expression derivative(String variable);

    /**
     * evaluating an expression with assigned variables.
     *
     * @param assignments assignation string.
     *
     * @return result of expression.
     */
    public abstract double eval(String assignments);

    /**
     * get new expression which is simplification of original expression.
     *
     * @return new expression.
     */
    public abstract Expression simple();
}

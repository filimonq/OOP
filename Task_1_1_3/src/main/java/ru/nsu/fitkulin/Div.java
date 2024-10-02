package ru.nsu.fitkulin;

public class Div extends Expression {
    Expression left;
    Expression right;

    Div (Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

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

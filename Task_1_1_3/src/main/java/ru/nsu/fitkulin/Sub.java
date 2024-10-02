package ru.nsu.fitkulin;

public class Sub extends Expression {
    Expression left;
    Expression right;

    Sub (Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public void print() {

    }

    @Override
    public Expression derivative(String variable) {
        return new Sub (left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(String assignments) {
        return this.left.eval(assignments) - this.right.eval(assignments);
    }
}


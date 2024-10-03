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
    public String toString() {
        return "(" + this.left.toString() + "-" + this.right.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub (left.derivative(variable), right.derivative(variable));
    }

    @Override
    public double eval(String assignments) {
        return this.left.eval(assignments) - this.right.eval(assignments);
    }
}


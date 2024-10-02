package ru.nsu.fitkulin;

public class Add extends Expression {
    Expression left;
    Expression right;

    Add (Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public void print() {

    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(String assignments) {
        return this.left.eval(assignments) + this.right.eval(assignments);
    }
}

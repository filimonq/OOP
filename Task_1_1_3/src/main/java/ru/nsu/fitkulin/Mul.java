package ru.nsu.fitkulin;

public class Mul extends Expression {
    Expression left;
    Expression right;

    Mul (Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public void print() {

    }

    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left, right.derivative(variable)),
                       new Mul(left.derivative(variable), right));
    }

    @Override
    public int eval(String assignments) {
        return left.eval(assignments) * right.eval(assignments);
    }
}

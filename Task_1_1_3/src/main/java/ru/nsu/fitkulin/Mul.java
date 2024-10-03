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
    public String toString() {
        return "(" + this.left.toString() + "*" + this.right.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left, right.derivative(variable)),
                       new Mul(left.derivative(variable), right));
    }

    @Override
    public double eval(String assignments) {
        return left.eval(assignments) * right.eval(assignments);
    }
}

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
    public String toString() {
        return "(" + this.left.toString() + "/" + this.right.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Div(new Sub(new Mul(this.right, this.left.derivative(variable)),
                                new Mul(this.left, this.right.derivative(variable))),
                    new Mul(this.right, this.right));
    }

    @Override
    public double eval(String assignments) {
        return left.eval(assignments) / right.eval(assignments);
    }
}

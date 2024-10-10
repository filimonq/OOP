package ru.nsu.fitkulin;

/**
 * class for division.
 */
public class Div extends Expression {
    Expression left;
    Expression right;

    /**
     * constructor.
     *
     * @param left  left expression.
     * @param right right expression.
     */
    Div(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    /**
     * string fot division.
     *
     * @return (a / b)
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + "/" + this.right.toString() + ")";
    }

    /**
     * derivative for division.
     *
     * @param variable variable for derivative.
     * @return derivative of division two expressions.
     */
    @Override
    public Expression derivative(String variable) {
        return new Div(new Sub(new Mul(this.right, this.left.derivative(variable)),
                new Mul(this.left, this.right.derivative(variable))),
                new Mul(this.right, this.right));
    }

    /**
     * evaluating an expression with assigned variables.
     *
     * @param assignments assignation string.
     * @return res of expression.
     */
    @Override
    public double eval(String assignments) {
        return left.eval(assignments) / right.eval(assignments);
    }

    @Override
    public Expression simple() {
        Div simplifiedDiv = new Div(this.left.simple(), this.right.simple());
        if (simplifiedDiv.left instanceof Number leftNumber
                && simplifiedDiv.right instanceof Number rightNumber) {
            return new Number(leftNumber.value / rightNumber.value);
        } else if (this.left instanceof Number leftNumber && leftNumber.value == 0) {
            return new Number(0);
        } else {
            return simplifiedDiv;
        }
    }
}


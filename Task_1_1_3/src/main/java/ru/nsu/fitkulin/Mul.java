package ru.nsu.fitkulin;

/**
 * class for multiplication.
 */
public class Mul extends Expression {
    Expression left;
    Expression right;

    /**
     * constructor.
     *
     * @param left left expression.
     *
     * @param right right expression.
     */
    Mul(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    /**
     * string for multiplication.
     *
     * @return (a*b).
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + "*" + this.right.toString() + ")";
    }

    /**
     * derivative for multiplication.
     *
     * @return derivative of mul two expressions.
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left, right.derivative(variable)),
                       new Mul(left.derivative(variable), right));
    }

    /**
     * evaluating an expression with assigned variables.
     *
     * @param assignments assignation string.
     *
     * @return res of expression.
     */
    @Override
    public double eval(String assignments) {
        return left.eval(assignments) * right.eval(assignments);
    }

    /**
     * @return simple exp for Mul.
     */
    @Override
    public Expression simple() {
        Mul simplifiedMul = new Mul(this.left.simple(), this.right.simple());
        if (simplifiedMul.left instanceof Number leftNumber
                && simplifiedMul.right instanceof Number rightNumber) {
            return new Number(leftNumber.value * rightNumber.value);

        } else if (simplifiedMul.left instanceof Number leftNumber && leftNumber.value == 0
                || simplifiedMul.right instanceof  Number rightNumber && rightNumber.value == 0) {
            return new Number(0);

        } else if (simplifiedMul.left instanceof Number leftNumber && leftNumber.value == 1) {
            return simplifiedMul.right;

        } else if (simplifiedMul.right instanceof Number rightNumber && rightNumber.value == 1) {
            return simplifiedMul.left;

        } else {
            return simplifiedMul;
        }
    }
}


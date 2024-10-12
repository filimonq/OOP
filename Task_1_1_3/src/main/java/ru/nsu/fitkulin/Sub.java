package ru.nsu.fitkulin;

/**
 * class for sub.
 */
public class Sub extends Expression {
    Expression left;
    Expression right;

    /**
     * constructor.
     *
     * @param left left expression.
     *
     * @param right right expression.
     */
    Sub(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    /**
     * string of sub.
     *
     * @return (a-b).
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + "-" + this.right.toString() + ")";
    }

    /**
     * derivative of sub.
     *
     * @param variable – variable for derivative.
     *
     * @return derivative of sub. two expressions.
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
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
        return this.left.eval(assignments) - this.right.eval(assignments);
    }

    /**
     * @return simple exp for Sub.
     */
    @Override
    public Expression simple() {
        Sub simplifiedSub = new Sub(this.left.simple(), this.right.simple());
        if (simplifiedSub.left instanceof Number leftNumber
                && simplifiedSub.right instanceof Number rightNumber) {
            return new Number(leftNumber.value - rightNumber.value);

        }  else if (simplifiedSub.left instanceof Number leftNumber && leftNumber.value == 0) {
            return simplifiedSub.right;

        } else if (simplifiedSub.right instanceof Number rightNumber && rightNumber.value == 0) {
            return simplifiedSub.left;

        } else {
            return simplifiedSub;
        }
    }
}



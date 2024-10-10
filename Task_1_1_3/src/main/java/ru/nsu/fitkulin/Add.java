package ru.nsu.fitkulin;

/**
 * class for sum.
 */
public class Add extends Expression {
    Expression left;
    Expression right;

    /**
     * constructor.
     *
     * @param left left expression
     *
     * @param right right expression.
     */
    Add(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    /**
     * string of sum.
     *
     * @return (a+b).
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + "+" + this.right.toString() + ")";
    }

    /**
     * derivative of sum.
     *
     * @param variable variable for derivative.
     *
     * @return derivative of sum two expressions.
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
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
        return this.left.eval(assignments) + this.right.eval(assignments);
    }

    /**
     * @return simple exp for Add.
     */
    @Override
    public Expression simple() {
        Add simplifiedAdd = new Add(this.left.simple(), this.right.simple());
        if (simplifiedAdd.left instanceof Number leftNumber
                && simplifiedAdd.right instanceof Number rightNumber) {
            return new Number(leftNumber.value + rightNumber.value);

        } else if (simplifiedAdd.left instanceof Number leftNumber && leftNumber.value == 0) {
            return simplifiedAdd.right;

        } else if (simplifiedAdd.right instanceof Number rightNumber && rightNumber.value == 0) {
            return simplifiedAdd.left;
            
        } else {
            return simplifiedAdd;
        }
    }
}

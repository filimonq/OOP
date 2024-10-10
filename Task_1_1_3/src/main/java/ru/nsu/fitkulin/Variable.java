package ru.nsu.fitkulin;

/**
 * class responsible for the variable.
 */
public class Variable extends Expression {

    String name;

    /**
     * constructor.
     */
    public Variable(String name) {
        super();
        this.name = name;
    }

    /**
     * string fot variable.
     *
     * @return variable.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * derivative of variable.
     *
     * @param variable variable for derivative.
     *
     * @return 1, if we take the derivative with respect to this variable.
     */
    public Expression derivative(String variable) {
        if (variable.equals(this.name)) {
            return new Number(1); // производная переменной по самой себе равна 1
        } else {
            return new Number(0); // производная переменной по другой переменной или константе
        }
    }

    /**
     * processes the input of a variable value.
     *
     * @param assignments assignation string.
     *
     * @return value of variable.
     */
    @Override
    public double eval(String assignments) {
        String[] assignmentsArray = assignments.split(";");

        for (String assignment : assignmentsArray) {
            String[] assignmentParts = assignment.split("=");
            String variableName = assignmentParts[0].trim();
            String variableValue = assignmentParts[1].trim();
            if (this.name.equals(variableName)) {
                return Float.parseFloat(variableValue);
            }
        }
        return 0;
    }
}

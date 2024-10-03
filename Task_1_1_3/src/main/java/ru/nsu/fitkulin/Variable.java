package ru.nsu.fitkulin;

public class Variable extends Expression {

    String name;
    public Variable(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Expression derivative(String variable) {
        if (variable.equals(this.name)) {
            return new Number(1); // производная переменной по самой себе равна 1
        } else {
            return new Number(0); // производная переменной по другой переменной или константе
        }
    }



    @Override
    public double eval(String assignments) {
        String[] assignmentsArray = assignments.split(";");

        for (String assignment : assignmentsArray) {
            String[] assignmentParts = assignment.split("=");
            String variableName = assignmentParts[0].trim();
            String variableValue = assignmentParts[1].trim();

            if (this.name.equals(variableName)) {
                    return Integer.parseInt(variableValue);
            }
        }
        return 0;
    }
}

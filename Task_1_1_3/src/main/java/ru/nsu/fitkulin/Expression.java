package ru.nsu.fitkulin;

public abstract class Expression {

    public void print() {
        System.out.println(this.toString());
    }

    public abstract Expression derivative(String variable);

    public abstract double eval(String assignments);

    // public abstract Expression dop();
}

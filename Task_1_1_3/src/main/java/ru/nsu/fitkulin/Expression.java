package ru.nsu.fitkulin;

public abstract class Expression {

    public void print() {
        System.out.println(this);
    }

    public abstract Expression derivative(String variable);

    public abstract int eval(String assignments);

    // public abstract Expression aaaaa();
}

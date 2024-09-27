package ru.nsu.fitkulin;

public abstract class Expression {
    public abstract void print();

    public abstract Expression derivative(String variable);

    public abstract int eval(String assignments);

    public abstract Expression aaaaa();
}

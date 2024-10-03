package ru.nsu.fitkulin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(4), new Div(new Number(2), new Variable("x")));

        System.out.print("Input: ");
        e.print();

        Expression de = e.derivative("x");
        System.out.print("Derivative: ");
        de.print();

        double result = e.eval("x = 10; y = 13");
        System.out.println("Evaluation Result: " + result);
    }
}

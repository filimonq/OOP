package ru.nsu.fitkulin;

import java.util.Scanner;

/**
 * main class.
 */
public class Main {
    /**
     * main func.
     */
    public static void main(String[] args) {
        System.out.println("Введите выражение");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();

        System.out.println("Введите переменные в формате 'x = 10; y = 5'");
        String variableValue = scanner.nextLine();

        System.out.println("Введите переменную, по которой нужно взять производную");
        String variableDerivative = scanner.nextLine();

        Expression e = Parser.parse(expression);

        System.out.print("Выражение: ");
        e.print();

        Expression de = e.derivative(variableDerivative);
        System.out.print("Производная: ");
        de.print();

        try {
            double result = e.eval(variableValue);
            System.out.println("Результат: " + result);
        } catch (ArithmeticException ex) {
            System.out.println("--- " + ex.getMessage() + " ---");
        }

        try {
            Expression se = e.simple();
            System.out.print("Упрощённое: ");
            se.print();
        } catch (ArithmeticException ex) {
            System.out.println("--- " + ex.getMessage() + " ---");
        }

        scanner.close();
    }
}

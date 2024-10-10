package ru.nsu.fitkulin;

import java.util.Scanner;

public class Main {
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

        double result = e.eval(variableValue);
        System.out.println("Результат: " + result);
    }
}

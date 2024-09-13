package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Блэкджек!");
        Cards cards = new Cards();
        ArrayList<Card> deck;
        deck = cards.getDeck();
        System.out.println(deck.toString());
    }
}
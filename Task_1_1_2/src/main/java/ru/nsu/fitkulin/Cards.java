package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.Collections;

public class Cards {
    ArrayList<Card> deck;

    public Cards() {
        deck = new ArrayList<>();
        String[] suits = {"Червы", "Трефы", "Бубны", "Пики"};
        String[] names = {"Двойка", "Тройка", "Четверка", "Пятерка", "Шестерка", "Семерка",
                "Восьмерка", "Девятка", "Десятка", "Валет", "Дама", "Король" ,"Туз"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < 13; i++) {
                deck.add(new Card(values[i], names[i], suit));
            }
        }
        Collections.shuffle(deck);
    }
    public ArrayList<Card> getDeck() {
        return deck;
    }
}

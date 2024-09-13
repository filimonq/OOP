package ru.nsu.fitkulin;

/**
 * enum for information about card.
 */
public enum CardValue {
    TWO("Двойка", 2),
    THREE("Тройка", 3),
    FOUR("Четверка", 4),
    FIVE("Пятерка", 5),
    SIX("Шестерка", 6),
    SEVEN("Семерка", 7),
    EIGHT("Восьмерка", 8),
    NINE("Девятка", 9),
    TEN("Десятка", 10),
    JACK("Валет", 10),
    QUEEN("Дама", 10),
    KING("Король", 10),
    ACE("Туз", 11);

    final int valueOfCard;
    final String nameOfCard;

    CardValue (String nameOfCard, int valueOfCard) {
        this.nameOfCard = nameOfCard;
        this.valueOfCard = valueOfCard;
    }
}
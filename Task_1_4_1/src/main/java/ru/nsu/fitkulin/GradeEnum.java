package ru.nsu.fitkulin;

/**
 * enumerate of grades.
 */
public enum GradeEnum {
    EXCELLENT(5), GOOD(4), SATISFACTORY(3), PASS(2);

    private final int value;

    GradeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
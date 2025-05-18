package ru.nsu.fitkulin;

public class Task {
    private final int id;
    private final long[] numbers;
    private boolean completed;
    private boolean hasNonPrime;

    public Task(int id, long[] numbers) {
        this.id = id;
        this.numbers = numbers;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public long[] getNumbers() {
        return numbers;
    }

    public void setResult(boolean hasNonPrime) {
        this.hasNonPrime = hasNonPrime;
        this.completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean getHasNonPrime() {
        return hasNonPrime;
    }
}
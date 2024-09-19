package ru.nsu.fitkulin;

import java.util.Scanner;

/**
 * main implementation of the game.
 */
public class Game {
    static Player playerHand;
    static Dealer dealerHand;
    static Cards deck;
    static Scanner scanner;
    int input = scanner.nextInt();
    static int roundValue = 0;
    static int playerScore = 0;
    static int dealerScore = 0;

    /**
     * start of the round.
     */
    public static void round() {
        scanner = new Scanner(System.in);
        deck = new Cards();
        playerHand = new Player(deck.getCard(), deck.getCard());
        dealerHand = new Dealer(deck.getCard(), deck.getCard());
        roundValue++;
        System.out.println("\n === Раунд " + roundValue + " ===");
        System.out.println("Дилер раздал карты");
        System.out.println(playerHand.toString() + " => " + playerHand.sumCards());
        System.out.println(dealerHand.toString());
        if (!playersTurn()) {
            dealersTurn();
        }
    }

    /**
     * end of the round.
     * @param flag - true if no one is over 21.
     */
    static boolean finish(boolean flag) {
        int playerSum = playerHand.sumCards();
        int dealerSum = dealerHand.sumCards();

        if ((flag && playerSum > dealerSum && playerSum <= 21) || dealerSum > 21) {
            playerScore++;
            System.out.println("Вы выиграли раунд! Счет " + playerScore + ":" + dealerScore);
            return true;
        }
        if (playerSum > 21 || (flag && playerSum < dealerSum)
                || (dealerHand.handChanged && dealerScore == 21)) {
            dealerScore++;
            System.out.println("Раунд выиграл дилер! Счет " + playerScore + ":" + dealerScore);
            return true;
        }
        if (flag) {
            System.out.println("Ничья! Счет " + playerScore + ":" + dealerScore);
        }
        return false;
    }

    /**
     * player`s turn while scanner == 1.
     */
    static boolean playersTurn() {
        if (blackJack()) {
            return true;
        }
        System.out.println("Ваш ход");
        System.out.println("-------");
        System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
        int input = scanner.nextInt();
        while (input == 1) {
            Card nextCard = deck.getCard();
            playerHand.addCard(nextCard);
            System.out.println("Вы открыли карту " + nextCard.toString());
            System.out.println("Ваши карты" + playerHand.toString()
                    + " => " + playerHand.sumCards());
            System.out.println("Карты дилера" + dealerHand.toString() + "\n");

            if (playerHand.sumCards() > 21) {
                return finish(false);
            }
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
            input = scanner.nextInt();
        }
        return finish(false);
    }

    /**
     * dealer`s turn while sum < 17.
     */
    static void dealersTurn() {
        dealerHand.handChanged = true;
        System.out.println("Дилер открывает закрытую карту ");
        System.out.println(playerHand.toString() + " => " + playerHand.sumCards());
        System.out.println(dealerHand.toString() + " => " + dealerHand.sumCards() + "\n");

        if (blackJack()) {
            return;
        }
        while (dealerHand.sumCards() < 17) {
            Card nextCard = deck.getCard();
            dealerHand.addCard(nextCard);
            System.out.println("Дилер открывает карту " + nextCard.toString());
            System.out.println("Ваши карты" + playerHand.toString()
                    + " => " + playerHand.sumCards());
            System.out.println("Карты дилера" + dealerHand.toString()
                    + " => " + dealerHand.sumCards() + "\n");
        }
        finish(true);
    }

    /**
     * check for sum equal 21 points.
     * @return blackjack or not.
     */
    static boolean blackJack() {
        if (!dealerHand.handChanged) {
            if (playerHand.sumCards() == 21) {
                playerScore++;
                System.out.println("Вы выиграли раунд! Счет " + playerScore + ":" + dealerScore);
                return true;
            }
        } else {
            if (dealerHand.sumCards() == 21) {
                dealerScore++;
                System.out.println("Раунд выиграл дилер! Счет " + playerScore + ":" + dealerScore);
                return true;
            }
        }
        return false;
    }

}

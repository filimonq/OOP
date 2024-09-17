package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    Player player;
    Dealer dealer;
    ArrayList<Card> deck;

    public Game() {
        this.player = new Player();
        this.dealer = new Dealer();
        Cards cards = new Cards();
        this.deck = cards.getDeck();
    }



    public void printHands() {
        System.out.println("    Ваши карты: " + player.getHand() + " > " + "sum");
        System.out.println("    Карты дилера: " + dealer.getHand() + " > " + "sum");
    }

}

package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AppSaloon {
    private List<Player> players;

    public AppSaloon() {
        players = new ArrayList<>();
        players.add(new Player("Игрок 1"));
        players.add(new Player("Бот 1"));
        players.add(new Player("Бот 2"));
        players.add(new Player("Бот 3"));

        initializePlayersDice();
    }

    private void initializePlayersDice() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                Dice dice = new Dice();
                player.addDice(dice);
            }
        }
    }

    public void startGame() {
        displayPlayersDice();
        doBet();
    }

    private void round(){

    }

    public void liar(){

    }

    private void doBet() {
        Scanner src = new Scanner(System.in);
        Bet lastBet = null;
        int diceCount = players.size() * 5;

        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(i);
            boolean validBet = false;

            if (currentPlayer.getName().startsWith("Бот")) {
                while (!validBet) {
                    int quantity = new Random().nextInt(diceCount) + 1;
                    int faceValue = new Random().nextInt(6) + 1;

                    if (quantity > diceCount) {
                        continue;
                    } else if (lastBet == null || quantity > lastBet.getQuantity() || faceValue > lastBet.getFaceValue()) {
                        lastBet = new Bet(currentPlayer, quantity, faceValue);
                        System.out.println(lastBet);
                        validBet = true;
                    }
                }
            } else {
                while (!validBet) {
                    System.out.println(currentPlayer.getName() + ", введите количество кубиков и значение (например, '2 3'):");
                    int quantity = src.nextInt();
                    int faceValue = src.nextInt();

                    if (quantity > diceCount) {
                        System.out.println("Нельзя поставить больше " + diceCount + " кубиков!");
                    } else if (faceValue > 6) {
                        System.out.println("Нельзя поставить значение кубиков больше 6!");
                    } else if (lastBet != null && (quantity < lastBet.getQuantity() || faceValue < lastBet.getFaceValue())) {
                        System.out.println("Ставка должна быть больше предыдущей ставки!");
                    } else {
                        lastBet = new Bet(currentPlayer, quantity, faceValue);
                        System.out.println(lastBet);
                        validBet = true;
                    }
                }
            }
        }

        src.close();
    }

    private void displayPlayersDice() {
        for (Player player : players) {
            if (!player.getName().startsWith("Бот")) {
                System.out.print(player.getName() + " бросает кубики: ");
                List<Dice> diceList = player.getDiceList();
                for (Dice dice : diceList) {
                    System.out.print(dice.getValue() + " ");
                }
                System.out.println();
            }
        }
        System.out.println("Боты бросили кубики.");
    }
}

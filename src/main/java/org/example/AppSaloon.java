package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AppSaloon {
    private List<Player> players;
    private CheckBet checkBet = new CheckBet();
    private Bet lastBet = null;
    private int maxDiceCount;

    public AppSaloon() {
        players = new ArrayList<>();
        players.add(new Player("Игрок 1"));
        players.add(new BotPlayer("Бот 1"));
        players.add(new BotPlayer("Бот 2"));
        players.add(new BotPlayer("Бот 3"));

        initializePlayersDice();
        maxDiceCount = players.size() * 5;
    }

    private void initializePlayersDice() {
        for (Player player : players) {
            for (var i = 0; i < 5; i++) {
                Dice dice = new Dice();
                player.addDice(dice);
            }
        }
    }

    public void startGame() {
        while (players.size() > 1) {
            displayPlayersDice();
            doBet();
        }
    }

    private void doBet() {
        Scanner src = new Scanner(System.in);
        var diceCount = players.size() * 5;

        for (Player currentPlayer : players) {
            if (currentPlayer instanceof BotPlayer) {
                placeBotBet(currentPlayer, diceCount);
            } else {
                placeUserBet(src, currentPlayer, diceCount);
            }

            System.out.print("Введите 'liar' для проверки ставки или 'ставка' для продолжения: ");
            String action = src.next().toLowerCase();
            if (action.equals("liar")) {
                checkBet.handleLiar(currentPlayer, lastBet.getPlayer(), lastBet, players);
                return;
            }
        }
    }

    private void placeBotBet(Player currentPlayer, int diceCount) {
        Random random = new Random();
        int quantity = random.nextInt(diceCount) + 1;
        int faceValue = random.nextInt(6) + 1;
        lastBet = new Bet(currentPlayer, quantity, faceValue);
        System.out.println(currentPlayer.getName() + " ставит " + quantity + " кубика(-ов) со значением " + faceValue);
    }

    private void placeUserBet(Scanner src, Player currentPlayer, int diceCount) {
        while (true) {
            System.out.println(currentPlayer.getName() + ", введите количество кубиков и значение (например, '2 3'):");
            int quantity = src.nextInt();
            int faceValue = src.nextInt();
            if (quantity <= diceCount && faceValue <= 6) {
                lastBet = new Bet(currentPlayer, quantity, faceValue);
                System.out.println(currentPlayer.getName() + " ставит " + quantity + " кубика(-ов) со значением " + faceValue);
                break;
            }
            System.out.println("Неверная ставка. Попробуйте снова.");
        }
    }

    private void displayPlayersDice() {
        for (Player player : players) {
            System.out.print(player.getName() + " бросает кубики: ");
            for (Dice dice : player.getDiceList()) {
                System.out.print(dice.getValue() + " ");
            }
            System.out.println();
        }
    }
}

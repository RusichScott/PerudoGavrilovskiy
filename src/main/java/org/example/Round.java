package org.example;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Round {
    private List<Player> players;
    private int currentPlayerIndex;
    private Scanner scanner;
    private CheckBet checkBet;

    public Round(List<Player> players, int currentPlayerIndex, Scanner scanner) {
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.scanner = scanner;
        this.checkBet = new CheckBet();
    }

    public int playRound(boolean isMaputa) {
        Bet lastBet = null;
        boolean roundContinues = true;

        while (roundContinues) {
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("\nХодит: " + currentPlayer.getName() + " (кубиков осталось: " + currentPlayer.getDiceList().size() + ")");

            if (currentPlayer instanceof BotPlayer) {
                handleBotTurn((BotPlayer) currentPlayer, lastBet, isMaputa);
            } else {
                handleUserTurn(currentPlayer, lastBet, isMaputa);
            }

            if (roundContinues) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            }
        }
        return currentPlayerIndex;
    }

    private void handleBotTurn(BotPlayer bot, Bet lastBet, boolean isMaputa) {
        BotBetStrategy botBetStrategy = new BotBetStrategy();
        if (new Random().nextInt(2) == 0 && lastBet != null) { // 50% шанс "liar"
            System.out.println(bot.getName() + " говорит: Liar!");
            checkBet.handleLiar(bot, lastBet.getPlayer(), lastBet, players);
        } else {
            lastBet = botBetStrategy.placeBet(bot, lastBet, getTotalDiceCount(), isMaputa);
            System.out.println("Ставка: " + lastBet);
        }
    }

    private void handleUserTurn(Player user, Bet lastBet, boolean isMaputa) {
        UserBetStrategy userBetStrategy = new UserBetStrategy(scanner);
        if (lastBet != null) {
            System.out.println("Введите 'liar' для проверки ставки или 'ставка' для продолжения:");
            String action = scanner.next().toLowerCase();
            if (action.equals("liar")) {
                System.out.println(user.getName() + " говорит: Liar!");
                checkBet.handleLiar(user, lastBet.getPlayer(), lastBet, players);
            } else {
                lastBet = userBetStrategy.placeBet(user, lastBet, getTotalDiceCount(), isMaputa);
                System.out.println("Ставка: " + lastBet);
            }
        } else {
            lastBet = userBetStrategy.placeBet(user, lastBet, getTotalDiceCount(), isMaputa);
            System.out.println("Ставка: " + lastBet);
        }
    }

    private int getTotalDiceCount() {
        return players.stream().mapToInt(player -> player.getDiceList().size()).sum();
    }
}

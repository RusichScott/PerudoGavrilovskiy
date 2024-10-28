package org.example;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Round {
    private List<Player> players;
    private int currentPlayerIndex;
    private Bet lastBet;
    private CheckBet checkBet;
    private Scanner scanner;

    public Round(List<Player> players, int currentPlayerIndex, Scanner scanner) {
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.scanner = scanner;
        this.checkBet = new CheckBet();
    }

    public int playRound(boolean isMaputa) {
        lastBet = null;
        boolean liarCalled = false;
        int initialPlayerIndex = currentPlayerIndex;

        while (!liarCalled) {
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("\nХодит: " + currentPlayer.getName() + " (кубиков осталось: " + currentPlayer.getDiceList().size() + ")");

            if (currentPlayer instanceof BotPlayer) {
                liarCalled = handleBotTurn((BotPlayer) currentPlayer, isMaputa);
            } else {
                liarCalled = handlePlayerTurn((UserPlayer) currentPlayer, initialPlayerIndex != currentPlayerIndex, isMaputa);
            }

            if (!liarCalled) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            }

            if (!liarCalled && currentPlayerIndex == initialPlayerIndex) {
                System.out.println("Автоматический вызов 'Liar' последним игроком!");
                checkBet.handleLiar(players.get(currentPlayerIndex), lastBet.getPlayer(), lastBet, players);
                liarCalled = true;
            }
        }
        return (initialPlayerIndex + 1) % players.size();
    }

    private boolean handleBotTurn(BotPlayer bot, boolean isMaputa) {
        BotBetStrategy botBetStrategy = new BotBetStrategy();

        if (lastBet != null && new Random().nextInt(2) == 0) {
            System.out.println(bot.getName() + " говорит: Liar!");
            checkBet.handleLiar(bot, lastBet.getPlayer(), lastBet, players);
            return true;
        } else {
            Bet newBet = botBetStrategy.placeBet(bot, lastBet, getTotalDiceCount(), isMaputa);

            lastBet = newBet;
            System.out.println(bot.getName() + " делает ставку: " + lastBet);

            return false;
        }
    }

    private boolean handlePlayerTurn(UserPlayer user, boolean canCallLiar, boolean isMaputa) {
        UserBetStrategy userBetStrategy = new UserBetStrategy(scanner);

        if (!canCallLiar) {
            System.out.println(user.getName() + " делает первую ставку:");
            lastBet = userBetStrategy.placeBet(user, lastBet, getTotalDiceCount(), isMaputa);
            System.out.println("Ставка: " + lastBet);
            return false;
        }

        while (true) {
            System.out.println("Введите 'liar' для проверки ставки или 'ставка' для продолжения:");
            String action = scanner.next().toLowerCase();

            if (action.equals("liar") && lastBet != null) {
                System.out.println(user.getName() + " говорит: Liar!");
                checkBet.handleLiar(user, lastBet.getPlayer(), lastBet, players);
                return true;
            } else if (action.equals("ставка")) {
                lastBet = userBetStrategy.placeBet(user, lastBet, getTotalDiceCount(), isMaputa);
                System.out.println("Ставка: " + lastBet);
                return false;
            } else {
                System.out.println("Некорректный ввод. Пожалуйста, введите 'liar' или 'ставка'.");
            }
        }
    }

    private int getTotalDiceCount() {
        return players.stream().mapToInt(player -> player.getDiceList().size()).sum();
    }
}
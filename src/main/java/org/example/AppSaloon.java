package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppSaloon {
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean maputaAnnounced = false;
    private Scanner scanner;

    public AppSaloon() {
        players = new ArrayList<>();
        players.add(new UserPlayer("Игрок 1"));
        players.add(new BotPlayer("Бот 1"));
        players.add(new BotPlayer("Бот 2"));
        players.add(new BotPlayer("Бот 3"));
        currentPlayerIndex = 0;
        scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.println("Игра Perudo началась!");

        while (players.size() > 1) {
            displayPlayersDice();

            if (shouldStartMaputaRound()) {
                System.out.println("Maputa объявлена!");
                MaputaRound maputaRound = new MaputaRound(players, currentPlayerIndex, scanner);
                currentPlayerIndex = maputaRound.playMaputaRound();
                maputaAnnounced = true;
            } else {
                Round round = new Round(players, currentPlayerIndex, scanner);
                currentPlayerIndex = round.playRound(false);
            }
            removePlayersWithoutDice();
        }
        announceWinner();
    }

    private void displayPlayersDice() {
        players.forEach(player -> {
            String diceInfo = (player instanceof BotPlayer) ? "(Осталось кубиков: " + player.getDiceList().size() + ")" :
                    player.getDiceList().stream().map(dice -> String.valueOf(dice.getValue())).reduce((d1, d2) -> d1 + " " + d2).orElse("");
            System.out.println(player.getName() + " бросает кубики: " + diceInfo);
        });
    }

    private boolean shouldStartMaputaRound() {
        return players.stream().anyMatch(player -> player.getDiceList().size() == 1 && !maputaAnnounced);
    }

    private void removePlayersWithoutDice() {
        players.removeIf(player -> !player.hasDice());
    }

    private void announceWinner() {
        if (players.size() == 1) {
            System.out.println("Победитель: " + players.get(0).getName() + "! Поздравляем!");
        } else {
            System.out.println("Игра завершена. Победитель не выявлен.");
        }
    }
}
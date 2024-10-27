package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppSaloon {
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean maputaAnnounced = false;
    private Scanner scanner = new Scanner(System.in);

    public AppSaloon() {
        players = new ArrayList<>();
        players.add(new UserPlayer("Игрок 1"));
        players.add(new BotPlayer("Бот 1"));
        players.add(new BotPlayer("Бот 2"));
        players.add(new BotPlayer("Бот 3"));
        currentPlayerIndex = 0;
    }

    public void startGame() {
        System.out.println("Игра Perudo началась!");
        while (players.size() > 2) {
            displayPlayersDice();
            if (shouldStartMaputaRound()) {
                System.out.println("Maputa объявлена!");
                MaputaRound maputaRound = new MaputaRound(players, currentPlayerIndex, scanner);
                currentPlayerIndex = maputaRound.playMaputaRound(); // Используем новый метод
                maputaAnnounced = true;
            } else {
                Round round = new Round(players, currentPlayerIndex, scanner);
                currentPlayerIndex = round.playRound(false); // Используем обычный метод
            }
            removePlayersWithoutDice();
        }
        announceWinner();
    }

    private void displayPlayersDice() {
        players.forEach(player -> {
            System.out.print(player.getName() + " бросает кубики: ");
            player.getDiceList().forEach(dice -> System.out.print(dice.getValue() + " "));
            System.out.println("(Осталось кубиков: " + player.getDiceList().size() + ")");
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
            System.out.println("Игра завершена с двумя оставшимися игроками.");
            System.out.println("Победители: " + players.get(0).getName() + " и " + players.get(1).getName());
        }
    }
}
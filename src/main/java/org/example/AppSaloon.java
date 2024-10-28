package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AppSaloon {
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean maputaAnnounced = false;
    private Scanner scanner = new Scanner(System.in);
    private static final String[] NAME_BASE = {"Lion", "Wolf", "Eagle", "Tiger", "Shark", "Bear"};

    public AppSaloon() {
        players = new ArrayList<>();
        currentPlayerIndex = 0;
    }

    public void displayMenu() {
        System.out.println("********************************");
        System.out.println("**      Добро пожаловать      **");
        System.out.println("**            в               **");
        System.out.println("**         ПЕРУДО!            **");
        System.out.println("********************************");

        while (true) {
            System.out.println("\n1. Начать игру");
            System.out.println("2. Правила игры");
            System.out.println("3. Выйти");
            System.out.print("Выберите опцию: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    setupGame();
                    startGame();
                    return;
                case "2":
                    displayRules();
                    break;
                case "3":
                    System.out.println("Выход из игры. До встречи!");
                    return;
                default:
                    System.out.println("Некорректный выбор. Пожалуйста, выберите 1, 2 или 3.");
                    break;
            }
        }
    }

    private void setupGame() {
        System.out.println("\nВведите имя игрока или нажмите 'Enter' для автоматической генерации имени:");
        String userName = scanner.nextLine();

        if (userName.isEmpty()) {
            userName = generateRandomName();
            System.out.println("Ваше имя: " + userName);
        }
        players.add(new UserPlayer(userName));

        for (int i = 1; i <= 3; i++) {
            String botName = generateRandomName();
            players.add(new BotPlayer(botName));
        }
    }

    private void displayRules() {
        try {
            String rules = new String(Files.readAllBytes(Paths.get("rules.txt")));
            System.out.println("\nПравила игры:\n" + rules);
        } catch (IOException e) {
            System.out.println("Не удалось загрузить правила. Убедитесь, что файл rules.txt находится в папке с игрой.");
        }
        System.out.println("*******************************");
    }

    private String generateRandomName() {
        Random random = new Random();
        String name = NAME_BASE[random.nextInt(NAME_BASE.length)];
        int number = random.nextInt(100_000);
        return name + number;
    }

    public void startGame() {
        System.out.println("\n**************************************");
        System.out.println("**       Игра Perudo началась!      **");
        System.out.println("**************************************");

        while (players.size() > 1) {
            rollAllDice();
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

            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
        }
        announceWinner();
    }

    private void rollAllDice() {
        players.forEach(player -> player.getDiceList().forEach(Dice::roll));
    }

    private void displayPlayersDice() {
        players.forEach(player -> {
            if (player instanceof BotPlayer) {
                System.out.println(player.getName() + " бросает кубики. (Осталось кубиков: " + player.getDiceList().size() + ")");
            } else {
                System.out.print(player.getName() + " бросает кубики: ");
                player.getDiceList().forEach(dice -> System.out.print(dice.getValue() + " "));
                System.out.println("(Осталось кубиков: " + player.getDiceList().size() + ")");
            }
        });
    }

    private boolean shouldStartMaputaRound() {
        return players.stream().anyMatch(player -> player.getDiceList().size() == 1 && !maputaAnnounced);
    }

    private void removePlayersWithoutDice() {
        players.removeIf(player -> !player.hasDice());
    }

    private void announceWinner() {
        System.out.println("\n*******************************");
        if (players.size() == 1) {
            System.out.println("**   Победитель: " + players.get(0).getName() + "!   **");
        } else {
            System.out.println("**   Игра окончена без победителя   **");
        }
        System.out.println("*******************************");
    }
}
package org.example;

import java.util.List;

public class CheckBet {
    public boolean checkBet(Bet bet, List<Player> players) {
        int total = players.stream()
                .flatMap(player -> player.getDiceList().stream())
                .mapToInt(Dice::getValue)
                .filter(value -> value == bet.getFaceValue())
                .sum();
        return total >= bet.getQuantity();
    }

    public void handleLiar(Player liarCaller, Player lastBetter, Bet lastBet, List<Player> players) {
        System.out.println("\nКубики всех игроков при проверке:");
        displayAllPlayersDice(players);

        RemoveDice removeDice = new RemoveDice();
        if (checkBet(lastBet, players)) {
            System.out.println("Ставка верна! " + liarCaller.getName() + " теряет один кубик.");
            removeDice.removeOneDie(liarCaller);
        } else {
            System.out.println("Ставка неверна! " + lastBetter.getName() + " теряет один кубик.");
            removeDice.removeOneDie(lastBetter);
        }
    }

    private void displayAllPlayersDice(List<Player> players) {
        players.forEach(player -> {
            System.out.print(player.getName() + " кубики: ");
            player.getDiceList().forEach(dice -> System.out.print(dice.getValue() + " "));
            System.out.println();
        });
    }
}
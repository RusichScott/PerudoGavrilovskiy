package org.example;

import java.util.List;

public class CheckBet {
    private RemoveDice removeDice = new RemoveDice();

    public boolean checkBet(Bet bet, List<Player> players) {
        int total = 0;
        for (Player player : players) {
            total += (int) player.getDiceValues().stream().filter(value -> value == bet.getFaceValue()).count();
        }
        return total >= bet.getQuantity();
    }

    public void handleLiar(Player liarCaller, Player lastBetter, Bet lastBet, List<Player> players) {
        if (checkBet(lastBet, players)) {
            System.out.println("Ставка верна! " + liarCaller.getName() + " теряет один кубик.");
            removeDice.removeOneDie(liarCaller);
        } else {
            System.out.println("Ставка неверна! " + lastBetter.getName() + " теряет один кубик.");
            removeDice.removeOneDie(lastBetter);
        }
    }
}
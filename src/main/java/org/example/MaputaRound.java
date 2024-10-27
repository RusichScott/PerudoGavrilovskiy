package org.example;

import java.util.List;
import java.util.Scanner;

public class MaputaRound extends Round {
    public MaputaRound(List<Player> players, int firstPlayerIndex, Scanner scanner) {
        super(players, firstPlayerIndex, scanner);
    }

    public int playMaputaRound() {
        System.out.println("Maputa Round начался! Можно повышать только количество кубиков.");
        return super.playRound(true);
    }
}

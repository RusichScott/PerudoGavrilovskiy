package org.example;

import java.util.List;
import java.util.Random;

public class BotBetStrategy {
    private Random random = new Random();

    public Bet placeBet(Player bot, Bet lastBet, int diceCount, boolean isMaputa) {
        int quantity = (lastBet == null) ? random.nextInt(diceCount) + 1 : lastBet.getQuantity() + 1;
        int faceValue = (lastBet == null) ? random.nextInt(6) + 1 : lastBet.getFaceValue();

        if (isMaputa) {
            quantity = (lastBet == null) ? random.nextInt(diceCount) + 1 : lastBet.getQuantity() + 1;
        } else {
            quantity = random.nextInt(diceCount) + 1;
            faceValue = random.nextInt(6) + 1;
        }

        if (lastBet == null || quantity > lastBet.getQuantity() || (faceValue > lastBet.getFaceValue() && !isMaputa)) {
            return new Bet(bot, quantity, faceValue);
        }
        return null;
    }
}
package org.example;

import java.util.Random;

public class BotBetStrategy {
    public Bet placeBet(Player bot, Bet lastBet, int diceCount, boolean isMaputa) {
        int quantity;
        int faceValue;

        if (lastBet == null) {
            quantity = new Random().nextInt(diceCount) + 1;
            faceValue = new Random().nextInt(6) + 1;
        } else {
            quantity = lastBet.getQuantity() + 1;
            faceValue = isMaputa ? lastBet.getFaceValue() : new Random().nextInt(6) + 1;
        }

        return new Bet(bot, quantity, faceValue);
    }
}
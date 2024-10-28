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

            if (isMaputa) {
                faceValue = lastBet.getFaceValue();
            } else {
                faceValue = lastBet.getFaceValue();
                if (faceValue < 6 && new Random().nextBoolean()) {
                    faceValue++;
                }
            }
        }

        System.out.println(bot.getName() + " делает ставку: " + quantity + " " + faceValue + " (от " + bot.getName() + ")");
        return new Bet(bot, quantity, faceValue);
    }
}
package org.example;

import java.util.HashMap;
import java.util.Map;

import static org.example.Dice.random;

public class BotBetStrategy {
    private static final int MAX_FACE_VALUE = 6; // Максимальный номинал кубика

    public Bet placeBet(Player bot, Bet lastBet, int diceCount, boolean isMaputa) {
        Map<Integer, Integer> diceFrequency = new HashMap<>();

        for (Dice dice : bot.getDiceList()) {
            int value = dice.getValue();
            diceFrequency.put(value, diceFrequency.getOrDefault(value, 0) + 1);
        }

        int bestFaceValue = diceFrequency.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(1);
        int baseQuantity = diceFrequency.getOrDefault(bestFaceValue, 1);

        int quantity = baseQuantity + random.nextInt(2) + 2;
        if (lastBet != null) {
            quantity = Math.max(lastBet.getQuantity() + 1, quantity);
        }

        int faceValue;
        if (lastBet != null) {
            faceValue = Math.min(Math.max(bestFaceValue, lastBet.getFaceValue() + 1), MAX_FACE_VALUE);
        } else {
            faceValue = Math.min(bestFaceValue, MAX_FACE_VALUE);
        }

        return new Bet(bot, quantity, faceValue);
    }
}
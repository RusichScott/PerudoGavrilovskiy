package BetStrategy;

import Players.BotPlayer;
import org.example.Bet;
import org.example.Dice;
import Players.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.example.Dice.random;

public class BotBetStrategy {
    private static final int MAX_FACE_VALUE = 6;
    private Random random = new Random();

    public Bet placeBet(BotPlayer bot, Bet lastBet, List<Bet> previousBets, int diceCount, boolean isMaputa) {
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
            quantity = Math.max(lastBet.getQuantity(), quantity + 1);
        }

        int faceValue;
        if (lastBet != null) {
            faceValue = Math.max(bestFaceValue, lastBet.getFaceValue());
        } else {
            faceValue = Math.min(bestFaceValue, MAX_FACE_VALUE);
        }

        Bet newBet = new Bet(bot, quantity, faceValue);
        for (Bet bet : previousBets) {
            if (bet.getQuantity() == newBet.getQuantity() && bet.getFaceValue() == newBet.getFaceValue()) {

                newBet = new Bet(bot, newBet.getQuantity() + 1, newBet.getFaceValue());
                break;
            }
        }

        return newBet;
    }
}
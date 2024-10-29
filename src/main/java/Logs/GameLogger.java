package Logs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.Dice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogger {
    private List<ObjectNode> events;
    private ObjectMapper mapper;
    private String logFileName;

    public GameLogger(String logFileName) {
        this.logFileName = logFileName;
        this.events = new ArrayList<>();
        this.mapper = new ObjectMapper();
    }

    public void logPlayerTurn(String playerName, boolean isBot) {
        ObjectNode logEntry = mapper.createObjectNode();
        logEntry.put("event", "player_turn");
        logEntry.put("player_name", playerName);
        logEntry.put("is_bot", isBot);
        logEntry.put("description", playerName + (isBot ? " (Bot)" : "") + " начал ход.");
        events.add(logEntry);
        saveLog();
    }

    public void logBet(String playerName, int quantity, int faceValue) {
        ObjectNode logEntry = mapper.createObjectNode();
        logEntry.put("event", "bet");
        logEntry.put("player_name", playerName);
        logEntry.put("quantity", quantity);
        logEntry.put("face_value", faceValue);
        logEntry.put("description", playerName + " делает ставку: " + quantity + " на " + faceValue);
        events.add(logEntry);
        saveLog();
    }

    public void logMaputaRound(String playerName) {
        ObjectNode logEntry = mapper.createObjectNode();
        logEntry.put("event", "maputa");
        logEntry.put("player_name", playerName);
        logEntry.put("description", playerName + " объявляет Maputa!");
        events.add(logEntry);
        saveLog();
    }

    public void logLiarCall(String playerName, String targetPlayerName) {
        ObjectNode logEntry = mapper.createObjectNode();
        logEntry.put("event", "liar_call");
        logEntry.put("caller_name", playerName);
        logEntry.put("target_name", targetPlayerName);
        logEntry.put("description", playerName + " заявляет Liar против " + targetPlayerName);
        events.add(logEntry);
        saveLog();
    }

    public void logDiceValues(String playerName, List<Dice> diceList) {
        ObjectNode logEntry = mapper.createObjectNode();
        logEntry.put("event", "dice_values");
        logEntry.put("player_name", playerName);

        String[] diceValues = diceList.stream()
                .map(dice -> String.valueOf(dice.getValue()))
                .toArray(String[]::new);

        logEntry.put("dice_values", Arrays.toString(diceValues));
        logEntry.put("description", playerName + " выпал(и) кубик(и): " + Arrays.toString(diceValues));
        events.add(logEntry);
        saveLog();
    }

    private void saveLog() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(logFileName), events);
        } catch (IOException e) {
            System.err.println("Ошибка при записи логов: " + e.getMessage());
        }
    }
}
import static org.junit.jupiter.api.Assertions.*;

import BetStrategy.BotBetStrategy;
import org.junit.jupiter.api.Test;
import org.example.Bet;
import Players.BotPlayer;
import java.util.List;
import java.util.ArrayList;

public class BotBetStrategyTest {
    //Тест для BotBetStrategy: Проверка создания новой ставки ботом
    @Test
    public void testBotPlacesValidBet() {
        BotBetStrategy strategy = new BotBetStrategy();
        BotPlayer bot = new BotPlayer("TestBot");
        Bet lastBet = new Bet(bot, 2, 3);
        List<Bet> previousBets = new ArrayList<>();

        Bet bet = strategy.placeBet(bot, lastBet, previousBets, 5, false);

        assertTrue(bet.getQuantity() >= lastBet.getQuantity());
        assertTrue(bet.getFaceValue() >= lastBet.getFaceValue());
    }
}

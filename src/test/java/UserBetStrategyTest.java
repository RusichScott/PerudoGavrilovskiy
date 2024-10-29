import static org.junit.jupiter.api.Assertions.*;

import BetStrategy.UserBetStrategy;
import org.junit.jupiter.api.Test;
import Players.UserPlayer;
import org.example.Bet;

import java.util.Scanner;

public class UserBetStrategyTest {
    //Тест для UserBetStrategy: Проверка корректной валидации ставки пользователя
    @Test
    public void testUserValidBet() {
        UserBetStrategy strategy = new UserBetStrategy(new Scanner("2 4\n"));
        UserPlayer user = new UserPlayer("TestUser");

        Bet lastBet = new Bet(user, 1, 3);
        Bet bet = strategy.placeBet(user, lastBet, 5, false);

        assertEquals(2, bet.getQuantity());
        assertEquals(4, bet.getFaceValue());
    }
}

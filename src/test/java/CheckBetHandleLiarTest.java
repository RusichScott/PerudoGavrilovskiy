import static org.junit.jupiter.api.Assertions.*;

import MainLogic.CheckBet;
import org.example.Bet;
import org.junit.jupiter.api.Test;
import Players.Player;

import java.util.List;

public class CheckBetHandleLiarTest {
    //Тест для CheckBet: Проверка обработки вызова "лжеца" (Liar)
    @Test
    public void testHandleLiar() {
        CheckBet checker = new CheckBet();
        Player liarCaller = new Player("LiarCaller");
        Player betMaker = new Player("BetMaker");
        Bet bet = new Bet(betMaker, 2, 3);

        checker.handleLiar(liarCaller, betMaker, bet, List.of(betMaker, liarCaller));

        assertEquals(4, liarCaller.getDiceList().size());
        assertEquals(5, betMaker.getDiceList().size());
    }
}

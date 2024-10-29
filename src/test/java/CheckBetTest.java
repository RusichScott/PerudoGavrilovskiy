import static org.junit.jupiter.api.Assertions.*;

import MainLogic.CheckBet;
import org.junit.jupiter.api.Test;
import Players.Player;
import java.util.List;
import java.util.ArrayList;

public class CheckBetTest {
    //Тест для CheckBet: Проверка подсчета количества кубиков определённого значения
    @Test
    public void testGetDiceCount() {
        CheckBet checker = new CheckBet();
        Player player = new Player("TestPlayer");

        int count = checker.getDiceCount(3, List.of(player));

        assertTrue(count >= 0);
    }
}

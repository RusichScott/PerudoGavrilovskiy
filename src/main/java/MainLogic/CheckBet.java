package MainLogic;

import Players.Player;
import org.example.Bet;

import java.util.List;

public class CheckBet {
    public int getDiceCount(int diceValue, List<Player> players) {
        int count = 0;
        for (Player player : players) {
            count = (int) (count + player.getDiceList().stream()
                    .filter(dice -> dice.getValue() == diceValue)
                    .count());
        }
        return count;
    }

    public void handleLiar(Player liarCaller, Player betMaker, Bet bet, List<Player> players) {
        System.out.println("\nКубики всех игроков при проверке:");
        int betCount = bet.getQuantity();
        int actualCount = getDiceCount(bet.getFaceValue(), players);

        players.forEach(player -> {
            System.out.print(player.getName() + " кубики: ");
            player.getDiceList().forEach(dice -> System.out.print(dice.getValue() + " "));
            System.out.println();
        });

        if (actualCount >= betCount) {
            System.out.println("Ставка верна! " + liarCaller.getName() + " теряет один кубик.");
            liarCaller.removeDie();
        } else {
            System.out.println("Ставка неверна! " + betMaker.getName() + " теряет один кубик.");
            betMaker.removeDie();
        }
    }
}
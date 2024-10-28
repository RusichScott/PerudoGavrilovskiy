package org.example;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserBetStrategy {
    private Scanner scanner;

    public UserBetStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    public Bet placeBet(Player user, Bet lastBet, int diceCount, boolean isMaputa) {
        int quantity;
        int faceValue;

        while (true) {
            if (isMaputa && lastBet != null) {
                System.out.println(user.getName() + ", введите только количество кубиков для повышения:");
                quantity = scanner.nextInt();
                faceValue = lastBet.getFaceValue();
            } else {
                System.out.println(user.getName() + ", введите количество кубиков и значение (например, '2 3'):");

                try {
                    quantity = scanner.nextInt();
                    faceValue = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Некорректный ввод. Пожалуйста, введите количество и значение кубиков числом.");
                    scanner.nextLine();
                    continue;
                }
            }

            if (isValidBet(quantity, faceValue, diceCount, lastBet)) {
                return new Bet(user, quantity, faceValue);
            } else {
                System.out.println("Некорректная ставка. Попробуйте снова.");
            }
        }
    }

    private boolean isValidBet(int quantity, int faceValue, int diceCount, Bet lastBet) {
        if (quantity > diceCount) {
            System.out.println("Нельзя поставить больше " + diceCount + " кубиков!");
            return false;
        } else if (faceValue > 6) {
            System.out.println("Нельзя поставить значение кубиков больше 6!");
            return false;
        } else if (lastBet != null && (quantity <= lastBet.getQuantity() || (faceValue <= lastBet.getFaceValue() && !lastBet.equals(null)))) {
            System.out.println("Ставка должна быть больше предыдущей!");
            return false;
        }
        return true;
    }
}
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
            try {
                System.out.println(user.getName() + ", введите количество кубиков и значение (например, '2 3'):");
                quantity = scanner.nextInt();
                faceValue = scanner.nextInt();

                if (isValidBet(quantity, faceValue, diceCount, lastBet)) {
                    return new Bet(user, quantity, faceValue);
                } else {
                    System.out.println("Ставка должна либо увеличить количество кубиков, либо увеличить номинал или оба.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите два целых числа, например, '2 3'.");
                scanner.nextLine();
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
        }

        if (lastBet == null) {
            return true;
        }

        boolean quantityIncreased = quantity >= lastBet.getQuantity();
        boolean faceValueIncreased = faceValue >= lastBet.getFaceValue();

        return (quantity > lastBet.getQuantity() || faceValue > lastBet.getFaceValue())
                && quantityIncreased && faceValueIncreased;
    }
}


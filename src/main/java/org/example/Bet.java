package org.example;

public class Bet {
    private Player player;
    private int quantity;
    private int faceValue;

    public Bet(Player player, int quantity, int faceValue) {
        this.player = player;
        this.quantity = quantity;
        this.faceValue = faceValue;
    }

    private Player getPlayer() {
        return player;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getFaceValue() {
        return faceValue;
    }

    @Override
    public String toString() {
        return player.getName() + " ставит " + quantity + " кубика(-ов) со значением  " + faceValue;
    }
}

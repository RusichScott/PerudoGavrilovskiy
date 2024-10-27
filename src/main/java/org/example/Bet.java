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

    public Player getPlayer() {
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
        return quantity + " " + faceValue + " (от " + player.getName() + ")";
    }
}
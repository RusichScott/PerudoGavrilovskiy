package org.example;

import java.util.Random;

public class Dice {
    private int value;
    private static final Random random = new Random();

    public Dice() {
        roll();
    }

    public void roll() {
        value = random.nextInt(6) + 1;
    }

    public int getValue() {
        return value;
    }
}
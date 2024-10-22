package org.example;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private List<Dice> diceList;
    private int score;

    public Player(String name) {
        this.name = name;
        this.diceList = new ArrayList<>();
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void addDice(Dice dice) {
        diceList.add(dice);
    }

    public void rollDice() {
        for (Dice dice : diceList) {
            dice.roll();
        }
    }

    public List<Dice> getDiceList() {
        return diceList;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int points) {
        score += points;
    }
}

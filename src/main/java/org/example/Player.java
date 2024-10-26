package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Dice> diceList;

    public Player(String name) {
        this.name = name;
        this.diceList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addDice(Dice dice) {
        diceList.add(dice);
    }

    public List<Dice> getDiceList() {
        return diceList;
    }

    public List<Integer> getDiceValues() {
        List<Integer> values = new ArrayList<>();
        for (Dice dice : diceList) {
            values.add(dice.getValue());
        }
        return values;
    }

    public void removeDie() {
        if (!diceList.isEmpty()) {
            diceList.remove(diceList.size() - 1);
        }
    }
}

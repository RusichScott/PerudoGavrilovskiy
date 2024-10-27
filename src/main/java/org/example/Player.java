package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Dice> diceList;

    public Player(String name) {
        this.name = name;
        this.diceList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            addDice(new Dice());
        }
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

    public void removeDie() {
        if (!diceList.isEmpty()) {
            diceList.remove(diceList.size() - 1);
        }
    }

    public boolean hasDice() {
        return !diceList.isEmpty();
    }
}


package org.example;

public class RemoveDice {
    public void removeOneDie(Player player) {
        player.removeDie();
        System.out.println(player.getName() + " теряет один кубик.");
    }
}
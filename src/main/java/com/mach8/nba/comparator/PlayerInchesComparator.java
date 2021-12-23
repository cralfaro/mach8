package com.mach8.nba.comparator;

import com.mach8.nba.model.Player;

import java.util.Comparator;

public class PlayerInchesComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        return p2.getHeightInches().compareTo(p1.getHeightInches());
    }
}

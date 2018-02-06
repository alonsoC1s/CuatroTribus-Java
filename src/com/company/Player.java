package com.company;

import com.company.Tribes.Tribe;

public class Player {
    public Tribe playerTribe;
    public String playerName;

    public Player(Tribe playerTribe, String name) {
        this.playerTribe = playerTribe;
        this.playerName = name;
    }
}

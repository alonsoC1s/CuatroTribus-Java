package com.company;

import com.company.Tribes.Tribe;

public class Player {
    public Tribe playerTribe;
    public String playerName;

    public Player(Tribe playerTribe, String name) {
        this.playerTribe = playerTribe;
        this.playerName = name;
    }

    public void beginTurn(){
        //Set this as the current player
        Main.updateCurrentPlayer(this);

        this.playerTribe.collectPACs();
        System.out.println("Tienes " + this.playerTribe.getPacsAvailable() + " PACs disponibles ");


    }

    public void beginSetupRound(){
        //Set this as the current player
        Main.updateCurrentPlayer(this);

        System.out.println("You are setting up your initial pieces");

        //Create initial army.
        try {
            this.playerTribe.createInitialArmy(this.playerTribe.getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Specify the user is setting up
        this.playerTribe.isSettingUp = true;

    }

}

package com.company;

import com.company.GUI.Board;
import com.company.Tribes.BlueTribe;

public class Main {
    static Board mainBoard;
    public static Player currentPlayer;


    /*
    TODO: Show brand banner on splashscreen
    TODO: Complete class for players, and find a way to wrap gameplay around that.
    TODO: Implement game turns logic
    TODO: Call recalculate reserves every time the turn ends.
    TODO: Implement PAC recollection along with turn handling.

    //All piece objects are created when tribal army is created on Tribe constructor
    //Board matrix initialized when Board is created.
     */

    public static void main(String[] args) {
        //Creating the board initialized all graphics content


        //Creating players
        BlueTribe tribuB = new BlueTribe();

        //Creating a user Player
        Player jugador1 = new Player(tribuB,"Alonso");

        currentPlayer = jugador1;

        mainBoard = new Board();


    }
}

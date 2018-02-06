package com.company.Tribes;

import com.company.GUI.Board;
import com.company.GUI.BoardSquare;
import com.company.Logic.LogicEngine;
import com.company.Pieces.GuiPiece;
import com.company.Pieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Tribe {
    private Piece.colors myColor;
    private int PacsAvailable;
    private List<GuiPiece> tribalArmy;
    List<BoardSquare> dominantTerritory; //Is this information really necessary???
    Board gameBoard;
    private LogicEngine logicEngine = new LogicEngine();


    //TODO: Cities are missing
    //TODO: List of squares dominated by tribe still unimplemented.

    /**
     * Constructor. When called, a new tribe of color x is created.
     * The method then creates a full army for each color as specified by the game´s rules
     * The int arrays are the specific powers each piece of each category must have. These are defined by game rules
     * @param color
     */
    public Tribe(Piece.colors color){
        this.myColor = color;

        switch (color){
            case WHITE:
                int[] w_infantriesPowerList = {1,1,1,1,1, 2,2,2,2, 3,3, 5,5 };
                int[] w_horsesPowerList = {5,5,5,  3,3};
                int[] w_artillieryPowersList = {3,3, 2};

                this.tribalArmy.addAll(this.createArmy(Piece.colors.WHITE,w_infantriesPowerList,w_horsesPowerList,w_artillieryPowersList));
                break;
            case GREEN:
                int[] g_infantriesPowerList = {1,1,1,1,1, 2,2,2,2,2,2, 3,3, 4,4, 5,5 };
                int[] g_horsesPowerList = {5, 4,4, 3};
                int[] g_artillieryPowersList = {3,3, 2,2};

                this.tribalArmy.addAll(this.createArmy(Piece.colors.GREEN,g_infantriesPowerList,g_horsesPowerList,g_artillieryPowersList));
                break;
            case BLUE:
                int[] b_infantriesPowerList = {1,1,1,1, 2,2,2,2, 3,3, 4,4,4, 5,5,5};
                int[] b_horsesPowersList = {5,5, 4,4, 3};
                int[] b_artillieriesPowersList = {3,3,3, 2};

                this.tribalArmy.addAll(this.createArmy(Piece.colors.BLUE,b_infantriesPowerList,b_horsesPowersList,b_artillieriesPowersList));
                break;
            case RED:
                int[] r_infantriesPowerList = {1,1,1,1,1, 2,2,2,2, 3,3,3, 4,4,4,  5,5,5 };
                int[] r_horsesPowerList = {5,5,5,  3,3};
                int[] r_artillieryPowerList = {3,3, 2};

                this.tribalArmy.addAll(this.createArmy(Piece.colors.RED,r_infantriesPowerList,r_horsesPowerList,r_artillieryPowerList));
                break;
        }

    }

    /**
     * Method to create the army upon specified color, and powers of pieces
     * @param color: Color of the tribe that is requesting the army, and therefore the color of the army
     * @param infrantryPowerList: Array of integers that specifies how many infantry pieces are to be created, and their designated powers
     * @param horsePowerList: Array of integers that specifies how many horse pieces are to be created, and their designated powers
     * @param artillieryPowerList: Array of integers that specifies how many artilliery pieces are to be created, and their designated powers
     * @return List of pieces that are to be added to the tribal army
     */
    private List<GuiPiece> createArmy(Piece.colors color, int[] infrantryPowerList, int[] horsePowerList, int[] artillieryPowerList){
        //Fetching piece icons
        List<GuiPiece> newArmy = new ArrayList<>();
        Image infantryIcon = this.logicEngine.getIconForPiece(color, Piece.types.INFANTRY);
        Image horseIcon = this.logicEngine.getIconForPiece(color, Piece.types.HORSE);
        Image artillieryIcon = this.logicEngine.getIconForPiece(color, Piece.types.ARTILLERY);

        //Creating infrantries.
        for (int anInfrantryPowerList : infrantryPowerList) {
            newArmy.add(new GuiPiece(color, Piece.types.INFANTRY, anInfrantryPowerList, false, infantryIcon));
        }

        //Creating horses
        for (int aHorsePowerList : horsePowerList) {
            newArmy.add(new GuiPiece(color, Piece.types.HORSE, aHorsePowerList, false, horseIcon));
        }

        //Creating artillieries
        for (int anArtillieryPowerList : artillieryPowerList) {
            newArmy.add(new GuiPiece(color, Piece.types.ARTILLERY, anArtillieryPowerList, false, artillieryIcon));
        }

        return newArmy;
    }

    /**
     * Method used to count how many cities are dominated, and collect one PAC for each
     * Uses the Logic Engine to count cities.
     */
    private void collectPACs(){
        this.PacsAvailable = LogicEngine.countCitiesDominated(this);
    }


    //Getters and setters

    public Piece.colors getColor() {
        return myColor;
    }
}

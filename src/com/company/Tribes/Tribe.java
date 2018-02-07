package com.company.Tribes;

import com.company.GUI.BoardSquare;
import com.company.GUI.ReservesSquare;
import com.company.Logic.LogicEngine;
import com.company.Pieces.GuiPiece;
import com.company.Pieces.Piece;
import com.company.colors;
import sun.rmi.runtime.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.Pieces.Piece.types.HORSE;

public class Tribe {
    protected colors myColor;
    protected int PacsAvailable;
    protected List<GuiPiece> tribalArmy;
    List<BoardSquare> dominantTerritory; //Is this information really necessary??? FIXME

    protected LogicEngine logicEngine;
    private ReservesSquare reserves;


    //TODO: Cities are missing
    //TODO: List of squares dominated by tribe still unimplemented. (Unecessary maybe)

    /**
     * Constructor. When called, a new tribe of color x is created.
     * The method then creates a full army for each color as specified by the game´s rules
     * The int arrays are the specific powers each piece of each category must have. These are defined by game rules
     * @param color
     */
    public Tribe(colors color, LogicEngine logicEngine) throws Exception {
        this.myColor = color;
        this.tribalArmy = new ArrayList<>();
        this.logicEngine = logicEngine;
        this.reserves = new ReservesSquare(this);

        switch (color) {
            case WHITE:
                int[] w_infantriesPowerList = {1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 5, 5};
                int[] w_horsesPowerList = {5, 5, 5, 3, 3};
                int[] w_artillieryPowersList = {3, 3, 2};

                this.tribalArmy.addAll(this.createArmy(colors.WHITE, w_infantriesPowerList, w_horsesPowerList, w_artillieryPowersList));
                break;
            case GREEN:
                int[] g_infantriesPowerList = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 5, 5};
                int[] g_horsesPowerList = {5, 4, 4, 3};
                int[] g_artillieryPowersList = {3, 3, 2, 2};

                this.tribalArmy.addAll(this.createArmy(colors.GREEN, g_infantriesPowerList, g_horsesPowerList, g_artillieryPowersList));
                break;
            case BLUE:
                int[] b_infantriesPowerList = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5};
                int[] b_horsesPowersList = {5, 5, 4, 4, 3};
                int[] b_artillieriesPowersList = {3, 3, 3, 2};

                this.tribalArmy.addAll(this.createArmy(colors.BLUE, b_infantriesPowerList, b_horsesPowersList, b_artillieriesPowersList));
                break;
            case RED:
                int[] r_infantriesPowerList = {1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5};
                int[] r_horsesPowerList = {5, 5, 5, 3, 3};
                int[] r_artillieryPowerList = {3, 3, 2};

                this.tribalArmy.addAll(this.createArmy(colors.RED, r_infantriesPowerList, r_horsesPowerList, r_artillieryPowerList));
                break;
        }

        placePiecesOnReserveArea();

    }

    /**
     * Method to create the army upon specified color, and powers of pieces
     * @param color: Color of the tribe that is requesting the army, and therefore the color of the army
     * @param infrantryPowerList: Array of integers that specifies how many infantry pieces are to be created, and their designated powers
     * @param horsePowerList: Array of integers that specifies how many horse pieces are to be created, and their designated powers
     * @param artillieryPowerList: Array of integers that specifies how many artilliery pieces are to be created, and their designated powers
     * @return List of pieces that are to be added to the tribal army
     */
    private List<GuiPiece> createArmy(colors color, int[] infrantryPowerList, int[] horsePowerList, int[] artillieryPowerList) throws Exception {
        //Fetching piece icons
        List<GuiPiece> newArmy = new ArrayList<>();

        Image infantryIcon = this.logicEngine.getIconForPiece(color, Piece.types.INFANTRY);
        Image horseIcon = this.logicEngine.getIconForPiece(color, HORSE);
        Image artillieryIcon = this.logicEngine.getIconForPiece(color, Piece.types.ARTILLERY);

        //Creating infrantries.
        for (int anInfrantryPowerList : infrantryPowerList) {
            newArmy.add(new GuiPiece(color, Piece.types.INFANTRY, anInfrantryPowerList, false, infantryIcon));
        }

        //Creating horses
        for (int aHorsePowerList : horsePowerList) {
            newArmy.add(new GuiPiece(color, HORSE, aHorsePowerList, false, horseIcon));
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
    public void collectPACs(){
        this.PacsAvailable = LogicEngine.countCitiesDominated(this);
    }

    /**
     * Method to place pieces on reserve. This is only done once, when the game starts.
     * The rest of the time ReserveSquare queries the list of pieces to check if they are deployed.
     * Visual accomodation of pieces is handled by the ReserveSquare.
     */
    private void placePiecesOnReserveArea(){
        this.reserves.addReserves(this.tribalArmy);
    }

    public boolean canBuyThisPiece(GuiPiece piece){
        boolean canBuyIt;

        int price = piece.getPiecePrice();

        //Check if the user has enough PACs to buy the selected piece
        canBuyIt = PacsAvailable - price > 0;

        return canBuyIt;
    }

    public void buyThisPiece(GuiPiece piece){

        int piecePrice = piece.getPiecePrice();
        this.PacsAvailable -= piecePrice;

        this.reserves.removeAndDeploy(piece);

    }


    //Getters and setters

    public colors getColor() {
        return myColor;
    }

    public ReservesSquare getReservesSquare(){
        return this.reserves;
    }

    public List<GuiPiece> getTribalArmy() {
        return tribalArmy;
    }

    public int getPacsAvailable() {
        return PacsAvailable;
    }
}

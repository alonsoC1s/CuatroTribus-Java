package com.company.Logic;
import com.company.GUI.Board;
import com.company.GUI.BoardSquare;
import com.company.Pieces.GuiPiece;
import com.company.Pieces.Piece;
import com.company.Tribes.Tribe;
import com.company.colors;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LogicEngine {
    //Leave space for dimension constants
    //TODO: FILL OUT PROPER DIMENSION CONSTANTS


    //GAMEPLAY RELATED METHODS

    /**
     * Method inside logic engine to resolve occuring battles.
     * List of pieces is sorted. Pieces are eliminated in 1:1 ratio starting from bottom.
     * @param defendingPieces Pieces originally on sqaure. Pieces defending the city
     * @param attackingPieces Pieces attacking the city.
     * @return List of pieces surviving the battle
     */
    public static List<GuiPiece> resolveBattle(List<GuiPiece> defendingPieces, List<GuiPiece> attackingPieces){
        List<GuiPiece> winnerPieces = null;

        System.out.println("Solving a battle");

        //Sorting the list of pieces
        Collections.sort(defendingPieces);
        Collections.sort(attackingPieces);

        //Check if an artillliery is withing *DEFENDING* pieces
        for (GuiPiece piece: defendingPieces){
            if (piece.getType().equals(Piece.types.ARTILLERY)){
                //If artilliery found, remove it, along with three lowermost troops on the attacker´s ranks
                int artillieryIndex = defendingPieces.indexOf(piece);


                defendingPieces.get(artillieryIndex).killPiece();
                defendingPieces.remove(artillieryIndex);

                for(int k=0; k<3; k++){
                    attackingPieces.get(k).killPiece();
                    attackingPieces.remove(k);
                }

            }

        }

        //Calculate sum of values here to account for lost pieces if artilliery defended
        int sumDefenders = sumPicesValues(defendingPieces);
        int sumAttackers = sumPicesValues(attackingPieces);

        //Check which party won
        if (sumAttackers - sumDefenders < 0){ // Defenders win
            for (int i=0; i<attackingPieces.size(); i++){

                eliminatePiece(attackingPieces,i);

                eliminatePiece(defendingPieces,i);

                //Return whatever pieces are left
                winnerPieces = defendingPieces;
            }
        } else if (sumAttackers - sumDefenders > 0){ // Attackers win
            for (int i=0; i<defendingPieces.size(); i++){

                eliminatePiece(attackingPieces,i);

                eliminatePiece(defendingPieces,i);

                //Return whatever pieces are left
                winnerPieces = attackingPieces;
            }
        }else{ // Draw!!
            //Return empty list instead of null to avoid runtime errors
            winnerPieces = new ArrayList<>();
        }


        return winnerPieces;
    }

    /**
     * Shorthand function to kill and eliminate piece from a list
     * @param pieceList: List that contains the piece
     * @param pieceIndex: Piece that is to be killed and un-deployed
     */
    private static void eliminatePiece(List<GuiPiece> pieceList, int pieceIndex){
        pieceList.get(pieceIndex).killPiece();
        pieceList.remove(pieceIndex);
    }

    /**
     * Sums the values of all the piece´s power on the list
     * @param pieces: List of pieces to be summed
     * @return int: Total power of all pieces passed
     */
    private static int sumPicesValues(List<GuiPiece> pieces){
        int sigma = 0;
        for (GuiPiece piece: pieces){
            sigma += piece.getPower();
        }
        return sigma;
    }

    /**
     * Assigns default x,y coordinates to the square so clicks can be detected.
     * @param row: Row assigned
     * @param col: Col assigned
     */
    public static void centerSquare(int row, int col, BoardSquare boardSquare){
        switch (col){
            case 0:
                boardSquare.setxPos(0);
                break;
            case 1:
                boardSquare.setxPos(100);
                break;
            case 2:
                boardSquare.setxPos(200);
                break;
            case 3:
                boardSquare.setxPos(300);
                break;
            case 4:
                boardSquare.setxPos(400);
                break;
            case 5:
                boardSquare.setxPos(500);
                break;
        }

        switch (row){
            case 0:
                boardSquare.setyPos(0);
                break;
            case 1:
                boardSquare.setyPos(100);
                break;
            case 2:
                boardSquare.setyPos(200);
                break;
            case 3:
                boardSquare.setyPos(300);
                break;
            case 4:
                boardSquare.setyPos(400);
                break;
            case 5:
                boardSquare.setyPos(500);
                break;
        }
    }

    /**
     * Gets the icon for a specific piece using pieceColor and type, making use of naming conventions for icons
     * @param pieceColor: Enum declared in Pieces. Corresponds to each of 4 tribe colors
     * @param pieceType: Enum declared in Pieces. Corresponds to each of the 3 possible piece types + city
     * @return Image object with the image obtained for the specified piece
     */
    public Image getIconForPiece(colors pieceColor, Piece.types pieceType) throws Exception {
        String pathName = "";

        switch (pieceColor) {
            case RED:
                pathName += "r_";
                break;
            case BLUE:
                pathName += "b_";
                break;
            case GREEN:
                pathName += "g_";
                break;
            case WHITE:
                pathName += "w_";
                break;
        }

        switch (pieceType) {
            case HORSE:
                pathName += "Horse.png";
                break;
            case INFANTRY:
                pathName += "Infantry.png";
                break;
            case ARTILLERY:
                pathName += "Artilliery.png";
                break;
            case CITY:
                pathName += "City.png";
                break;
        }

        URL iconUrl = getClass().getClassLoader().getResource("com/company/images/" + pathName);

        if (iconUrl != null) {
            return new ImageIcon(iconUrl).getImage();
        } else {
            throw new Exception("The icon for the " + pathName + " piece, was not found");
        }

    }

    /**
     * Counts how many cities theTribe controls
     * @param theTribe: the Tribe calling the method
     * @return int: number of cities dominated
     */
    public static int countCitiesDominated(Tribe theTribe){
        int totalCities = 0;

        for( int row=0; row<6 ;row++){
            for (int col=0; col<6; col++){
                if(Board.boardMatrix[row][col].dominantColor == theTribe.getColor()){
                    totalCities++;
                }
            }
        }

        return totalCities;
    }



}

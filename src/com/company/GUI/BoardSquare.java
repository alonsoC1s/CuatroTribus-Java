package com.company.GUI;

import com.company.Logic.LogicEngine;
import com.company.Pieces.GuiPiece;
import com.company.Pieces.Piece;
import com.company.colors;

import java.util.ArrayList;
import java.util.List;

public class BoardSquare {

    public List<GuiPiece> pieces = new ArrayList<>();
    public colors dominantColor; //This is to be determined by the color of the city in this square.
    public boolean hasCity;

    public int row;
    public int col;

    private int x;
    private int y;

    public BoardSquare(int row, int col){
        this.row = row;
        this.col = col;
        this.dominantColor = null;
        this.hasCity = false;

        LogicEngine.centerSquare(row,col,this);
        this.determineDominantColor();
    }

    /**
     * This method is used when first creating pieces and adding them to board
     * IMPORTANT NOTE: THIS IS NOT TO BE USED WHILE MOVING PIECES DURING GAME. ONLY DURING SETUP, NOT ABLE TO HANDLE COLLISIONS
     * @param newPiece: Piece that was created and needs to be added.
     */
    public void addPieceToSquare(GuiPiece newPiece){
        pieces.add(newPiece);
        System.out.println("I, square on column " + this.col + " and row " + this.row + " received new piece of type " );

        this.determineDominantColor();
    }


    //TODO: Clone method to accept a single GuiPiece instead of a List, and bypass checking previous squares.
    /**
     * Method used during gameplay when different players movilze army
     * NOTE: THIS IS THE CORRECT METHOD TO BE USED DURING GAMEPLAY AS IT IS EQUIPPED TO HANDLE COLLISIONS.
     * BTW no need to change deployment status
     * @param newPieces: List of pieces that are being moved to this square
     */
    public void addPiecesToSquare(List<GuiPiece> newPieces, colors actingColor){
        System.out.println("Some new pieces were added to square " + this.row + "," + this.col);

        //First, get the square that previously contained them, and remove pieces
        for (GuiPiece piece: newPieces){
            int prevCol = piece.getColumn();
            int prevRow = piece.getRow();

            Board.boardMatrix[prevRow][prevCol].removePieceFromSquare(piece);
        }

        if (actingColor != this.dominantColor && this.dominantColor != null){ //Collision detected
            List<GuiPiece> survivingPieces = LogicEngine.resolveBattle(this.pieces,newPieces);

            if (!survivingPieces.isEmpty()) {
                for (GuiPiece piece : survivingPieces) {
                    piece.setRowCol(this.row, this.col);
                }

                pieces.addAll(survivingPieces);
            }
        }else{ // No battle was detected
            for (GuiPiece piece: newPieces){
                piece.setRowCol(this.row,this.col);
            }

            pieces.addAll(newPieces);
        }

        this.determineDominantColor();
    }

    public void removePieceFromSquare(GuiPiece newPiece){
        pieces.remove(newPiece);
        System.out.println("I, square on column " + this.col + " and row " + this.row + " removed a piece" );

        this.determineDominantColor();
    }

    public boolean isEmpty(){
        return this.pieces.isEmpty();
    }

    /**
     * Determines which color controls a square by checking if it has a city and getting city color, or checking the color of the pieces on
     * that square.
     */
    private void determineDominantColor(){
        if (this.hasCity){
            for (GuiPiece piece: this.pieces){
                //Find the city in the list, and get its color
                if (piece.getType() == Piece.types.CITY){
                    this.dominantColor = piece.getColor();
                    return;
                }
            }
        } else if (! this.pieces.isEmpty()) {
            //Get the color of the first piece on the square
            this.dominantColor = this.pieces.get(0).getColor();
        }else{
            this.dominantColor = null;
        }
    }

    /**
     * Checks if the coordinates provided fall into the the square´s area
     * @param clickX: X coord of the click
     * @param clickY: Y coord of the click
     * @return Bool. Is click inside of square´ area?
     * NOTE: The squares are assumed to be 100 x 100 px. This is slightly different in reality Fixme
     */
    public boolean isClicked( int clickX, int clickY){
        return (this.x <= clickX && this.x +99 >= clickX
                && this.y <= clickY && this.y +99 >= clickY);
    }

    public List<GuiPiece> getPieces() {
        return pieces;
    }

    public void setxPos(int x){
        this.x = x;
    }

    public void setyPos(int y){
        this.y = y;
    }

}

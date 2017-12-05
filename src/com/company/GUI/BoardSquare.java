package com.company.GUI;

import com.company.Pieces.GuiPiece;
import com.company.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class BoardSquare {
    //Dimension constants.
    public static final int SQR_00_START = 0;
    // ... blah blah blah ... //

    public List<GuiPiece> pieces = new ArrayList<>();
    public Piece.colors dominantColor; //This is to be determined by the color of the city in this square.
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

        this.setXY(row,col);
        this.determineDominantColor();
    }

    //Fixme: Println just for development and testing purposes

    /**
     * This method is used when first creating pieces and adding them to board
     * IMPORTANT NOTE: THIS IS NOT TO BE USED WHILE MOVING PIECES DURING GAME. ONLY DURING SETUP
     * @param newPiece: Piece that was created and needs to be added.
     */
    public void addPieceToSquare(GuiPiece newPiece){
        pieces.add(newPiece);
        System.out.println("I, square on column " + this.col + " and row " + this.row + " received new piece of type " );

        this.determineDominantColor();
    }

    /**
     * Method used during gameplay when different players movilze army
     * NOTE: THIS IS THE CORRECT METHOD TO BE USED DURING GAMEPLAY AS IT IS EQUIPPED TO HANDLE COLLISIONS.
     * @param newPieces: List of pieces that are being moved to this square
     */
    public void addPiecesToSquare(List<GuiPiece> newPieces, Piece.colors actingColor){
        System.out.println("Some new pieces were added to square " + this.row + "," + this.col);

        for (GuiPiece piece: newPieces){
            piece.setRowCol(this.row,this.col);
        }

        pieces.addAll(newPieces);

        if (actingColor != this.dominantColor && this.dominantColor != null){
            this.resolveBattles();
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

    /**
     * Assigns default x,y coordinates to the square so clicks can be detected.
     * @param row: Row assigned
     * @param col: Col assigned
     */
    public void setXY(int row, int col ){
        switch (col){
            case 0:
                this.x = 0;
                break;
            case 1:
                this.x = 100;
                break;
            case 2:
                this.x = 200;
                break;
            case 3:
                this.x = 300;
                break;
            case 4:
                this.x = 400;
                break;
            case 5:
                this.x = 500;
                break;
        }

        switch (row){
            case 0:
                this.y = 0;
                break;
            case 1:
                this.y = 100;
                break;
            case 2:
                this.y = 200;
                break;
            case 3:
                this.y = 300;
                break;
            case 4:
                this.y = 400;
                break;
            case 5:
                this.y = 500;
                break;
        }
    }

    public List<GuiPiece> getPieces() {
        return pieces;
    }

    //ToDo: Write method to resolve battles or "collisions"
    private void resolveBattles(){
        for( GuiPiece piece : this.pieces){
            if (piece.getColor() != this.dominantColor){
                System.out.println("A battle has been detected at " + this.row + "," + this.col);
            }
        }
    }


}

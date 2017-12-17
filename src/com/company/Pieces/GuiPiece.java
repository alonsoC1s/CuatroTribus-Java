package com.company.Pieces;

import java.awt.*;

public class GuiPiece extends Piece implements Comparable<GuiPiece>{
    //Parameters to reference their position in board grid. Start at 0,0 to map directly to a matrix
    private int row;
    private int column;

    public Image icon;
    public int xPos;
    public int yPos;

    public int iconHeight;
    public int iconWidth;

    public GuiPiece(colors color, types type, int power, boolean isDeployed, int row, int column, Image icon) {
        super(color, type, power, isDeployed);
        this.row = row;
        this.column = column;
        this.icon = icon;

        //TODO: Ask if piece is deployed. If not deployed render outside of board and put into reserves box
        this.centerPieceToSquare();

        this.iconHeight = icon.getHeight(null);
        this.iconWidth = icon.getWidth(null);
    }

    /*
    Checks row / col parameters of the piece, and centers it into the square
    FIXME: Needs tweaking to center pieces correctly
     */
    public void centerPieceToSquare(){
        int pieceCol = this.column;
        int pieceRow = this.row;

        switch (pieceCol){
            case 0:
                this.setxPos(10);
                break;
            case 1:
                this.setxPos(110);
                break;
            case 2:
                this.setxPos(210);
                break;
            case 3:
                this.setxPos(310);
                break;
            case 4:
                this.setxPos(410);
                break;
            case 5:
                this.setxPos(510);
                break;
        }

        switch (pieceRow){
            case 0:
                this.setyPos(10);
                break;
            case 1:
                this.setyPos(110);
                break;
            case 2:
                this.setyPos(210);
                break;
            case 3:
                this.setyPos(310);
                break;
            case 4:
                this.setyPos(410);
                break;
            case 5:
                this.setyPos(510);
                break;
        }
    }

    /**
     * Sets row and col in one go, and centers it to the corresponding square by modifying x,y coordinates
     * @param row:
     * @param col:
     */
    public void setRowCol(int row, int col){
        this.row = row;
        this.column = col;

        this.centerPieceToSquare();
    }

    @Override
    public String toString() {
        return (  this.pColor.toString() + " " + this.pType.toString() + " with power " + this.pPower  );
    }

    public void killPiece(){
        this.isDeployed = false;
        //TODO: Draw piece outside board, and inside deploying area.
    }


    /*
    Getters and Setters
     */

    public Image getIcon() {
        return icon;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;


    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public types getType(){ return this.pType; }

    public colors getColor(){ return this.pColor; }

    public int getPower(){ return  this.pPower; }

    @Override
    public int compareTo(GuiPiece o) {
        int myPower = this.getPower();
        int otherPower = o.getPower();
        int diff = myPower - otherPower;

        if (diff < 0){
            return -1;
        }else if (diff == 0){
            return diff;
        }else{
            return 1;
        }

    }
}



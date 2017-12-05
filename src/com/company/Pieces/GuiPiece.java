package com.company.Pieces;

import com.company.GUI.Board;

import java.awt.*;

public class GuiPiece extends Piece {
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
    private void centerPieceToSquare(){
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

    private void centerPieceToRow(){
        int x = this.xPos;

        if (x > 0 && x < 100){
            this.row = 0;
        } else if (x < 200){
            this.row = 1;
        }else if (x < 300){
            this.row = 2;
        }else if (x < 400){
            this.row = 3;
        }else if (x < 500){
            this.row = 4;
        }else if (x < 600){
            this.row = 5;
        }
    }

    private void centerPieceToColumn(){
        int y = this.getyPos();

        if (y > 0 && y < 100){
            this.column = 0;
        } else if (y < 200){
            this.column = 1;
        }else if (y < 300){
            this.column = 2;
        }else if (y < 400){
            this.column = 3;
        }else if (y < 500){
            this.column = 4;
        }else if (y < 600){
            this.column = 5;
        }
    }

    @Override
    public String toString() {
        return (  this.pColor.toString() + " " + this.pType.toString() + " with power " + this.pPower  );
    }

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

    public int getIconHeight() {
        return iconHeight;
    }

    public int getIconWidth() {
        return iconWidth;
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
}



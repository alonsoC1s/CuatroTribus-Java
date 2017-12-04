package com.company.GUI;

import com.company.Pieces.GuiPiece;
import com.company.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class BoardSquare {
    public List<GuiPiece> pieces = new ArrayList<>();
    public Piece.colors dominantColor; //This is to be determined by the color of the city in this square.

    public int row;
    public int col;

    public BoardSquare(int row, int col){
        this.row = row;
        this.col = col;
        this.dominantColor = null;
    }

    //Fixme: Println just for development and testing purposes
    public void addPieceToList(GuiPiece newPiece){
        pieces.add(newPiece);
        System.out.println("I, square on column " + this.col + " and row " + this.row + " received new piece of type " );
    }

    public void removePieceFromList(GuiPiece newPiece){
        pieces.remove(newPiece);
        System.out.println("I, square on column " + this.col + " and row " + this.row + " removed a piece" );
    }

    //ToDo: Write method to resolve battles or "collisions"


}

package com.company.GUI;

import com.company.Pieces.GuiPiece;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DragNDropListener implements MouseMotionListener, MouseListener{
    private BoardSquare[][] boardMatrix;
    private BoardSquare clickedSquare;
    private Board gameBoard;
    private List<GuiPiece> piecesOnSquare = new ArrayList<>();

    private GuiPiece dragPiece;
    private int xOffset;
    private int yOffset;

    /**
     * Public constructor to set the game pieces & board accordingly
     * @param board: Main board where game is happening
     */
    public DragNDropListener( Board board){
        this.gameBoard = board;
        this.boardMatrix = this.gameBoard.boardMatrix;
    }

    /**
     * Called when mouse clicked. Checks if click coordinates correspond to a square
     * @param e: Mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();

        //TODO: Check if the click is within board, or in staging area or reserves area

        //Get the square that is being clicked by iterating through matrix
        for(int row=0; row<6; row++){
            for(int col=0; col<6; col++){
                if (boardMatrix[row][col].isClicked(clickX,clickY)){
                    this.clickedSquare = boardMatrix[row][col];
                    System.out.println("Square on " + this.clickedSquare.row + "," + this.clickedSquare.col + " was clicked");
                }
            }
        }

        //Get a response from the clicked square
        if (clickedSquare.dominantColor == gameBoard.colorInTurn) {
            this.piecesOnSquare = clickedSquare.getPieces();

            //Creating panel to display options, and new list to contain radio buttons
            final JPanel panel = new JPanel();
            List<JRadioButton> pieceOption = new ArrayList<>();

            //Create new list of radio buttons representing available pieces and adding them to the panel
            for (GuiPiece piece: piecesOnSquare){
                pieceOption.add(new JRadioButton(piece.toString()));

                //Adding the radiobutton to the panel
                panel.add(pieceOption.get(piecesOnSquare.indexOf(piece)));
            }

            //Show the option pane
            JOptionPane.showMessageDialog(this.gameBoard,panel,"Select pieces to mobilize",JOptionPane.PLAIN_MESSAGE);



        } else {
            System.out.println("You have no power over this city");
            return;
        }

        //Proceed to show a dialogue that shows pieces user can gather and move.

    }


    //Fixme: Work needed on this method. Don´t move just pieces, move lists of pieces. i.e use gameplay equipped method
    @Override
    public void mouseReleased(MouseEvent e) {
        int clickX;
        int clickY;

        if ( this.dragPiece != null){
            clickX = e.getPoint().x - this.xOffset;
            clickY = e.getPoint().y - this.yOffset;

            //Move piece to new Location
            this.gameBoard.parseXYCoords(this.dragPiece);
            this.gameBoard.centerPieceToSquare(this.dragPiece);
            this.gameBoard.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.dragPiece != null){

            int x = e.getPoint().x - this.xOffset;
            int y = e.getPoint().y - this.yOffset;

            //Temporary x,y coordinates that serve animation puposes
            this.dragPiece.setxPos(x);
            this.dragPiece.setyPos(y);
            this.gameBoard.repaint();
        }

    }


    /*
    Unused functions. Implemented to fulfill interface.
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }

}

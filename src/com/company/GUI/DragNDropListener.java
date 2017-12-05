package com.company.GUI;

import com.company.Pieces.GuiPiece;
import javafx.scene.control.RadioButton;

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
    private List<GuiPiece> piecesOnTheMove = new ArrayList<>();

    Boolean troopsAreBeingMobilized;

    /**
     * Public constructor to set the game pieces & board accordingly
     * @param board: Main board where game is happening
     */
    public DragNDropListener( Board board){
        this.gameBoard = board;
        this.boardMatrix = this.gameBoard.boardMatrix;
        this.troopsAreBeingMobilized = false;
    }

    /**
     * Called when mouse clicked. Checks if click coordinates correspond to a square, and shows dialogue to mobilize if so
     * @param e: Mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();

        //TODO: Check if the click is within board, or in staging area or reserves area

        this.getClickedSquare(clickX,clickY);

        if (!troopsAreBeingMobilized){

            //Get a response from the clicked square
            if (clickedSquare.dominantColor == gameBoard.colorInTurn && !clickedSquare.isEmpty()) {
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

                //Getting the boolean values of the radiobuttons, and adding corresponding pieces to list of pieces being dragged
                for (JRadioButton button: pieceOption){
                    if (button.isSelected()){
                        //Add the piece that is in the same index as the radio button
                        piecesOnTheMove.add(this.piecesOnSquare.get(pieceOption.indexOf(button)));
                    }
                }

                if(!piecesOnTheMove.isEmpty())
                    troopsAreBeingMobilized = true;

                //Todo: Cleanup all lists used.

            } else {
                System.out.println("You have no power over this city");
                return;
            }
        } else {
            this.clickedSquare.addPiecesToSquare(piecesOnTheMove, gameBoard.colorInTurn);
            this.troopsAreBeingMobilized = false;
            gameBoard.repaint();
        }


    }

    /**
     * Called when mouse is being moved. Only acts if troopsAreBeingMobilized
     * @param e: MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (troopsAreBeingMobilized) {

            int x = e.getX();
            int y = e.getY();

            for (GuiPiece piece : piecesOnTheMove) {
                piece.setxPos(x);
                piece.setyPos(y);
                this.gameBoard.repaint();
            }
        }
    }

    /**
     * Function to check what square was pressed by checking through matrix
     * @param clickX: x coord of the click
     * @param clickY: y coord of the click
     */
    public void getClickedSquare(int clickX, int clickY){
        for(int row=0; row<6; row++){
            for(int col=0; col<6; col++){
                if (boardMatrix[row][col].isClicked(clickX,clickY)){
                    this.clickedSquare = boardMatrix[row][col];
                    System.out.println("Square on " + this.clickedSquare.row + "," + this.clickedSquare.col + " was clicked");
                }
            }
        }
    }


    /*
    Unused functions. Implemented to fulfill interface.
    */
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {
    }


}

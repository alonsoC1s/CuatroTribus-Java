package com.company.GUI;

import com.company.Main;
import com.company.Pieces.GuiPiece;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class DragNDropListener implements MouseMotionListener, MouseListener{
    private BoardSquare[][] boardMatrix;
    private BoardSquare clickedSquare;
    private Board gameBoard;
    private List<GuiPiece> piecesOnSquare = new ArrayList<>();
    private List<GuiPiece> piecesOnTheMove = new ArrayList<>();

    private Boolean troopsAreBeingMobilized;
    private Boolean deployingTroops;

    private GuiPiece pieceOnDeployment;
    private ReservesSquare currentPlayerReserves;

    /**
     * Public constructor to set the game pieces & board accordingly
     * @param board: Main board where game is happening
     */
    public DragNDropListener( Board board){
        this.gameBoard = board;
        this.boardMatrix = Board.boardMatrix;
        this.troopsAreBeingMobilized = false;
        this.deployingTroops = false;
    }

    /**
     * Called when mouse clicked. Checks if click coordinates correspond to a square, and shows dialogue to mobilize if so
     * @param e: Mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();

        //TODO: Move the piece translation funcitonality to a separete function, and call said function on mousePressed and on Mouse Released


        if (reservesClicked(clickX)){

            this.currentPlayerReserves = Main.currentPlayer.playerTribe.getReservesSquare();

            pieceOnDeployment = getClickedPiece(clickX,clickY);
            deployingTroops = true;


        } else { //Reserves were not clicked.
            this.clickedSquare = getClickedSquare(clickX,clickY);

            if (!troopsAreBeingMobilized) {

                //Check if the user has power over the clicked square
                if (clickedSquare.dominantColor == gameBoard.tribeInTurn.getColor() && !clickedSquare.isEmpty()) {
                    this.piecesOnSquare = clickedSquare.getPieces();

                    //Creating panel to display options, and new list to contain radio buttons
                    final JPanel panel = new JPanel();
                    int listSize = piecesOnSquare.size();
                    JRadioButton[] pieceOptions = new JRadioButton[listSize];
                    int i = 0;

                    //Create new list of radio buttons representing available pieces and adding them to the panel
                    for (GuiPiece piece : piecesOnSquare) {
                        pieceOptions[i] = new JRadioButton(piece.toString());

                        //Adding the radiobutton to the panel
                        panel.add(pieceOptions[i]);
                        i++;
                    }

                    //Show the option pane
                    JOptionPane.showMessageDialog(this.gameBoard, panel, "Select pieces to mobilize", JOptionPane.PLAIN_MESSAGE);

                    //Getting the boolean values of the radiobuttons, and adding corresponding pieces to list of pieces being dragged
                    for (int j = 0; j < listSize; j++) {
                        if (pieceOptions[j].isSelected()) {
                            piecesOnTheMove.add(this.piecesOnSquare.get(j));
                        }
                    }

                    if (!piecesOnTheMove.isEmpty())
                        troopsAreBeingMobilized = true;

                    //Todo: Cleanup all lists used.

                } else { // Square clicked by user is outside of his control
                    System.out.println("You have no power over this city");
                    return;
                }
            } else { //Troops are not being mobilized
                this.clickedSquare.addPiecesToSquare(piecesOnTheMove, gameBoard.tribeInTurn.getColor());
                this.troopsAreBeingMobilized = false;
                piecesOnTheMove = new ArrayList<>();
                gameBoard.repaint();
            }

        }
    }

    private boolean reservesClicked(int xCoord){
        return xCoord > 605;
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
    public BoardSquare getClickedSquare(int clickX, int clickY){
        BoardSquare clickedSqr = null;

        for(int row=0; row<6; row++){
            for(int col=0; col<6; col++){
                if (boardMatrix[row][col].isClicked(clickX,clickY)){
                    clickedSqr = boardMatrix[row][col];
                    System.out.println("Square on " + clickedSqr.row + "," + clickedSqr.col + " was clicked");
                }
            }
        }
        return clickedSqr;
    }

    public GuiPiece getClickedPiece(int clickX, int clickY){
        GuiPiece selectedPiece = null ;
        for (GuiPiece piece : this.currentPlayerReserves.pieces){
            if (piece.isClicked(clickX,clickY)){
                selectedPiece = piece;
            }
        }
        return selectedPiece;
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

        if (deployingTroops) {
            this.clickedSquare = getClickedSquare(e.getX(),e.getY());

            BoardSquare releaseSqr = this.getClickedSquare(e.getX(), e.getY());

            List <GuiPiece> pieceList = new ArrayList<>();
            pieceOnDeployment.isDeployed = true;
            pieceList.add(pieceOnDeployment); //Fixme: Not ideal to create list of one element, but the method equiped to handle collisions only receives lists

            //TODO: If releaseSqr null, check if dropped back to reserves (not possible under rules)

            releaseSqr.addPiecesToSquare(pieceList, gameBoard.tribeInTurn.getColor());
            this.deployingTroops = false;
            pieceOnDeployment = null;
            gameBoard.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (deployingTroops){
            pieceOnDeployment.xPos = e.getX();
            pieceOnDeployment.yPos = e.getY();
            this.gameBoard.repaint();
        }
    }


}

package com.company.GUI;

import com.company.Pieces.GuiPiece;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class DragNDropListener implements MouseMotionListener, MouseListener{
    private List<GuiPiece> pieceList;
    private Board gameBoard;

    private GuiPiece dragPiece;
    private int xOffset;
    private int yOffset;

    /*
    Public constructor to set the game pieces & board accordingly
     */
    public DragNDropListener(List<GuiPiece> pieces, Board board){
        this.pieceList = pieces;
        this.gameBoard = board;
    }

    /*
    Called when mouse clicked. Checks if click coordinates correspond to a piece, and sets it as active if so.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();

        /*
        FIXME: For development purposes only. Fix by checking if click is on city ( or lone Piece) and open menu to
        give access to available troops <=> the user has has control over the city.
         */

        for(GuiPiece curPiece : pieceList){
            if (mouseOverPiece(curPiece,clickX,clickY)){
                this.xOffset = clickX - curPiece.getxPos();
                this.yOffset = clickY - curPiece.getyPos();
                this.dragPiece = curPiece;
                break;

            }
        }
    }

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

            this.dragPiece.setxPos(x);
            this.dragPiece.setyPos(y);
            this.gameBoard.repaint();
        }

    }

    /*
    Function to check if user clicked over a piece
     */
    private boolean mouseOverPiece(GuiPiece guiPiece, int x, int y) {
        return guiPiece.getxPos() <= x
                && guiPiece.getxPos()+guiPiece.getIconWidth() >= x
                && guiPiece.getyPos() <= y
                && guiPiece.getyPos()+guiPiece.getIconHeight() >= y;
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

package com.company.GUI;

import com.company.Main;
import com.company.Pieces.GuiPiece;
import com.company.Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//FixMe: Missing the turn switching functionality. Currently, white is the only playing color;

public class Board extends JPanel{
    private JFrame fram;

    public Piece.colors colorInTurn;
    private Image imgBackground;
    public static BoardSquare[][] boardMatrix = new BoardSquare[6][6];


    /**
     * Board main constructor. Called to create main game board, init graphics, and to set up the initial game positions
     */
    public Board(){
        //Create new Drag and Drop listener
        DragNDropListener mouseListener = new DragNDropListener(this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);

        initGraphics();
        setupNewGame();
    }

    /**
     * Method inherited from JFrame. Called when drawing new images or refreshing screen
     * @param g: Graphics, provided by Swing
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(this.imgBackground,0,0,null);

        //Fixme: May or may not be a more efficient method of drawing. Try only drawing city and topmost piece.
        for( int row=0; row<6 ;row++){
            for (int col=0; col<6; col++){
                for(GuiPiece piece: boardMatrix[row][col].getPieces()){
                    g.drawImage(piece.getIcon(),piece.getxPos(),piece.getyPos(),null);
                }
            }
        }

        //TODO: Find a way to get only the current player´s reserves.
        ReservesSquare currentPlayerReserves = Main.currentPlayer.playerTribe.getReservesSquare();

        for (GuiPiece piece : currentPlayerReserves.pieces){
            g.drawImage(piece.getIcon(),piece.getxPos(),piece.getyPos(),null);
        }

    }

    /**
     * Method to initialize graphic components i.e background image
     */
    private void initGraphics(){
        this.setLayout(null);

        //Drawing background
        this.imgBackground = new ImageIcon(getClass().getClassLoader().getResource("com/company/images/Board.png")).getImage();


        //Create application frame
        //FIXME: Window is drawn slightly smaller than the actual size of background image.
        fram = new JFrame();
        fram.setVisible(true);
        fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fram.add(this);
        fram.setSize(imgBackground.getWidth(null)+450,imgBackground.getHeight(null)+50);


    }

    /**
     * Creates 36 new BoardSquare objects and places them on the board matrix. Sets playing color to white
     */
    private void setupNewGame(){
        //Create 36 new boardsquares, and place them in the board matrix
        for( int row=0; row<6 ;row++){
            for (int col=0; col<6; col++){
                boardMatrix[row][col] = new BoardSquare(row,col);
            }
        }

        //Sets white as color in turn
        this.colorInTurn = Piece.colors.WHITE;

    }

    /**
     * Function to switch to next color´ turn.
     */
    public void nextTurn(){
        switch (colorInTurn){
            case WHITE:
                this.colorInTurn = Piece.colors.RED;
                break;
            case RED:
                this.colorInTurn = Piece.colors.BLUE;
                break;
            case BLUE:
                this.colorInTurn = Piece.colors.GREEN;
                break;
            case GREEN:
                this.colorInTurn = Piece.colors.WHITE;
                break;
        }
    }


    }


package com.company.GUI;

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
    public BoardSquare[][] boardMatrix = new BoardSquare[6][6];

    /**
     * Board main constructor. Called to create main game board, and to set up the initial game positions
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
        fram.setSize(imgBackground.getWidth(null),imgBackground.getHeight(null));


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

        //Creating a white horse
        //FIXME: This is just for testing purposes. Pieces need to be resized
        createAndAddPiece(Piece.colors.WHITE, Piece.types.HORSE, 2, true,0,0);
        createAndAddPiece(Piece.colors.BLUE, Piece.types.ARTILLERY, 3, true, 2,3);
        createAndAddPiece(Piece.colors.RED, Piece.types.INFANTRY, 4,true,4,3);
    }

    /**
     * Creates and paints a new piece based on parameters.
     * @param color: Enum declared in Pieces. Corresponds to each of 4 tribe colors
     * @param type: Enum declared in Pieces. Corresponds to each of the 3 possible piece types
     * @param power: Represents the numerical power a piece can have
     * @param isDeployed: Bool. Represents if a piece is deployed on the board, or currently on the army reserves
     * @param row: Initial row position
     * @param col: Initial col position
     */
    private void createAndAddPiece(Piece.colors color, Piece.types type, int power, boolean isDeployed, int row, int col) {
        Image pieceIcon = getIconForPiece(color,type);

        GuiPiece newPiece = new GuiPiece(color,type,power,isDeployed,row,col,pieceIcon);
        this.boardMatrix[row][col].addPieceToSquare(newPiece);

        //Calling repaint to update screen
        fram.repaint();
    }

    /**
     * Gets the icon for a specific piece using pieceColor and type, making use of naming conventions for icons
     * @param pieceColor: Enum declared in Pieces. Corresponds to each of 4 tribe colors
     * @param pieceType: Enum declared in Pieces. Corresponds to each of the 3 possible piece types + city
     * @return Image object with the image obtained for the specified piece
     */
    private Image getIconForPiece(Piece.colors pieceColor, Piece.types pieceType){
        String pathName = "";

        switch (pieceColor) {
            case RED:
                pathName += "r_";
                break;
            case BLUE:
                pathName += "b_";
                break;
            case GREEN:
                pathName += "g_";
                break;
            case WHITE:
                pathName += "w_";
                break;
        }

        switch (pieceType) {
            case HORSE:
                pathName += "Horse.png";
                break;
            case INFANTRY:
                pathName += "Infantry.png";
                break;
            case ARTILLERY:
                pathName += "Artilliery.png";
                break;
            case CITY:
                pathName += "City.png";
                break;
        }

        URL iconUrl = getClass().getClassLoader().getResource("com/company/images/" + pathName);
        return new ImageIcon(iconUrl).getImage();

        }

    /**
     * Recieves a piece, and determines in which row / col it is, and changes its parameters to match
     & Adds the piece to the corresponding square´s list of pieces.
     & Removes the piece from it´s previous square´s list of pieces.
     * @param piece: Piece object that is to be manipulated
     *  Fixme: Adapt to recieve lists of pieces instead of single units i.e use gameplay method
     */
    public void parseXYCoords(GuiPiece piece){
        int x = piece.getxPos();
        int y = piece.getyPos();

        //First, we check if the piece was already contained in another boardsquare. If so, remove from its list of pieces.
        int prevCol = piece.getColumn();
        int prevRow = piece.getRow();
        boardMatrix[prevRow][prevCol].removePieceFromSquare(piece);

        //Setting rows and columns based on the piece´s x,y coordinates.
        if (x > 0 && x < 100){
            piece.setColumn(0);
        } else if (x < 200){
            piece.setColumn(1);
        }else if (x < 300){
            piece.setColumn(2);
        }else if (x < 400){
            piece.setColumn(3);
        }else if (x < 500){
            piece.setColumn(4);
        }else if (x < 600){
            piece.setColumn(5);
        }

        if (y > 0 && y < 100){
            piece.setRow(0);
        } else if (y < 200){
            piece.setRow(1);
        }else if (y < 300){
            piece.setRow(2);
        }else if (y < 400){
            piece.setRow(3);
        }else if (y < 500){
            piece.setRow(4);
        }else if (y < 600){
            piece.setRow(5);
        }

        //Adds the piece to BoardSquare´s piece list
        boardMatrix[piece.getRow()][piece.getColumn()].addPieceToSquare(piece);
    }

    /**
     * Checks row / col parameters of the piece, and centers it into the square
     * @param piece
     * FIXME: Needs tweaking to center pieces correctly
     */
    public void centerPieceToSquare(GuiPiece piece){
        int pieceCol = piece.getColumn();
        int pieceRow = piece.getRow();

        switch (pieceCol){
            case 0:
                piece.setxPos(10);
                break;
            case 1:
                piece.setxPos(110);
                break;
            case 2:
                piece.setxPos(210);
                break;
            case 3:
                piece.setxPos(310);
                break;
            case 4:
                piece.setxPos(410);
                break;
            case 5:
                piece.setxPos(510);
                break;
        }

        switch (pieceRow){
            case 0:
                piece.setyPos(10);
                break;
            case 1:
                piece.setyPos(110);
                break;
            case 2:
                piece.setyPos(210);
                break;
            case 3:
                piece.setyPos(310);
                break;
            case 4:
                piece.setyPos(410);
                break;
            case 5:
                piece.setyPos(510);
                break;
        }

        System.out.println("I am in column " + pieceCol + " and row " + pieceRow);
    }

    /**
     * Function to switch to next color´ turn. Fixme: Missing function to clear the dragPiece of mouse listener
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


package pieces;

import static main.Main.sketch;

import java.util.List;

import board.Board;
import processing.core.PImage;
import processing.core.PVector;

public abstract class Piece {
    
    public static enum Team {
        BLACK,
        WHITE
    }

    public PVector square;
    protected int moveCount;

    // Dictionary of pieces to their FEN symbol
    //public static Map<String, Piece> fenSymbols = new HashMap<String, Piece>();
    

    public Team team;

    private PImage icon;

    public Piece(Team teamColor, String iconFilePath, char fenSymbol, PVector square) {
        this.team = teamColor;
        this.loadIcon(iconFilePath);
        this.moveCount = 0;
        this.square = square;
        //this.registerSymbol(fenSymbol);  // Dynamic FEN symbol registering doesn't work, but that's a todo
    }

    /**
     * Draws the image for the called piece on the board
     * @param squareWidth - Width of a single square on the board
     * @param squareHeight - Height of a single square on the board
     */
    public void draw(int squareWidth, int squareHeight) {

        // Scale a bit to make it look nicer
        float scaleDownFactor = .8f;
        // Move to center
        int shiftX = (int) (squareWidth * (1-scaleDownFactor)/2);
        int shiftY = (int) (squareHeight * (1-scaleDownFactor)/2);
        sketch.translate(shiftX, shiftY);
        sketch.scale(scaleDownFactor);
        
        // Scale to fit icon image in square  // Have to do this after the nice scaling to get translation to work nicely
        sketch.scale((float) squareWidth/this.icon.width, (float) squareHeight/this.icon.height);
        sketch.image(this.icon, 0, 0);
    }

    /**
     * Loads the image for the piece
     * @param iconFilePath - File path to image relative to 'res/'    Do not include .png    Image should be a .png file
     */
    private void loadIcon(String iconFilePath) {
        if (this.team == Team.BLACK) iconFilePath += 'B';
        else iconFilePath += 'W';
        iconFilePath += ".png";
        this.icon = sketch.loadImage("res/" + iconFilePath);
    }

    public void movePiece(Board gameBoard, PVector square) {
        this.moveCount++;
        this.square = square;
        gameBoard.board[(int) square.x][(int) square.y] = this;
    }

    /**
     * Registers a symbol to be associated to the piece in FEN notation
     * @param symbol - Character symbol to represent piece. Must have an upper and lower case
     */
    // protected void registerSymbol(char symbol) {
    //     fenSymbols.put(String.valueOf(symbol), this);
    // }

    public abstract List<PVector> generateMoves(Board gameBoard);  // TODO
}

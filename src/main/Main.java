package main;

import board.Board;
import processing.core.PApplet;

public class Main extends PApplet{
    
    /** Main processing sketch */
    public static Main sketch;

    /** Size of Window */
    private static final int WIDTH = 1000, HEIGHT = 800;


    private static Board gameBoard;

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        gameBoard = new Board();
    }

    public void draw() {
        background(33);
        gameBoard.displayBoard(100,0,HEIGHT, HEIGHT);
    }

    public void mousePressed() {
        background(255,0,0);
    }
    

    /**
     * Begin Sketch. Use 'setup()' for anything that normally would go here
     */
    public static void main(String[] args) {
        sketch = new Main();
        String[] processingArgs = {"App"};
        PApplet.runSketch(processingArgs, sketch);
    }
}




/*


Chess board
Has an array of arrays describing the board
will contain null if there is no piece there
or the piece which is on that spot

Each piece will be extended from a parent class
Parent class will have
    Image of piece
    Method to draw piece at current location
    Piece description (king, rook, etc)
    Team description (black, white, etc)
    GetValidMoves method
        Overriden by pieces
        Returns an array of valid moves indexes




*/
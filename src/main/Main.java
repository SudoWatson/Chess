package main;

import java.util.List;

import board.Board;
import pieces.Piece;
import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet{
    
    /** Main processing sketch */
    public static Main sketch;

    /** Size of Window */
    private static final int WIDTH = 1000, HEIGHT = 800;


    private static Board gameBoard;
    private static Piece pickedUpPiece;

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        gameBoard = new Board(100, 0, HEIGHT, HEIGHT);
        PVector test = new PVector(3,2);
        PVector temp = new PVector(3,2);
        System.out.println(test == temp);
    }

    public void draw() {
        background(33);
        gameBoard.displayBoard(pickedUpPiece != null ? pickedUpPiece.generateMoves(gameBoard) : null);

        if (pickedUpPiece != null) {
            translate(mouseX-gameBoard.squareWidth/2, mouseY-gameBoard.squareHeight/2);
            pickedUpPiece.draw(gameBoard.squareWidth, gameBoard.squareHeight);
        }


    }

    public void mousePressed() {
        PVector mousePos = gameBoard.getMousePosition();
        int x = (int) mousePos.x;
        int y = (int) mousePos.y;


        if (mousePos.x != -1 && mousePos.y != -1) {
            // Pick up a piece
            if (pickedUpPiece == null) {
                pickedUpPiece = gameBoard.board[x][y];

                gameBoard.board[x][y] = null;
            } else if (pickedUpPiece != null) {  // Drop a piece down
                List<PVector> possibleMoves = pickedUpPiece.generateMoves(gameBoard);
                // Cancel movement
                if (pickedUpPiece.square.x == mousePos.x && pickedUpPiece.square.y == mousePos.y) {
                    gameBoard.board[(int) mousePos.x][(int) mousePos.y] = pickedUpPiece;
                    pickedUpPiece = null;
                    return;
                }
                if (possibleMoves == null) return;  // No possible moves don't bother with the rest
                for (PVector move : pickedUpPiece.generateMoves(gameBoard)) {
                    if (move.x == mousePos.x && move.y == mousePos.y) {
                        pickedUpPiece.movePiece(gameBoard, move);
                        pickedUpPiece = null;
                    }
                }
            }
        }
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
package main;

import java.util.List;

import board.Board;
import move.Move;
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

    private static boolean matchTurn = true;
    private static final boolean WHITE = true;
    private static final boolean BLACK = false;


    private static final boolean DISABLE_TURN_SWITCH = false;


    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        surface.setTitle("Chess!");
        gameBoard = new Board(100, 0, HEIGHT, HEIGHT);
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

        if (gameBoard.verrifySquareInBounds(mousePos)) {
            // Pick up a piece
            if (pickedUpPiece == null) {
                if (gameBoard.getPiece(mousePos) != null) {
                    if ((matchTurn == WHITE && gameBoard.getPiece(mousePos).team == Piece.Team.WHITE) || (matchTurn == BLACK && gameBoard.getPiece(mousePos).team == Piece.Team.BLACK)) {
                        pickedUpPiece = gameBoard.getPiece(mousePos);
                        gameBoard.removePiece(mousePos);;
                    }
                }

            } else if (pickedUpPiece != null) {  // Drop a piece down
                List<Move> possibleMoves = pickedUpPiece.generateMoves(gameBoard);
                // Cancel movement
                if (pickedUpPiece.position.x == mousePos.x && pickedUpPiece.position.y == mousePos.y) {
                    gameBoard.board[(int) mousePos.x][(int) mousePos.y] = pickedUpPiece;
                    pickedUpPiece = null;
                    return;
                }
                if (possibleMoves == null) return;  // No possible moves don't bother with the rest
                // Move;
                for (Move move : pickedUpPiece.generateMoves(gameBoard)) {
                    PVector pos = move.getPosition();
                    if (pos.x == mousePos.x && pos.y == mousePos.y) {
                        //pickedUpPiece.movePiece(gameBoard, move);
                        move.Execute(gameBoard);
                        pickedUpPiece = null;
                        if (!DISABLE_TURN_SWITCH) matchTurn = !matchTurn;  // Moved, switch turns
                    }
                }
            }
        }
    }

    
    public void keyPressed() {
        if (keyCode == 70) {System.out.println(gameBoard.generateFEN());}
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
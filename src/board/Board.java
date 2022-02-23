package board;

import static main.Main.sketch;

import java.util.List;

import pieces.Pawn;
import pieces.Piece;
import pieces.Rook;
import processing.core.PVector;
import pieces.Queen;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;


public class Board {
    public Piece[][] board;  // rows * cols

    PVector position;

    public int squareWidth, squareHeight;
    public int rows, cols;

    public Board(int x, int y, int w, int h) {
        this(x, y, w, h, 8, 8);
    }

    public Board(int x, int y, int w, int h, int rows, int cols) {
        this.position = new PVector(x, y);
        
        this.board = new Piece[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.squareWidth = w/this.board.length;
        this.squareHeight = h/this.board[0].length;


        this.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    public void displayBoard(List<PVector> tintMoves) {
        sketch.pushMatrix();
        sketch.translate(this.position.x, this.position.y);

        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board.length; col++) {
                sketch.noStroke();
                // Set alternating colors
                if (tintMoves != null) {
                    sketch.fill(100,30,30);
                }
                if ((col+row) % 2 == 0) {  
                    sketch.fill(238, 238, 210);    // Color 1
                } else {
                    sketch.fill(118, 150, 86);  // Color 2
                }
                sketch.pushMatrix();
                sketch.translate(row*squareWidth, col*squareHeight);

                sketch.rect(0, 0, squareWidth, squareHeight);

                // Tint possible moves  // TODO make not so bad and loopy
                if (tintMoves != null) {
                    for (PVector move : tintMoves) {
                        if (move.x == row && move.y == col) {
                            sketch.fill(100,20,20,150);
                            sketch.rect(0, 0, squareWidth, squareHeight);
                        }
                    }
                }
                if (this.board[row][col] != null) {
                    
                    this.board[row][col].draw(squareWidth, squareHeight);
                }


                sketch.popMatrix();
            }
        }

        sketch.popMatrix();
    }

    /**
     * Loads a FEN notation string to load pieces onto positions in the board
     * @param fenNotation - FEN Notation to load
     */
    public void loadFEN(String fenNotation) {
        int row = 0;
        int col = 0;
        for (int i = 0; i < fenNotation.length(); i++) {
            String current = String.valueOf(fenNotation.charAt(i));

            // If number, put in that many empty spaces
            try {
                for (int spaces = Integer.parseInt(current); spaces > 0; spaces--) {  // Number, skip that many spaces
                    this.board[row][col] = null;                    
                    row++;
                }
                continue;  // Dont bother with checking the rest, just go onto the next piece in notation
            } catch (Exception e) {}  // Not a number

            if (current.equals("/")) {
                row = 0;
                col ++;
                continue;
            }


            // TODO Make this more dynamic for easy to add pieces
            Piece newPiece = null;

            switch(current) {
                case "p":
                    newPiece = new Pawn(Piece.Team.BLACK, new PVector(row, col));
                    break;
                case "P":
                    newPiece = new Pawn(Piece.Team.WHITE, new PVector(row, col));
                    break;
                case "r":
                    newPiece = new Rook(Piece.Team.BLACK, new PVector(row, col));
                    break;
                case "R":
                    newPiece = new Rook(Piece.Team.WHITE, new PVector(row, col));
                    break;
                case "n":
                    newPiece = new Knight(Piece.Team.BLACK, new PVector(row, col));
                    break;
                case "N":
                    newPiece = new Knight(Piece.Team.WHITE, new PVector(row, col));
                    break;
                case "b":
                    newPiece = new Bishop(Piece.Team.BLACK, new PVector(row, col));
                    break;
                case "B":
                    newPiece = new Bishop(Piece.Team.WHITE, new PVector(row, col));
                    break;
                case "q":
                    newPiece = new Queen(Piece.Team.BLACK, new PVector(row, col));
                    break;
                case "Q":
                    newPiece = new Queen(Piece.Team.WHITE, new PVector(row, col));
                    break;
                case "k":
                    newPiece = new King(Piece.Team.BLACK, new PVector(row, col));
                    break;
                case "K":
                    newPiece = new King(Piece.Team.WHITE, new PVector(row, col));
                    break;  
            }

            this.board[row][col] = newPiece;

            row++;

        }

        // TODO Load castling, en passant targets, and move counts
        // https://www.chess.com/terms/fen-chess
        // TODO Error handling on improperly sized FEN notation
    }
    

    public PVector getMousePosition() {

        int xPos = sketch.mouseX;
        int yPos = sketch.mouseY;

        int maxX = this.board.length * this.squareWidth;
        int maxY = this.board[0].length * this.squareHeight;

        xPos -= this.position.x;
        yPos -= this.position.y;
        
        xPos/=this.squareWidth;
        yPos/=this.squareHeight;

        if (sketch.mouseX < this.position.x) xPos = -1;
        if (sketch.mouseY < this.position.y) yPos = -1;

        if (sketch.mouseX > maxX + this.position.x) xPos = -1;
        if (sketch.mouseY > maxY + this.position.y) yPos = -1;

        return new PVector(xPos, yPos);
    }

}

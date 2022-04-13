package board;

import static main.Main.sketch;

import java.util.List;

import move.Move;
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
    public int rows, cols;  // TODO Getters

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


        //this.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");  // Default
        this.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/R3KBNR");
        //this.loadFEN("///4Q");
    }

    public void displayBoard(List<Move> tintMoves) {
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
                    for (Move move : tintMoves) {
                        PVector square = move.getPosition();
                        if (square.x == row && square.y == col) {
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









        /* TODO Delete Me */
        for (Piece[] row : this.board) {
            for (Piece potentialKing : row) {
                if (potentialKing instanceof King) {
                    this.inCheck(potentialKing);
                }
            }
        }
        /* */
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
    
    // TODO Doc
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

    // TODO Doc
    public String generateFEN() {
        String fenString = "";


        int gap = 0;
        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.cols; x++) {
                PVector checkSquare = new PVector(x, y);
                
                if (this.getPiece(checkSquare) == null) {
                    gap++;
                    continue;
                }
                if (gap != 0) fenString += gap;
                gap = 0;  // TODO Just comment this whole method

                Piece pieceToAdd = this.getPiece(checkSquare);

                // TODO Make more flexible
                if (pieceToAdd instanceof Bishop) fenString += (pieceToAdd.team == Piece.Team.WHITE ? 'B' : 'b');
                else if (pieceToAdd instanceof Rook) fenString += (pieceToAdd.team == Piece.Team.WHITE ? 'R' : 'r');
                else if (pieceToAdd instanceof Knight) fenString += (pieceToAdd.team == Piece.Team.WHITE ? 'N' : 'n');
                else if (pieceToAdd instanceof Queen) fenString += (pieceToAdd.team == Piece.Team.WHITE ? 'Q' : 'q');
                else if (pieceToAdd instanceof King) fenString += (pieceToAdd.team == Piece.Team.WHITE ? 'K' : 'k');
                else if (pieceToAdd instanceof Pawn) fenString += (pieceToAdd.team == Piece.Team.WHITE ? 'P' : 'p');

            }
            if (gap != 0) fenString += gap;
            gap = 0;
            fenString += '/';
        }


        return fenString;
    }

    // TODO Doc
    public boolean verrifySquareInBounds(PVector square) {
        return (square.y >= 0 && square.y < this.rows) && (square.x >= 0 && square.x < this.cols);
    }

    //TODO Doc
    public Piece getPiece(PVector square) {
        return this.board[(int) square.x][(int) square.y];
    }

    public void removePiece(PVector square) {
        this.board[(int) square.x][(int) square.y] = null;
    }

    public boolean inCheck(Piece king) {
        for (Piece[] row : this.board) {
            for (Piece piece : row) {
                if (piece == null) continue;
                if (piece.team == king.team) continue;
                for (Move move : piece.generateMoves(this)) {
                    if (this.getPiece(move.getPosition()) == king) {
                        System.out.println("Check");
                    }
                }
            }
        }
        return true;
    }

}

package pieces;

import static main.Main.sketch;

import java.util.ArrayList;
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

        // Load piece image
        this.icon = sketch.loadImage("res/" + iconFilePath);
        if (this.icon == null) {
            // Image not found, load missing image
            System.err.println("Chess image not found: " + "res/" + iconFilePath);
            this.icon = sketch.loadImage("res/missing.png");
        }

        if (this.icon == null) {
            System.err.println("Error 'missing' texture image not found!!");
        }
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

    public abstract List<PVector> generateMoves(Board gameBoard);


    // TODO Doc
    protected List <PVector> generateDiaganolMoves(Board gameBoard) {
        return generateDiaganolMoves(gameBoard, -1);
    }
    // TODO Doc
    protected List <PVector> generateDiaganolMoves(Board gameBoard, int maxDist) {
        List<PVector> possibleMoves = new ArrayList<PVector>();

        // Handle diagonal moves
        for (int j = -1; j <= 1; j+=2) {  // Do negative and positive
            for (int k = -1; k <= 1; k+=2) {  // Do negative and positive
                // Max Distance is either the maximum provided distance, or the maximum size of the board
                maxDist = (maxDist > 0 ? maxDist : ((gameBoard.cols > gameBoard.rows) ? gameBoard.cols : gameBoard.rows));
                for (int i = 1; i <= maxDist; i++) {  // Maximum to move is the minimum dimension of the board
                    PVector futureMove = new PVector(this.square.x + i*j, this.square.y + i*k);
                    if (!gameBoard.verrifySquareInBounds(futureMove)) break;  // Reached edge of board, move on
                    if (gameBoard.getPiece(futureMove) == null) {
                        possibleMoves.add(futureMove);
                        continue;
                    }
                    if (gameBoard.getPiece(futureMove).team != this.team) {
                        possibleMoves.add(futureMove);
                    }
                    break;  // Blocked, that's it for this line go to next line
                }
            }
        }
        return possibleMoves;
    }

    // TODO Doc
    protected List <PVector> generateStraightMoves(Board gameBoard) {
        return generateStraightMoves(gameBoard, -1);
    }
    // TODO Doc
    protected List <PVector> generateStraightMoves(Board gameBoard, int maxDist) {
        List<PVector> possibleMoves = new ArrayList<PVector>();

        // Handle verticle moves
        for (int j = -1; j <= 1; j+=2) {  // Do negative and positive
            maxDist = (maxDist > 0 ? maxDist : gameBoard.rows);  // Maximum to move vertically is the verticle height of board, or max input
            for (int i = 1; i <= maxDist; i++) {
                PVector futureMove = new PVector(this.square.x, this.square.y + i*j);
                if (!gameBoard.verrifySquareInBounds(futureMove)) break;  // Reached edge of board, move on
                if (gameBoard.getPiece(futureMove) == null) {
                    possibleMoves.add(futureMove);
                    continue;
                }
                if (gameBoard.getPiece(futureMove).team != this.team) {
                    possibleMoves.add(futureMove);
                }
                break;  // Blocked, that's it for this line go to next line
            }
        }

        // Handle horizontal moves moves
        for (int j = -1; j <= 1; j+=2) {  // Do negative and positive
            maxDist = (maxDist > 0 ? maxDist : gameBoard.cols);  // Maximum to move horizontall is the horizontal height of board, or max input
            for (int i = 1; i <= maxDist; i++) {
                PVector futureMove = new PVector(this.square.x + i*j, this.square.y);
                if (!gameBoard.verrifySquareInBounds(futureMove)) break;  // Reached edge of board, move on
                if (gameBoard.getPiece(futureMove) == null) {
                    possibleMoves.add(futureMove);
                    continue;
                }
                if (gameBoard.getPiece(futureMove).team != this.team) {
                    possibleMoves.add(futureMove);
                }
                break;  // Blocked, that's it for this line go to next line
            }
        }

        return possibleMoves;
    }

    protected void capture(Board gameBoard, PVector square) {
        gameBoard.removePiece(square);
    }

}

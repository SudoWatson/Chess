package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import processing.core.PVector;

public class King extends Piece {
    
    public King(Team teamColor, PVector square) {
        super(teamColor, "king", 'k', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        List<PVector> possibleMoves = new ArrayList<PVector>();

        possibleMoves.addAll(this.generateStraightMoves(gameBoard, 1));
        possibleMoves.addAll(this.generateDiaganolMoves(gameBoard, 1));
        //possibleMoves.addAll(this.generateCastleMoves(gameBoard));


        return possibleMoves;
    }
    

    private List<PVector> generateCastleMoves(Board gameBoard) {
        List<PVector> possibleMoves = new ArrayList<PVector>();
        // TODO Make this work somehow. Two pieces have to move at once


        /*
        Castling RULES-----
        King moves 2 spaces to the left or right. The respective rook jumps over the king to the other side
        king.x = king.oldX+-2
        rook.x = king.oldX+-1

        - King cannot have moved
        - Rook cannot have moved
        - TODO No pieces can be between
        - TODO King CANNOT be in check
        - TODO King CANNOT pass THROUGH check


        */


        if (this.moveCount == 0) {
            for (int x = 0; x < gameBoard.cols; x++) {
                PVector futureMove = new PVector(x, this.square.y);
                if (!gameBoard.verrifySquareInBounds(futureMove)) continue;  // Out of bounds
                Piece potentialRook = gameBoard.getPiece(futureMove);
                if (!(potentialRook instanceof Rook) || potentialRook.team != this.team) continue;  // Not a rook on our team


                if (potentialRook.moveCount == 0) {
                    int invert = (int) ((potentialRook.square.x - this.square.x) / Math.abs(potentialRook.square.x - this.square.x));
                    PVector rookNewMove = new PVector(this.square.x + invert, this.square.y);
                    potentialRook.movePiece(gameBoard, new PVector());
                }
            }
        }

        return possibleMoves;
    }
}

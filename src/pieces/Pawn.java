package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import move.Move;
import processing.core.PVector;

public class Pawn extends Piece {

    private int promotionRow;

    public Pawn(Team teamColor, PVector square, Board gameBoard) {
        super(teamColor, "pawn", 'p', square);
        promotionRow = (this.team == Team.BLACK ? gameBoard.rows : 0); // Determine row that pawn needs to get to to promote 
    }


    @Override
    public List<Move> generateCaptureMoves(Board gameBoard) {
        List<Move> possibleMoves = new ArrayList<Move>();
     
        int i = (this.team == Team.BLACK ? 1 : -1);
        
        // Handle diagonal attacks
        for (int j = -1; j <= 1; j+=2) {  // Diaganol is -1 and +1 x offset
            PVector futureMove = new PVector(this.position.x + j, this.position.y + i);
            if (!gameBoard.verrifySquareInBounds(futureMove)) continue;  // Place is out of bounds
            if (gameBoard.getPiece(futureMove) != null && gameBoard.getPiece(futureMove).team != this.team) {
                possibleMoves.add(new Move(this, futureMove));
            }
        }


        return possibleMoves;
    }


    @Override
    public List<Move> generateMoves(Board gameBoard) {
        List<Move> possibleMoves = new ArrayList<Move>();

        /**Inverts direction if black pawn */
        int i = (this.team == Team.BLACK ? 1 : -1);
        int maxDist = (this.moveCount == 0 ? 2 : 1);  // Can move twice if first move



        // Handle forward movement
        for (int j = 1; j <= maxDist; j++) {
            PVector futureMove = new PVector(this.position.x, this.position.y + j*i);
            if (gameBoard.verrifySquareInBounds(futureMove)) {  // Verify it doesn't go out of bounds
                if (gameBoard.getPiece(futureMove) == null) {  // Pawn can't capture in front
                possibleMoves.add(new Move(this, futureMove));
                }
            }
        }

        // Handle diagonal attacks
        possibleMoves.addAll(this.generateCaptureMoves(gameBoard));

        // TODO Handle en paccant

        // TODO Handle attack en paccant

        // TODO Handle advancement



        return possibleMoves;
    }
}

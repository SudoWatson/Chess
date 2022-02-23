package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import processing.core.PVector;

public class Pawn extends Piece {

    public Pawn(Team teamColor, PVector square) {
        super(teamColor, "pawn", 'p', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        List<PVector> possibleMoves = new ArrayList<PVector>();

        /**Inverts direction if black pawn */
        int i = (this.team == Team.BLACK ? 1 : -1);
        int maxDist = (this.moveCount == 0 ? 2 : 1);  // Can move twice if first move

        for (int j = 1; j <= maxDist; j++) {
            // TODO set up future move pvector and use that
            if (this.square.y + j*i >= 0 && this.square.y + j*i < gameBoard.cols) {
                possibleMoves.add(new PVector(this.square.x, this.square.y + j*i));
            }
        }
        
        // TODO attack somewhere not necessarily right here

        return possibleMoves;
    }
}

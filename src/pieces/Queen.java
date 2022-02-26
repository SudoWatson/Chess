package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import processing.core.PVector;

public class Queen extends Piece {
    
    public Queen(Team teamColor, PVector square) {
        super(teamColor, "queen", 'q', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        List<PVector> possibleMoves = new ArrayList<PVector>();
        possibleMoves.addAll(this.generateDiaganolMoves(gameBoard));
        possibleMoves.addAll(this.generateStraightMoves(gameBoard));

        return possibleMoves;
    }
}

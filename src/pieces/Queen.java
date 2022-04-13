package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import move.Move;
import processing.core.PVector;

public class Queen extends Piece {
    
    public Queen(Team teamColor, PVector square) {
        super(teamColor, "queen", 'q', square);
    }

    @Override
    public List<Move> generateMoves(Board gameBoard) {
        List<Move> possibleMoves = new ArrayList<Move>();
        possibleMoves.addAll(this.generateDiaganolMoves(gameBoard));
        possibleMoves.addAll(this.generateStraightMoves(gameBoard));

        return possibleMoves;
    }
}

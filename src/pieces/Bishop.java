package pieces;

import java.util.List;

import board.Board;
import processing.core.PVector;

public class Bishop extends Piece {
    

    public Bishop(Team teamColor, PVector square) {
        super(teamColor, "bishop", 'b', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        return this.generateDiaganolMoves(gameBoard);
    }
}

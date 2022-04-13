package pieces;

import java.util.List;

import board.Board;
import move.Move;
import processing.core.PVector;

public class Bishop extends Piece {
    

    public Bishop(Team teamColor, PVector square) {
        super(teamColor, "bishop", 'b', square);
    }

    @Override
    public List<Move> generateMoves(Board gameBoard) {
        return this.generateDiaganolMoves(gameBoard);
    }
}

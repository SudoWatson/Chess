package pieces;

import java.util.List;

import board.Board;
import move.Move;
import processing.core.PVector;

public class Rook extends Piece {
    
    public Rook(Team teamColor, PVector square) {
        super(teamColor, "rook", 'r', square);
    }

    @Override
    public List<Move> generateMoves(Board gameBoard) {
        return this.generateStraightMoves(gameBoard);
    }
}

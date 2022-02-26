package pieces;

import java.util.List;

import board.Board;
import processing.core.PVector;

public class Rook extends Piece {
    
    public Rook(Team teamColor, PVector square) {
        super(teamColor, "rook", 'r', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        return this.generateStraightMoves(gameBoard);
    }
}

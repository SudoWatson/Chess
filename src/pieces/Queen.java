package pieces;

import java.util.List;

import board.Board;
import processing.core.PVector;

public class Queen extends Piece {
    
    public Queen(Team teamColor, PVector square) {
        super(teamColor, "queen", 'q', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        // TODO Auto-generated method stub
        return null;
    }
}

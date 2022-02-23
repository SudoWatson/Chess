package pieces;

import java.util.List;

import board.Board;
import processing.core.PVector;

public class King extends Piece {
    
    public King(Team teamColor, PVector square) {
        super(teamColor, "king", 'k', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        // TODO Auto-generated method stub
        return null;
    }
}

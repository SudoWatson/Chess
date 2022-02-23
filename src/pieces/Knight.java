package pieces;

import java.util.List;

import board.Board;
import processing.core.PVector;

public class Knight extends Piece{
    
    public Knight(Team teamColor, PVector square) {
        super(teamColor, "knight", 'n', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        // TODO Auto-generated method stub
        return null;
    }
}

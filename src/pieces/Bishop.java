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
        // TODO Auto-generated method stub
        return null;
    }
}

package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import processing.core.PVector;

public class King extends Piece {
    
    public King(Team teamColor, PVector square) {
        super(teamColor, "king", 'k', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        List<PVector> possibleMoves = new ArrayList<PVector>();

        possibleMoves.addAll(this.generateStraightMoves(gameBoard, 1));
        possibleMoves.addAll(this.generateDiaganolMoves(gameBoard, 1));

        // TODO Castle

        return possibleMoves;
    }
}

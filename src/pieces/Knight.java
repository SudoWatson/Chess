package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import processing.core.PVector;

public class Knight extends Piece{
    
    public Knight(Team teamColor, PVector square) {
        super(teamColor, "knight", 'n', square);
    }

    @Override
    public List<PVector> generateMoves(Board gameBoard) {
        List<PVector> possibleMoves = new ArrayList<PVector>();
        
        for (int x=-2; x <= 2; x++) {  
            for (int y = -2; y <= 2; y++) {
                if (Math.abs(x) == Math.abs(y)) {  // Either 2,2 or 1,1 which are inllegal. Don't add move
                    continue;
                }
                if (x == 0 || y == 0) {  // Not moving in one of the directions, don't add move
                    continue;
                }

                PVector futureMove = new PVector(this.square.x+x,this.square.y+y);
                if (!gameBoard.verrifySquareInBounds(futureMove)) continue;  // Out of bounds, don't add move
                if (gameBoard.getPiece(futureMove) == null || gameBoard.getPiece(futureMove).team != this.team) {
                    possibleMoves.add(futureMove);  // In bounds && not on same team, add move
                }
            }
        }




        return possibleMoves;
    }
}

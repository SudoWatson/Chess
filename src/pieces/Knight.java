package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import move.Move;
import processing.core.PVector;

public class Knight extends Piece{
    
    public Knight(Team teamColor, PVector square) {
        super(teamColor, "knight", 'n', square);
    }

    @Override
    public List<Move> generateMoves(Board gameBoard) {
        List<Move> possibleMoves = new ArrayList<Move>();
        
        for (int x=-2; x <= 2; x++) {  
            for (int y = -2; y <= 2; y++) {
                if (Math.abs(x) == Math.abs(y)) {  // Either 2,2 or 1,1 which are inllegal. Don't add move
                    continue;
                }
                if (x == 0 || y == 0) {  // Not moving in one of the directions, don't add move
                    continue;
                }

                PVector futureMove = new PVector(this.position.x+x,this.position.y+y);
                if (!gameBoard.verrifySquareInBounds(futureMove)) continue;  // Out of bounds, don't add move
                if (gameBoard.getPiece(futureMove) == null || gameBoard.getPiece(futureMove).team != this.team) {
                        possibleMoves.add(new Move(this, futureMove));  // In bounds && not on same team, add move
                }
            }
        }




        return possibleMoves;
    }
}

package move;

import board.Board;
import pieces.Piece;
import processing.core.PVector;

public class Promotion extends Move {

    Move pawnMove;

    public Promotion(Piece pawn, PVector pos) {
        super(pawn, pos);
        pawnMove = new Move(pawn, pos);
    }

    @Override
    public void Execute(Board gameBoard) {
        pawnMove.Execute(gameBoard);
        //TODO Actually promote
        //TODO Oh also implement this
    }
    
}

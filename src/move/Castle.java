package move;

import board.Board;
import pieces.Piece;
import processing.core.PVector;
// TODO Rename to 'CastleMove'
public class Castle extends Move {

    Move kingMove;
    Move rookMove;

    public Castle(Piece king, PVector kingPos, Piece rook, PVector rookPos) {
        super(king, kingPos);  // For displaying the possible move
        kingMove = new Move(king, kingPos);
        rookMove = new Move(rook, rookPos);
    }

    @Override
    public void Execute(Board gameBoard) {
        kingMove.Execute(gameBoard);
        rookMove.Execute(gameBoard);
    }
    
}

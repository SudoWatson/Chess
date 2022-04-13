package move;

import board.Board;
import pieces.Piece;
import processing.core.PVector;

public class Move {

    PVector position;
    Piece piece;

    public Move(Piece piece, PVector pos) {
        this.piece = piece;
        this.position = pos;
    }

    // TODO Doc    -   Executes the move, moves the move
    public void Execute(Board gameBoard) {
        int x = (int) this.position.x, y = (int) this.position.y;
        gameBoard.removePiece(this.piece.getPosition());
        if (gameBoard.board[x][y] != null) gameBoard.board[x][y].capture(gameBoard);
        gameBoard.board[x][y] = this.piece;
        this.piece.movePiece(this.position);
    }

    public PVector getPosition() {
        return this.position;
    }

}

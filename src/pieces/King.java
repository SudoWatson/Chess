package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import move.Castle;
import move.Move;
import processing.core.PVector;

public class King extends Piece {
    
    public King(Team teamColor, PVector square) {
        super(teamColor, "king", 'k', square);
    }

    @Override
    public List<Move> generateCaptureMoves(Board gameBoard) {
        List<Move> possibleMoves = new ArrayList<Move>();

        possibleMoves.addAll(this.generateStraightMoves(gameBoard, 1));
        possibleMoves.addAll(this.generateDiaganolMoves(gameBoard, 1));
     
        return possibleMoves;
    }

    @Override
    public List<Move> generateMoves(Board gameBoard) {
        List<Move> possibleMoves = new ArrayList<Move>();

        possibleMoves.addAll(this.generateCaptureMoves(gameBoard));
        possibleMoves.addAll(this.generateCastleMoves(gameBoard));

        return possibleMoves;
    }
    

    private List<Move> generateCastleMoves(Board gameBoard) {
        List<Move> possibleMoves = new ArrayList<Move>();
        if (this.moveCount != 0) return possibleMoves;  // Can't castle if already moved
        if (gameBoard.inCheck(this.position, Piece.oppositeTeam(this.team)).size() != 0) return possibleMoves;  // Can't castle out of check


        for (int dir = -1; dir <= 1; dir += 2) {
            for (int offset = 1; offset < gameBoard.cols; offset++) {
                PVector squareToCheck = new PVector(this.position.x + (offset * dir), this.position.y);
                if (!gameBoard.verrifySquareInBounds(squareToCheck)) break;  // Out of bounds, stop checking
                Piece potentialRook = gameBoard.getPiece(squareToCheck);
                if (gameBoard.inCheck(squareToCheck, Piece.oppositeTeam(this.team)).size() != 0) { break; } // Square in check, can't castle

                if (potentialRook == null) continue;  // Empty square not in check, keep going
                if (!(potentialRook instanceof Rook) || potentialRook.team != this.team) break;  // Piece, but not a rook on our team
                if (potentialRook.moveCount != 0) break;  // Rook has already moved before, can't castle with it
                
                PVector rookNewMove = new PVector(this.position.x + dir, this.position.y);
                PVector kingNewMove = new PVector(this.position.x + (2 * dir), this.position.y);
                possibleMoves.add(new Castle(this, kingNewMove, potentialRook, rookNewMove));

            }
        }
        return possibleMoves;
    }
}

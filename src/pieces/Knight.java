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
        /*
        

        ---X-X--
        --X---X--
            0
        --X---X--
        ---X-X---

        (x+-2,y+-1)
        (x+-1,y+-2)
        -2 - +2
        Except when they are the same
        or 0
        */



        // For loop from -1 to 1 by step 2, then do that again, then mult those by x * y. Removes the chance for 0, should be less loops?
        for (int x=-2; x <= 2; x++) {  
            for (int y = -2; y <= 2; y++) {
                if (Math.abs(x) == Math.abs(y)) {
                    continue;
                }
                if (x == 0 || y == 0) {
                    continue;
                }
            }
        }




        return null;
    }
}

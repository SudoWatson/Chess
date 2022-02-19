package board;

import static main.Main.sketch;

import pieces.Piece;

public class Board {
    Piece[][] board;  // rows * cols

    public Board() {
        this(8,8);
    }

    public Board(int sizeX, int sizeY) {
        this.board = new Piece[sizeX][sizeY];
    }

    public void displayBoard(int x, int y, int width, int height) {
        sketch.pushMatrix();
        sketch.translate(x, y);

        int squareWidth = width/this.board.length;
        int squareHeight = height/this.board[0].length;
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board.length; col++) {
                sketch.noStroke();
                // Set alternating colors
                if ((col+row) % 2 == 0) {  
                    sketch.fill(0);
                } else {
                    sketch.fill(255);
                }
                sketch.pushMatrix();
                sketch.translate(row*squareWidth, col*squareHeight);

                sketch.rect(0, 0, squareWidth, squareHeight);

                sketch.popMatrix();
            }
        }

        sketch.popMatrix();
    }

    
}

package pieces;

import static main.Main.sketch;

import processing.core.PImage;

public abstract class Piece {
    
    static enum Team {
        BLACK,
        WHITE
    }
    

    public Team team;

    private PImage icon;

    public Piece() {
    }

    public void draw(int width, int height) {
        sketch.image(this.icon, width, height);
    }

    public abstract void generateMoves();
}

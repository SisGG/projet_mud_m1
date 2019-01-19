import java.io.Serializable;

public class Piece implements Serializable {


    private int coordonneeX;
    private int coordonneeY;

    public Piece(int coordonneX, int coordonneeY) {
        this.coordonneeX = coordonneX;
        this.coordonneeY = coordonneeY;
    }

    public int getCoordonneeX() {
        return this.coordonneeX;
    }

    public int getCoordonneeY() {
        return this.coordonneeY;
    }

    public boolean equals(Piece piece) {
        return this.coordonneeX == piece.coordonneeX && this.coordonneeY == piece.coordonneeY;
    }

    public String toString() {
        return "Piece[" + this.coordonneeX + ":" + this.coordonneeY + "]";
    }
}
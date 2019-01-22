import java.io.Serializable;

public class Piece implements Serializable {


    private int coordonneeX;
    private int coordonneeY;

    Piece(int coordonneX, int coordonneeY) {
        this.coordonneeX = coordonneX;
        this.coordonneeY = coordonneeY;
    }

    int getCoordonneeX() {
        return this.coordonneeX;
    }

    int getCoordonneeY() {
        return this.coordonneeY;
    }

    boolean equals(Piece piece) {
        return this.coordonneeX == piece.coordonneeX && this.coordonneeY == piece.coordonneeY;
    }

    public String toString() {
        return "Piece[" + this.coordonneeX + ":" + this.coordonneeY + "]";
    }
}
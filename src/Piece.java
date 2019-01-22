/* ****************************************************************************
 *
 * Name File : src/Piece.java
 * Authors   : OLIVIER Thomas
 *             BOURAKADI Reda
 *             LAPEYRADE Sylvain
 *
 * Location  : UPSSITECH - University Paul Sabatier
 * Date      : Janvier 2019
 *
 *                        This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *
 * ***************************************************************************/

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
import java.io.Serializable;

/******************************************************************************
 * file     : src/Piece.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 4.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 25 Mars 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class Piece implements Serializable {


    private int coordonneeX;
    private int coordonneeY;

    /**
     * Constructeur de la classe Piece.
     *
     * @param coordonneX  Entier correspendant à la coordonnée X de la piece.
     * @param coordonneeY Entier correspendant à la coordonnée Y de la piece.
     */
    Piece(int coordonneX, int coordonneeY) {
        this.coordonneeX = coordonneX;
        this.coordonneeY = coordonneeY;
    }

    /**
     * Récupérer la coordonnée X de la pièce.
     *
     * @return Renvoie l'entier qui représente la coordonnée X.
     */
    int getCoordonneeX() {
        return this.coordonneeX;
    }

    /**
     * Récupérer la coordonnée Y de la pièce.
     *
     * @return Renvoie l'entier qui représente la coordonnée Y.
     */
    int getCoordonneeY() {
        return this.coordonneeY;
    }

    /**
     * Indique si la Piece passé en paramètre est identique a la Piece courante.
     *
     * @param piece Piece à comparer.
     * @return Renvoie true si les deux Piece sont considéré comme identique, false sinon.
     */
    boolean equals(Piece piece) {
        return this.coordonneeX == piece.coordonneeX && this.coordonneeY == piece.coordonneeY;
    }

    /**
     * Renvoie une chaine de caractère représentant la Piece.
     *
     * @return Renvoie une chaine de caractère représentant la Piece.
     */
    public String toString() {
        return "Piece[" + this.coordonneeX + ":" + this.coordonneeY + "]";
    }
}

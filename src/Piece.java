import java.io.Serializable;

/******************************************************************************
 * file     : src/Piece.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 1.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 30 Janvier 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class Piece implements Serializable {


    private int coordonneeX;
    private int coordonneeY;

    /**
     * Constructeur de la classe Piece
     * @param coordonneX entier correspendant à la coordonnée X de la piece
     * @param coordonneeY entier correspendant à la coordonnée Y de la piece
     */
    Piece(int coordonneX, int coordonneeY) {
        this.coordonneeX = coordonneX;
        this.coordonneeY = coordonneeY;
    }

    /**
     * Récupérer la coordonnée X de la pièce
     * @return renvoie l'entier qui représente la coordonnée X
     */
    int getCoordonneeX() {
        return this.coordonneeX;
    }

    /**
     * Récupérer la coordonnée Y de la pièce
     * @returnrenvoie l'entier qui représente la coordonnée Y
     */
    int getCoordonneeY() {
        return this.coordonneeY;
    }

    /**
     * @param piece l'objet piece que l'on veut comparer
     * @return vrai ou faux pour savoir si les deux pièces ont les mêmes coordonnées
     */
    boolean equals(Piece piece) {
        return this.coordonneeX == piece.coordonneeX && this.coordonneeY == piece.coordonneeY;
    }

    /**
     * Afficher les détails de l'instance de Piece
     * @return chaine de caractère représentant la pièce et ses coordonnées
     */
    public String toString() {
        return "Piece[" + this.coordonneeX + ":" + this.coordonneeY + "]";
    }
}
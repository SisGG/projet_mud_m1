import java.io.Serializable;

/******************************************************************************
 * file     : src/EtreVivant.java
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
abstract class EtreVivant implements Serializable {

    String nomEtreVivant;
    private Piece pieceActuelle;
    private int pointDeVieMax;
    private int pointDeVieActuel;

    /**
     * Constructeur de la classe EtreVivant.
     *
     * @param nomEtreVivant Chaine de caractère de l'EtreVivant.
     * @param pointDeVie    Points de vie de l'EtreVivant.
     */
    EtreVivant(String nomEtreVivant, int pointDeVie) {
        this.nomEtreVivant = nomEtreVivant;
        this.pointDeVieMax = pointDeVie;
        this.pointDeVieActuel = pointDeVie;
        this.pieceActuelle = null;
    }

    /**
     * Constructeur de la classe EtreVivant.
     *
     * @param nomEtreVivant Chaine de caractère de l'EtreVivant.
     * @param pointDeVieMax Points de vie de l'EtreVivant.
     * @param piece         Piece a associé à l'EtreVivant.
     */
    EtreVivant(String nomEtreVivant, int pointDeVieMax, Piece piece) {
        this.nomEtreVivant = nomEtreVivant;
        this.pointDeVieMax = pointDeVieMax;
        this.pointDeVieActuel = pointDeVieMax;
        this.pieceActuelle = piece;
    }

    /**
     * Récupère la piece actuelle de l'EtreVivant.
     *
     * @return Renvoie la Piece actuelle de l'EtreVivant.
     */
    Piece getPieceActuelle() {
        return this.pieceActuelle;
    }

    /**
     * Définie la piece actuelle de l'EtreVivant.
     *
     * @param pieceActuelle Piece à définir.
     */
    void setPieceActuelle(Piece pieceActuelle) {
        this.pieceActuelle = pieceActuelle;
    }

    /**
     * Augmente un point de vie à l'EtreVivant.
     */
    synchronized void augmenterPointDeVie() {
        this.pointDeVieActuel += 1;
        this.pointDeVieMax += 1;
    }

    /**
     * Décrémente un point de vie à l'EtreVivant.
     */
    synchronized void perdrePointDeVie() {
        this.pointDeVieActuel -= 1;
    }

    /**
     * Initialise les points de vie de l'EtreVivant au maximum.
     */
    synchronized void regagnerPointDeVieMax() {
        this.pointDeVieActuel = this.pointDeVieMax;
    }

    /**
     * Récupère les points de vies actuels de l'EtreVivant.
     *
     * @return Renvoie les points de vie actuels de l'EtreVivant?
     */
    int getPointDeVie() {
        return pointDeVieActuel;
    }

    /**
     * Récupère le nom de l'être  vivant
     *
     * @return Renvoie la chaine de caractère du nom de l'EtreVivant.
     */
    String getNom() {
        return nomEtreVivant;
    }

    /**
     * Indique si l'EtreVivant passé en paramètre est identique à l'EtreVivant courant.
     *
     * @param etreVivant EtreVivant a comparé.
     * @return Renvoie true si les deux EtreVivant sont considérées comme identiques, false sinon.
     */
    boolean equals(EtreVivant etreVivant) {
        return this.nomEtreVivant.equals(etreVivant.getNom());
    }

}

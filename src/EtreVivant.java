import java.io.Serializable;

/******************************************************************************
 * file     : src/EtreVivant.java
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
abstract class EtreVivant implements Serializable {

    protected String nomEtreVivant;
    private Piece pieceActuelle;
    private int pointDeVieMax;
    private int pointDeVieActuel;

    EtreVivant(String nomEtreVivant, int pointDeVie) {
        this.nomEtreVivant = nomEtreVivant;
        this.pointDeVieMax = pointDeVie;
        this.pointDeVieActuel = pointDeVie;
        this.pieceActuelle = null;
    }

    EtreVivant(String nomEtreVivant, int pointDeVieMax, Piece piece) {
        this.nomEtreVivant = nomEtreVivant;
        this.pointDeVieMax = pointDeVieMax;
        this.pointDeVieActuel = pointDeVieMax;
        this.pieceActuelle = piece;
    }

    /**
     * Définie la piece actuelle de l'etre vivant.
     */
    void setPieceActuelle(Piece pieceActuelle) {
        this.pieceActuelle = pieceActuelle;
    }

    /**
     * Récupère la piece actuelle de l'etre vivant.
     * @return Renvoie la piece actuelle de l'etre vivant.
     */
    Piece getPieceActuelle() {
        return this.pieceActuelle;
    }

    void augmenterPointDeVie() {
        this.pointDeVieActuel += 1;
        if (this.pointDeVieActuel > pointDeVieMax)
            pointDeVieMax = pointDeVieActuel;
    }

    void perdrePointDeVie() {
        this.pointDeVieActuel -= 1;
    }

    void regagnerPointDeVieMax() {
        this.pointDeVieActuel = this.pointDeVieMax;
    }

    int getPointDeVie() {
        return pointDeVieActuel;
    }

    String getNom() {
        return nomEtreVivant;
    }
}

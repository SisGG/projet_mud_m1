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

    private int pointDeVieMax;
    private int pointDeVieActuel;
    private Piece pieceActuelle;
    protected String nomEtreVivant;

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

    void augmenterPointDeVieActuel(int nbPointDevieEnPlus) {
        this.pointDeVieActuel += nbPointDevieEnPlus;
        if (this.pointDeVieActuel > pointDeVieMax)
            pointDeVieMax = pointDeVieActuel;
    }

    void perdrePointDeVieActuel(int nbPointDeVieEnMoins){
        this.pointDeVieActuel -= nbPointDeVieEnMoins;
    }

    void regagnerPointDeVieMax(){
        this.pointDeVieActuel = this.pointDeVieMax;
    }

    int getPointDeVieActuel() {
        return pointDeVieActuel;
    }

    String getNomEtreVivant() {
        return nomEtreVivant;
    }
}

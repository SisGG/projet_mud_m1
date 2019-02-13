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

    /**
     * Constructeur de la classe EtreVivant
     * @param nomEtreVivant nom de l'être vivant
     * @param pointDeVie points de vie de l'être vivant
     * */
    EtreVivant(String nomEtreVivant, int pointDeVie) {
        this.nomEtreVivant = nomEtreVivant;
        this.pointDeVieMax = pointDeVie;
        this.pointDeVieActuel = pointDeVie;
        this.pieceActuelle = null;
    }

    /**
    * Surcharge du constructeur de la classe EtreVivant pour iniitaliser le paramètre pieceActuelle
    * @param nomEtreVivant nom de l'être vivant
    * @param pointDeVieMax points de vie de l'être vivant
    * @param piece piece où se trouve l'être vivant
    * */
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

    /**
     * Augmente les points de vies de l'être vivant à l'issu d'un combat gagné
     * */
    synchronized void augmenterPointDeVie() {
        this.pointDeVieActuel += 1;
        if (this.pointDeVieActuel > pointDeVieMax)
            pointDeVieMax = pointDeVieActuel;
    }

    /**
     * Décrémente de 1 les points de vie de l'être vivant*/
    synchronized void perdrePointDeVie() {
        this.pointDeVieActuel -= 1;
    }

    /**
     * Remet au maximum les points de vie de l'être vivant
     */
    synchronized void regagnerPointDeVieMax() {
        this.pointDeVieActuel = this.pointDeVieMax;
    }

    /**
     * Récupère les points de vies actuels  de  l'être  vivant
     * @return renvoie les points de vie actuels de l'être  vivant
     */
    int getPointDeVie() {
        return pointDeVieActuel;
    }

    /**
     * Récupère le nom de  l'être  vivant
     * @return renvoie le nom de l'être vivant
     */
    String getNom() {
        return nomEtreVivant;
    }

    /**
     * Vérifie si l'être vivant passé en paramètres  est équivalent à l'être vivant appelant  de la foncition
     * @param etreVivant être vivant à comparer avec l'appelant
     * @return résultat de la comparaison
     */
    boolean equals(EtreVivant etreVivant) {
        return this.nomEtreVivant.equals(etreVivant.getNom());
    }
}

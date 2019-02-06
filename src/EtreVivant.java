import java.io.Serializable;

abstract class EtreVivant implements Serializable {

    private int pointDeVieMax;
    private int pointDeVieActuel;
    private Piece pieceActuelle;
    private String nomEtreVivant;

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

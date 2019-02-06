import java.io.Serializable;

public abstract class EtreVivant implements Serializable {

    protected int pointDeVieMax;
    protected int pointDeVieActuel;
    protected Piece pieceActuelle;

    public EtreVivant(int pointDeVieMax, Piece piece) {
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

    public int getPointDeVieActuel() {
        return pointDeVieActuel;
    }

    private void augmenterPointDeVieActuel(int pointDevieEnPlus) {
        this.pointDeVieActuel += pointDeVieActuel;
        if (this.pointDeVieActuel > pointDeVieMax)
            pointDeVieMax = pointDeVieActuel;
    }

     public void regagnerPointDeVieMax(){
        this.pointDeVieActuel = this.pointDeVieMax;
    }
}

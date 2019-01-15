public class Personnage {
    private String nomPersonnage;
    private Piece pieceActuelle;

    public Personnage(String nomPersonnage) {
        this.nomPersonnage = nomPersonnage;
        this.pieceActuelle = null;
    }

    public void setPieceActuelle(Piece pieceActuelle) {
        this.pieceActuelle = pieceActuelle;
    }

    public Piece getPieceActuelle() {
        return this.pieceActuelle;
    }

    public String getNomPersonnage() {
        return this.nomPersonnage;
    }
}

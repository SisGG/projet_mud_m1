import java.io.Serializable;

public class Personnage implements Serializable {

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

    public String toString() {
        return "Personnage[" + this.nomPersonnage + "]";
    }

    public void notifier(String message){};
}

import java.io.Serializable;

public class Personnage implements Serializable {

    private String nomPersonnage;
    private Piece pieceActuelle;

    /**
     * Constructeur de la classe Personnage.
     * @param nomPersonnage Nom du personnage.
     */
    public Personnage(String nomPersonnage) {
        this.nomPersonnage = nomPersonnage;
        this.pieceActuelle = null;
    }

    public void setPieceActuelle(Piece pieceActuelle) {
        this.pieceActuelle = pieceActuelle;
    }

    /**
     * Récupère la piece actuelle du personnage.
     * @return Renvoie la piece du personnage.
     */
    public Piece getPieceActuelle() {
        return this.pieceActuelle;
    }

    /**
     * Récupère le nom du personnage.
     * @return Renvoie la chaine de caractère du nom de personnage.
     */
    public String getNomPersonnage() {
        return this.nomPersonnage;
    }

    /**
     * Récupère la chaine de caractère définissant l'objet.
     * @return Renvoie une chaine de caractère.
     */
    public String toString() {
        return "Personnage[" + this.nomPersonnage + "]";
    }
}
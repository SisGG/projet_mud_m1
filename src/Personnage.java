import java.io.Serializable;

public class Personnage implements Serializable {

    private String nomPersonnage;
    private Piece pieceActuelle;
    private ServeurNotification serveurNotification;

    /**
     * Constructeur de la classe Personnage.
     * @param nomPersonnage Nom du personnage.
     */
    Personnage(String nomPersonnage) {
        this.nomPersonnage = nomPersonnage;
        this.pieceActuelle = null;
    }

    void setPieceActuelle(Piece pieceActuelle) {
        this.pieceActuelle = pieceActuelle;
    }

    /**
     * Récupère la piece actuelle du personnage.
     * @return Renvoie la piece du personnage.
     */
    Piece getPieceActuelle() {
        return this.pieceActuelle;
    }

    /**
     * Récupère le nom du personnage.
     * @return Renvoie la chaine de caractère du nom de personnage.
     */
    String getNomPersonnage() {
        return this.nomPersonnage;
    }

    void setServeurNotification(ServeurNotification serveurNotification) {
        this.serveurNotification = serveurNotification;
    }

    ServeurNotification getServeurNotification() {
        return this.serveurNotification;
    }

    /**
     * Récupère la chaine de caractère définissant l'objet.
     * @return Renvoie une chaine de caractère.
     */
    public String toString() {
        return "Personnage[" + this.nomPersonnage + "]";
    }

    boolean equals(Personnage personnage) {
        return this.nomPersonnage.equals(personnage.getNomPersonnage());
    }
}
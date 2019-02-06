import java.util.HashMap;
import java.util.Vector;

public class Donjon {

    private int tailleDonjon;
    private HashMap<String,Personnage> listePersonnage;
    private Piece[][] donjon;

    public Donjon(int tailleDonjon) {
        this.tailleDonjon = tailleDonjon;
        this.listePersonnage = new HashMap<>();
        this.donjon = new Piece[tailleDonjon][tailleDonjon];
        this.genererDonjon(tailleDonjon);
    }

    /**
     * Génère les pièces du donjon en fonction de la taille du donjon.
     * @param taille Taille du donjon.
     */
    private void genererDonjon(int taille) {
        for ( int i = 0; i < taille; i++ ) {
            for ( int j = 0; j < taille; j++ ) {
                Piece piece = new Piece(i,j);
                this.donjon[i][j] = piece;
            }
        }
    }

    /**
     * Récupère la pièce de départ du donjon.
     * @return Renvoie une pièce.
     */
    public Piece getPieceDepart() {
        return this.donjon[0][0];
    }

    public Vector<Personnage> getPersonnageMemePiece(Personnage personnage) {
        Vector<Personnage> listePersonnage = new Vector<>();
        for (Personnage personnageCourant : this.listePersonnage.values() ) {
            if ( personnage.getPieceActuelle().equals(personnageCourant.getPieceActuelle()) ) {
                listePersonnage.add(personnageCourant);
            }
        }
        return listePersonnage;
    }

    public Piece getPiece(Piece piece, String direction) {
        int coordonneeX = piece.getCoordonneeX();
        int coordonneeY = piece.getCoordonneeY();
        switch( direction ) {
            case "Nord":
                if ( coordonneeX + 1 < this.tailleDonjon ) {
                    return this.donjon[coordonneeX+1][coordonneeY];
                }
                break;
            case "Est":
                if ( coordonneeY + 1 < this.tailleDonjon ) {
                    return this.donjon[coordonneeX][coordonneeY+1];
                }
                break;
            case "Sud":
                if ( coordonneeX - 1 >= 0 ) {
                    return this.donjon[coordonneeX-1][coordonneeY];
                }
                break;
            case "Ouest":
                if ( coordonneeY - 1 >= 0 ) {
                    return this.donjon[coordonneeX][coordonneeY-1];
                }
                break;
        }
        return null;
    }

    public boolean ajouterPersonnage(Personnage personnage) {
        if ( !this.listePersonnage.containsKey(personnage.getNomPersonnage()) ) {
            this.listePersonnage.put(personnage.getNomPersonnage(), personnage);
            return true;
        }
        return false;
    }

    public void supprimerPersonnage(Personnage personnage) {
        if ( this.listePersonnage.containsKey(personnage.getNomPersonnage()) ) {
            this.listePersonnage.remove(personnage.getNomPersonnage());
        }
    }

    public Personnage recuperePersonnage(String nomPersonnage) {
        if ( this.listePersonnage.containsKey(nomPersonnage) ) {
            return this.listePersonnage.get(nomPersonnage);
        }
        return null;
    }

    public void associerServeurNotificationPersonnage(Personnage personnage, ServeurNotification serveurNotification) {
        if ( this.listePersonnage.containsKey(personnage.getNomPersonnage()) ) {
            this.listePersonnage.get(personnage.getNomPersonnage()).setServeurNotification(serveurNotification);
        }
    }
}

import java.util.HashMap;
import java.util.Vector;

public class Donjon {

    private int tailleDonjon;
    private HashMap<String,EtreVivant> listeEtreVivant;
    private Piece[][] donjon;

    public Donjon(int tailleDonjon) {
        this.tailleDonjon = tailleDonjon;
        this.listeEtreVivant = new HashMap<>();
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

    public Vector<EtreVivant> getEtreVivantMemePiece(EtreVivant etreVivant) {
        Vector<EtreVivant> listePersonnage = new Vector<>();
        for (EtreVivant etreVivantCourant : this.listeEtreVivant.values() ) {
            if ( etreVivant.getPieceActuelle().equals(etreVivantCourant.getPieceActuelle()) ) {
                listePersonnage.add(etreVivantCourant);
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

    public boolean ajouterEtreVivant(EtreVivant etreVivant) {
        if ( !this.listeEtreVivant.containsKey(etreVivant.getNom()) ) {
            this.listeEtreVivant.put(etreVivant.getNom(), etreVivant);
            return true;
        }
        return false;
    }

    public void supprimerEtreVivant(EtreVivant etreVivant) {
        if ( this.listeEtreVivant.containsKey(etreVivant.getNom()) ) {
            this.listeEtreVivant.remove(etreVivant.getNom());
        }
    }

    public EtreVivant recupereEtreVivant(String nomEtreVivant) {
        if ( this.listeEtreVivant.containsKey(nomEtreVivant) ) {
            return this.listeEtreVivant.get(nomEtreVivant);
        }
        return null;
    }

    public void associerServeurNotificationPersonnage(EtreVivant etreVivant, ServeurNotification serveurNotification) {
        if ( this.listeEtreVivant.containsKey(etreVivant.getNom()) ) {
            EtreVivant etreVivantListe = this.listeEtreVivant.get(etreVivant.getNom());
            if ( etreVivantListe instanceof Personnage ) {
                Personnage personnageCourant = (Personnage) etreVivantListe;
                personnageCourant.setServeurNotification(serveurNotification);
            }
        }
    }

    public boolean nomEtreVivantExist(String nomEtreVivant) {
        return this.listeEtreVivant.containsKey(nomEtreVivant);
    }
}

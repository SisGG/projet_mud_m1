import java.util.HashMap;
import java.util.Vector;

/******************************************************************************
 * file     : src/Donjon.java
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
public class Donjon {

    private int tailleDonjon;
    private HashMap<String,EtreVivant> listeEtreVivant;
    private Piece[][] donjon;

    /**
     * Constructeur de la classe Donjon
     * @param tailleDonjon  taille  du donjon
     */
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
    Piece getPieceDepart() {
        return this.donjon[0][0];
    }

    /**
     * Récupère tous les etreVivants présents dans la même piece qu'un etre vivant
     * @param etreVivant etre vivant qui est dans la piece
     * @return liste de tous les etreVivant présents dans la même pièce que l'etre vivant passé en paramètres
     */
    Vector<EtreVivant> getEtreVivantMemePiece(EtreVivant etreVivant) {
        Vector<EtreVivant> listePersonnage = new Vector<>();
        for (EtreVivant etreVivantCourant : this.listeEtreVivant.values() ) {
            if ( etreVivant.getPieceActuelle().equals(etreVivantCourant.getPieceActuelle()) ) {
                listePersonnage.add(etreVivantCourant);
            }
        }
        return listePersonnage;
    }

    Piece getPiece(Piece piece, String direction) {
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

    /**
     * Ajoute un etre vivant dans la liste listeEtreVivants
     * @param etreVivant etreVivant à ajouter dans la liste
     */
    synchronized void ajouterEtreVivant(EtreVivant etreVivant) {
        if ( !this.listeEtreVivant.containsKey(etreVivant.getNom()) ) {
            this.listeEtreVivant.put(etreVivant.getNom(), etreVivant);
        }
    }

    /**
     * Supprime un être vivant de la liste listeEtreVivant
     * @param etreVivant etreVivant à supprimer de la liste
     */
    synchronized void supprimerEtreVivant(EtreVivant etreVivant) {
        if ( etreVivant != null ) {
            this.listeEtreVivant.remove(etreVivant.getNom());
        }
    }

    /**
     * Récupère un etre vivant à partir de listeEtreVivant
     * @param nomEtreVivant nom de l'être vivant à récupérer
     * @return etreVivant qui a le nom passé en paramètre,  null sinon
     */
    EtreVivant recupereEtreVivant(String nomEtreVivant) {
        if ( this.listeEtreVivant.containsKey(nomEtreVivant) ) {
            return this.listeEtreVivant.get(nomEtreVivant);
        }
        return null;
    }

    /**
     * Assoscie un serveur  de   notification à un etreVivant
     * @param etreVivant etreVivant à associer au serveur de notification
     * @param serveurNotification serveur de notification à associer
     */
    void associerServeurNotificationPersonnage(EtreVivant etreVivant, ServeurNotification serveurNotification) {
        if ( this.listeEtreVivant.containsKey(etreVivant.getNom()) ) {
            EtreVivant etreVivantListe = this.listeEtreVivant.get(etreVivant.getNom());
            if ( etreVivantListe instanceof Personnage ) {
                Personnage personnageCourant = (Personnage) etreVivantListe;
                personnageCourant.setServeurNotification(serveurNotification);
            }
        }
    }

    /**
     * Verifie si l'etreVivant est présent dans la liste
     * @param nomEtreVivant nom de l'etreVivant recherché
     * @return vrai s'il existe, faux sinon
     */
    boolean nomEtreVivantExist(String nomEtreVivant) {
        return this.listeEtreVivant.containsKey(nomEtreVivant);
    }
}

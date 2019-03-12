import java.util.HashMap;
import java.util.Vector;

/******************************************************************************
 * file     : src/Donjon.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 3.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 18 Mars 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class Donjon {

    private int tailleDonjon;
    private HashMap<String,EtreVivant> listeEtreVivant;
    private Vector<Combat> listeCombat;
    private Piece[][] donjon;

    /**
     * Constructeur de la classe Donjon.
     * @param tailleDonjon  taille  du donjon.
     */
    public Donjon(int tailleDonjon) {
        this.tailleDonjon = tailleDonjon;
        this.listeEtreVivant = new HashMap<>();
        this.donjon = new Piece[tailleDonjon][tailleDonjon];
        this.genererDonjon(tailleDonjon);
        this.listeCombat = new Vector<>();
    }

    /**
     * Vérifie si le combat se déroule bien dans une piece.
     * @param piece La piece à vérifier.
     * @return Renvoie la valeur true si un combat se déroule dans la piece passée en paramètre, false sinon.
     */
    boolean seDeroulerCombatPiece(Piece piece) {
        for(Combat combat : this.listeCombat){
            if(combat.recupererPieceCombat().equals(piece)){
                return true;
            }
        }
        return false;
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
     * Récupère tous les EtreVivants présents dans la même piece qu'un EtreVivant.
     * @param piece Piece concernée.
     * @return Renvoie la liste de tous les etreVivant présents dans la même pièce que l'EtreVivant passé en paramètres
     */
    Vector<EtreVivant> getEtreVivantMemePiece(Piece piece) {
        Vector<EtreVivant> listePersonnage = new Vector<>();
        for (EtreVivant etreVivantCourant : this.listeEtreVivant.values() ) {
            if ( piece.equals(etreVivantCourant.getPieceActuelle()) ) {
                listePersonnage.add(etreVivantCourant);
            }
        }
        return listePersonnage;
    }

    /**
     * Parcours la liste des combat  et  retourne un vecteur qui contient les combats  qui se passent  dans une  même pièce
     * @param piece pièce que l'on veut vérifier
     * @return vecteur de combats
     */
    Vector<Combat> getCombatMemePiece(Piece piece) {
        Vector<Combat> combats = new Vector<>();
        for (Combat combat : this.listeCombat.subList(0, this.listeCombat.size())) {
            if ( piece.equals(combat.recupererPieceCombat()) ) {
                combats.add(combat);
            }
        }
        return combats;
    }


    /**
     * Retourne une pièce à partir d'une pièce et d'une direction
     * @return la pièce suivante
     */
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
     * Ajoute un nouveau combat dans le Donjon.
     * @param combat Combat à ajouter.
     */
    synchronized void ajouterCombat(Combat combat){
        this.listeCombat.add(combat);
    }

    /**
     * Supprime un combat du Donjon.
     * @param combat Combat à supprimer.
     */
    synchronized void supprimerCombat(Combat combat){
        this.listeCombat.remove(combat);
    }


    /**
     * Ajoute un nouvel EtreVivant dans le Donjon.
     * @param etreVivant EtreVivant à ajouter.
     */
    synchronized void ajouterEtreVivant(EtreVivant etreVivant) {
        if ( !this.listeEtreVivant.containsKey(etreVivant.getNom()) ) {
            this.listeEtreVivant.put(etreVivant.getNom(), etreVivant);
        }
    }

    /**
     * Supprime un EtreVivant du Donjon.
     * @param etreVivant EtreVivant à supprimer.
     */
    synchronized void supprimerEtreVivant(EtreVivant etreVivant) {
        this.listeEtreVivant.remove(etreVivant.getNom());
    }

    /**
     * Récupère un EtreVivant dans le Donjon.
     * @param nomEtreVivant Nom de l'EtreVivant à récupérer.
     * @return Renvoie un EtreVivant dont le nom est passé en paramètre, null sinon.
     */
    EtreVivant recupereEtreVivant(String nomEtreVivant) {
        if ( this.listeEtreVivant.containsKey(nomEtreVivant) ) {
            return this.listeEtreVivant.get(nomEtreVivant);
        }
        return null;
    }

    /**
     * Associe un ServeurNotification à un EtreVivant.
     * @param etreVivant EtreVivant à associer.
     * @param serveurNotification ServeurNotification à associer à l'EtreVivant.
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
     * Verifie si un nom d'EtreVivant est présent dans le Donjon.
     * @param nomEtreVivant Chaine de caractère de l'EtreVivant recherché.
     * @return Renvoie la valeur true si un nom existe dans le Donjon, false sinon.
     */
    boolean nomEtreVivantExiste(String nomEtreVivant) {
        return this.listeEtreVivant.containsKey(nomEtreVivant);
    }

    /**
     * @return la liste des combats
     */
    Vector<Combat> getListeCombat() {
        return this.listeCombat;
    }

    /**
     * Notifie les autres êtres vivant d'un pièce
     * @param etreVivant dans laquelle on va notifier
     * @param message à notifier
     */
    void prevenirJoueurMemePiece(EtreVivant etreVivant, String message){
        for (EtreVivant etreVivantCourant : this.getEtreVivantMemePiece(etreVivant.getPieceActuelle()) ) {
            if ( !etreVivantCourant.equals(etreVivant) ) {
                try {
                    if (etreVivantCourant instanceof Personnage) {
                        Personnage personnageCourant = (Personnage) etreVivantCourant;
                        personnageCourant.getServeurNotification().notifier(message);
                    }
                } catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
    }
}

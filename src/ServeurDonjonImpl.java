import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/******************************************************************************
 * file     : src/ServeurDonjonImpl.java
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
public class ServeurDonjonImpl extends UnicastRemoteObject implements ServeurDonjon {

    private Donjon donjon;

    /**
     * Constructeur de la classe ServeurDonjonImpl.
     * @param donjon Base de données Donjon pour le serveur.
     * @throws RemoteException Exception déclenchée si ServeurDonjonImpl ne crée pas l'objet.
     */
    ServeurDonjonImpl(Donjon donjon) throws RemoteException {
        super();
        this.donjon = donjon;
    }

    /**
     * Crée un personnage, l'enregistre dans le serveur et renvoie le personnage.
     * @param nomPersonnage Nom du personnage.
     * @return Renvoie le nouveau personnage crée.
     */
    public synchronized Personnage seConnecter(String nomPersonnage) {
        Personnage personnage = new Personnage(nomPersonnage);
        this.donjon.ajouterEtreVivant(personnage);
        return personnage;
    }

    /**
     * Déplace un personnage dans le donjon. Met à jour la pièce du joueur et renvoie le nouveau joueur.
     * @param personnage Personnage qui se déplace.
     * @param direction Direction vers lequel le personnage se déplace.
     * @return Renvoie le personnage mis à jour.
     */
    public Personnage seDeplacer(Personnage personnage, String direction) {
        Personnage personnageListe = (Personnage) this.donjon.recupereEtreVivant(personnage.getNom());
        Piece pieceDirection = this.donjon.getPieceDepart();
        if ( personnageListe.getPieceActuelle() == null ) {
            personnageListe.setPieceActuelle(pieceDirection);
        }
        switch ( direction.toUpperCase() ) {
            case "N":
                pieceDirection = this.donjon.getPiece(personnageListe.getPieceActuelle(), "Nord");
                break;
            case "E":
                pieceDirection = this.donjon.getPiece(personnageListe.getPieceActuelle(), "Est");
                break;
            case "S":
                pieceDirection = this.donjon.getPiece(personnageListe.getPieceActuelle(), "Sud");
                break;
            case "O":
                pieceDirection = this.donjon.getPiece(personnageListe.getPieceActuelle(), "Ouest");
                break;
            default:
                break;
        }
        if ( pieceDirection != null ) {
            if ( !direction.equals("") ) {
                this.prevenirJoueurQuitterPiece(personnageListe);
            }
            personnageListe.setPieceActuelle(pieceDirection);
            try {
                personnageListe.getServeurNotification().notifier("\rVous arrivez dans la piece " + pieceDirection);
                this.prevenirEntrerPersonnageMemePiece(personnageListe);
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } else {
            try {
                personnageListe.getServeurNotification().notifier("\rImpossible d'aller dans cette direction.");
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        return personnageListe;
    }

    /**
     * Déconnecte un personnage du donjon. Il le supprime de la liste des personnage.
     * @param personnage Personnage à déconnecter.
     */
    public void seDeconnecter(Personnage personnage){
        try {
            this.donjon.supprimerEtreVivant(personnage);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche tous les EtreVivant qui se trouvent dans la même pièce qu'un Personnage.
     * @param personnage Personnage pour récupérer la pièce et pour le notifier.
     */
    public void afficherEtreVivantPiece(Personnage personnage){
        String notification = "Il y a ";
        for ( EtreVivant etreVivantCourant : this.donjon.getEtreVivantMemePiece(personnage.getPieceActuelle()) ) {
            if ( !etreVivantCourant.equals(personnage) ) {
                notification += etreVivantCourant.getNom() + " ";
            }
        }
        if ( notification.equals("Il y a ") ) {
            notification = "Il n'y a pas d'autre joueur dans la pièce.";
        } else {
            notification += "dans la pièce.";
        }
        try {
            personnage.getServeurNotification().notifier(notification);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Quand un personnage entre dans une pìèce, envoie une notification
     * a tous les personnages deja present et le nom de tous les personnage
     * deja present au personnage entrant, sinon envoie qu'il n'y a personne.
     * @param personnage Personnage entrant dans la piece.
     */
    private void prevenirEntrerPersonnageMemePiece(Personnage personnage) {
        for ( EtreVivant etreVivantCourant : this.donjon.getEtreVivantMemePiece(personnage.getPieceActuelle()) ) {
            if ( !etreVivantCourant.equals(personnage) ) {
                try {
                    if ( etreVivantCourant instanceof Personnage ) {
                        Personnage personnageCourant = (Personnage) etreVivantCourant;
                        personnageCourant.getServeurNotification().notifier(personnage.getNom()
                                + " est entré dans la pièce: " + personnage.getPieceActuelle());
                    }
                } catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
        this.afficherEtreVivantPiece(personnage);
    }

    /**
     * Quand un personnage quitte une pièce, en notifie les autres personnages encore dedans.
     * @param personnage quittant la pièce.
     */
    private void prevenirJoueurQuitterPiece(Personnage personnage){
        for ( EtreVivant etreVivantCourant : this.donjon.getEtreVivantMemePiece(personnage.getPieceActuelle()) ) {
            if ( !etreVivantCourant.equals(personnage) ) {
                try {
                    if ( etreVivantCourant instanceof Personnage ) {
                        Personnage personnageCourant = (Personnage) etreVivantCourant;
                        personnageCourant.getServeurNotification().notifier(personnage.getNom()
                                + " a quitté la pièce.");
                    }
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Associe un serveur de notification à un personnage.
     * @param personnage auquel on associe un serveur notification.
     * @param serveurNotification qui sera associé au personnage.
     */
    public void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) {
        this.donjon.associerServeurNotificationPersonnage(personnage, serveurNotification);
    }

    /**
     * Vérifie si un personnage est dans la liste de personnage du donjon.
     * @param nomPersonnage que l'on cherche dans la liste.
     * @return Renvoie la valeur true si le personnage existe, false sinon.
     */
    public boolean existeNomPersonnage(String nomPersonnage)  {
        return this.donjon.recupereEtreVivant(nomPersonnage) != null;
    }

}

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/******************************************************************************
 * file     : src/ServeurDonjonImpl.java
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
     * @param serveurCombat Utilisé pour lancer un combat lors d'un déplacement
     * @return Renvoie le personnage mis à jour.
     */
    public Personnage seDeplacer(Personnage personnage, String direction, ServeurCombat serveurCombat) {
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
                this.donjon.prevenirJoueurMemePiece(personnageListe, personnage.getNom() + " a quitté la pièce.");
            }
            personnageListe.setPieceActuelle(pieceDirection);
            try {
                personnageListe.getServeurNotification().notifier("Vous arrivez dans la pièce : " + pieceDirection);
                this.afficherEtreVivantPiece(personnageListe);
                this.donjon.prevenirJoueurMemePiece(personnageListe, personnage.getNom()+" est entré dans la pièce: " + pieceDirection);
                if ( !direction.equals("") ) {
                    serveurCombat.lancerCombatMonstre(personnageListe);
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } else {
            try {
                personnageListe.getServeurNotification().notifier("Impossible d'aller dans cette direction.");
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        return personnageListe;
    }

    /**
     * Déconnecte un personnage du donjon. Il le supprime de la liste des personnage.
     * @param personnage à déconnecter.
     */
    public void deconnecter(Personnage personnage) {
        try {
            this.donjon.prevenirJoueurMemePiece(personnage, personnage.getNom() + " est maintenant déconnecté.");
            this.donjon.supprimerEtreVivant(personnage);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche tous les EtreVivant qui se trouvent dans la même pièce qu'un Personnage.
     * @param personnage Personnage pour récupérer la pièce et pour le notifier.
     */
    public void afficherEtreVivantPiece(Personnage personnage) {
        String notification = "Êtres dans la pièce: ";
        for ( EtreVivant etreVivantCourant : this.donjon.getEtreVivantMemePiece(personnage.getPieceActuelle()) ) {
            if ( !etreVivantCourant.equals(personnage) ) {
                notification = notification.concat("[" + etreVivantCourant.getNom() + "|" + etreVivantCourant.getPointDeVie()+"pdv] ");
            }
        }
        if ( notification.equals("Êtres dans la pièce: ")) {
            notification = "Il n'y a pas d'autres êtres dans la pièce.";
        }
        try {
            personnage.getServeurNotification().notifier(notification);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche tous les combats qui se trouvent dans la même pièce qu'un personnage.
     * @param personnage  pour récupérer la pièce et pour le notifier.
     */
    public void afficherCombatPiece(Personnage personnage) {
        String notification = "Combats dans la pièce: ";
        for ( Combat combat : this.donjon.getCombatMemePiece(personnage.getPieceActuelle()) ) {
            notification = notification.concat("[" + combat.getEtreVivantAttaquant().getNom() +  "|vs|" + combat.getEtreVivantAttaque().getNom() + "] ");
        }
        if ( notification.equals("Combats dans la pièce: ") ) {
            notification = "Il n'y a pas de combat dans la pièce.";
        }
        try {
            personnage.getServeurNotification().notifier(notification);
        } catch ( Exception e ) {
            e.printStackTrace();
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
     * @param nomEtreVivant que l'on cherche dans la liste.
     * @return Renvoie la valeur true si le personnage existe, false sinon.
     */
    public boolean existeNomEtreVivant(String nomEtreVivant) {
        return this.donjon.recupereEtreVivant(nomEtreVivant) != null;
    }

    /**
     * Retourne un personnage en fonction du nom passé en parametre
     * @param nomPersonnage nom du  personnage recherché
     * @return personnage si trouvé, null sinon
     */
    public Personnage getPersonnage(String nomPersonnage){
        EtreVivant etreVivant = this.donjon.recupereEtreVivant(nomPersonnage);
        if ( etreVivant.getClass().equals(Personnage.class) ) {
            return (Personnage) etreVivant;
        }
        return null;
    }

    /**
     * Retourne un monstre en fonction du nom passé en parametre
     * @param nomMonstre du monstre recherché
     * @return Monstre si trouvé sinon null
     */
    public Monstre getMonstre(String nomMonstre) {
        EtreVivant etreVivant = this.donjon.recupereEtreVivant(nomMonstre);
        if ( etreVivant.getClass().equals(Monstre.class) ) {
            return (Monstre) etreVivant;
        }
        return null;
    }

}

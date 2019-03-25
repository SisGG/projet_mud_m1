import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/******************************************************************************
 * file     : src/ServeurCombatImpl.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 4.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 25 Mars 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class ServeurCombatImpl extends UnicastRemoteObject implements ServeurCombat {

    private Donjon donjon;

    /**
     * Constructeur de la classe ServeurCombatImpl.
     *
     * @param donjon Base de données Donjon pour le serveur.
     * @throws RemoteException Exception déclenchée si ServeurCombatImpl ne crée pas l'objet.
     */
    ServeurCombatImpl(Donjon donjon) throws RemoteException {
        super();
        this.donjon = donjon;
    }

    /**
     * Permet de lancer un combat entre un Personnage et un Monstre.
     *
     * @param personnage Personnage qui est attaquer par un Monstre.
     */
    public void lancerCombatMonstre(Personnage personnage) {
        Monstre monstre = new Monstre(personnage.getPieceActuelle());
        while (this.donjon.nomEtreVivantExiste(monstre.getNom())) {
            monstre = new Monstre(personnage.getPieceActuelle());
        }
        this.donjon.ajouterEtreVivant(monstre);
        this.lancerCombat(monstre, personnage);
    }

    /**
     * Permet de lancer un combat entre deux êtres vivants se trouvant dans la même pièce
     *
     * @param attaquant Etre attaquant
     * @param attaque   Etre attaqué
     */
    public void lancerCombat(EtreVivant attaquant, EtreVivant attaque) {
        this.afficherMessageCombat(attaquant, attaque);

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Combat combat = new Combat(this.donjon, attaquant, attaque);
        Piece pieceCombat = combat.recupererPieceCombat();
        this.donjon.ajouterCombat(combat);
        EtreVivant gagnant = combat.lancerCombat();
        if (gagnant != null) {
            if (combat.getEtreVivantAttaquant().getPointDeVieActuel() == 0
                    && combat.getEtreVivantAttaque().equals(gagnant)) {
                this.afficherMessageVainqueur(combat.getEtreVivantAttaque(), combat.getEtreVivantAttaquant());
            } else if (combat.getEtreVivantAttaque().getPointDeVieActuel() == 0
                    && combat.getEtreVivantAttaquant().equals(gagnant)) {
                this.afficherMessageVainqueur(combat.getEtreVivantAttaquant(), combat.getEtreVivantAttaque());
            }
        }
        this.donjon.supprimerCombat(combat);
        this.regagnerVieMax(pieceCombat);
    }

    /**
     * permet d'afficher l'issue d'un comlbat dans la pièce et supprime l'etreVivant perdant
     *
     * @param vainqueur etreVivant vainqueur du combat
     * @param vaincu    etreVivant perdant du combat
     */
    private void afficherMessageVainqueur(EtreVivant vainqueur, EtreVivant vaincu) {
        this.donjon.prevenirJoueurMemePiece(vainqueur, vaincu.getNom() + " a été vaincu par " + vainqueur.getNom() + ".");
        this.donjon.supprimerEtreVivant(vaincu);
    }

    /**
     * Cette méthode prévient les joueurs présent dans la même pièce du début d'un combat
     *
     * @param attaquant EtreVivant attaquant
     * @param attaque   EtreVivant attaqué
     */
    private void afficherMessageCombat(EtreVivant attaquant, EtreVivant attaque) {
        String messageAttaque = "[" + attaquant.getNom() + " - " + attaquant.getPointDeVieActuel() + " pdv]"
                + " attaque [" + attaque.getNom() + " - " + attaque.getPointDeVieActuel() + " pdv].";
        this.donjon.prevenirJoueurMemePiece(attaquant, messageAttaque);
        if (attaquant instanceof Personnage) {
            try {
                ((Personnage) attaquant).getServeurNotification().notifier(messageAttaque);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        this.messageCommandeFuite(attaquant);
        this.messageCommandeFuite(attaque);
    }

    private void messageCommandeFuite(EtreVivant etreVivant) {
        if (etreVivant instanceof Personnage) {
            try {
                ((Personnage) etreVivant).getServeurNotification().notifier("Appuyez sur \'Entrer\' pour fuir.");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fais regagner tous les points de vie à tous les EtreVivants se trouvant dans la Pièce passée en paramètre.
     *
     * @param piece Pièce dont tous les EtreVivants doivent regagner tous leurs points de vie.
     */
    private void regagnerVieMax(Piece piece) {
        if (!this.donjon.seDeroulerCombatPiece(piece)) {
            for (EtreVivant etreVivant : this.donjon.getEtreVivantMemePiece(piece)) {
                this.donjon.recupereEtreVivant(etreVivant.nomEtreVivant).regagnerPointDeVieMax();
                if (etreVivant instanceof Personnage) {
                    try {
                        ((Personnage) etreVivant).getServeurNotification().notifier(
                                "Il n'y a plus de combat en cours dans la pièce, tous les êtres regagnent leurs pdv maximum."
                                        + " Vous avez " + etreVivant.getPointDeVieActuel() + " pdv.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Permet à un EtreVivant de fuir un combat et prévient les joeurs dans la même pièce de la fuite
     *
     * @param fuyant EtreVivant voulant fuir
     */
    public void fuirCombat(EtreVivant fuyant) {
        Combat combat = this.getCombatEtre(fuyant);
        if (combat.getEtreVivantAttaquant().equals(fuyant)) {
            combat.fuirCombat(fuyant, combat.getEtreVivantAttaque());
        } else {
            combat.fuirCombat(fuyant, combat.getEtreVivantAttaquant());
        }
    }

    /**
     * Parcour la liste des combat pour renvoyer le combat auquel participe l'etreVivant passé en paramètres
     *
     * @param etreVivant etreVivant participant au  combat
     * @return combat quaquel participe l'etreVivant ou null
     */
    public Combat getCombatEtre(EtreVivant etreVivant) {
        for (Combat combat : this.donjon.getListeCombat()) {
            if (combat.getEtreVivantAttaque().equals(etreVivant)
                    || combat.getEtreVivantAttaquant().equals(etreVivant)) {
                return combat;
            }
        }
        return null;
    }

    /**
     * Renvoie un boolean pour savoir si un etreVivant  est en combat ou non
     *
     * @param etreVivant etrVivant dont on veut connaitre l'etat
     * @return boolean
     */
    public boolean estEnCombat(EtreVivant etreVivant) {
        return this.getCombatEtre(etreVivant) != null;
    }

}

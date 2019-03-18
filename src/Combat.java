import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Random;

/******************************************************************************
 * file     : src/Combat.java
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
class Combat implements Serializable {

    private EtreVivant etreVivantAttaquant;
    private EtreVivant etreVivantAttaque;
    private boolean combatEnCours;

    /**
     * Constructeur de la classe Combat.
     * @param donjon Donjon dans lequel se passe le combat.
     * @param etreVivantAttaquant etreVivant impliqué dans le combat.
     * @param etreVivantAttaque Monstre impliqué dans le combat.
     */
    Combat(Donjon donjon, EtreVivant etreVivantAttaquant, EtreVivant etreVivantAttaque) {
        this.etreVivantAttaquant = donjon.recupereEtreVivant(etreVivantAttaquant.getNom());
        this.etreVivantAttaque = donjon.recupereEtreVivant(etreVivantAttaque.getNom());
        this.combatEnCours = true;
    }

    /**
     * Lance le combat et donne le choix au etreVivantAttaquant de continuer ou fuir.
     * @return l'être vivant gagnant du combat
     */
    EtreVivant lancerCombat() {
        EtreVivant gagnant = null;
        while ( this.combatEnCours ) {
            gagnant = this.effectuerTour();
        }
        return gagnant;
    }

    /**
     * Fait perdre à un des deux participants un point de vie de façon aléatoire.
     * Notifie les participants du déroulement du combat.
     */
    private EtreVivant effectuerTour() {
        int ciblePerdant1PDV = new Random().nextInt(2);

        if ( this.etreVivantAttaquant.getPointDeVie() != 0 && this.etreVivantAttaque.getPointDeVie() != 0 ) {

            if ( ciblePerdant1PDV == 0 ) {
                this.etreVivantAttaquant.perdrePointDeVie();
                this.afficherMessageBlessure(etreVivantAttaquant, etreVivantAttaque, etreVivantAttaquant);
                this.afficherMessageBlessure(etreVivantAttaquant, etreVivantAttaque, etreVivantAttaque);

            } else if ( ciblePerdant1PDV == 1 ) {
                this.etreVivantAttaque.perdrePointDeVie();
                this.afficherMessageBlessure(etreVivantAttaque, etreVivantAttaquant, etreVivantAttaquant);
                this.afficherMessageBlessure(etreVivantAttaque, etreVivantAttaquant, etreVivantAttaque);
            }

            if ( this.etreVivantAttaquant.getPointDeVie() == 0 ) {
                this.etreVivantAttaque.augmenterPointDeVie();
                this.afficherMessageMort(this.etreVivantAttaquant, this.etreVivantAttaque);
                this.combatEnCours = false;
                return etreVivantAttaque;
            } else if ( this.etreVivantAttaque.getPointDeVie() == 0 ) {
                this.etreVivantAttaquant.augmenterPointDeVie();
                this.afficherMessageMort(this.etreVivantAttaque, this.etreVivantAttaquant);
                this.combatEnCours = false;
                return etreVivantAttaquant;
            }

            try {
                Thread.sleep(1000);
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
            choixCombat(etreVivantAttaquant, etreVivantAttaque);
            choixCombat(etreVivantAttaque, etreVivantAttaquant);
        } else {
            this.combatEnCours = false;
        }
        return null;
    }

    /**
     * Notifie le personnage du déroulement d'un tour du combat dans lequel il est impliqué
     * @param blesse EtreVivant blessé dans ce tour
     * @param blesseur EtreVivant blessant dans ce tour
     * @param etreNotifie l'EtreVivant à notifier à condition qu'il soit un personnage
     */
    private void afficherMessageBlessure(EtreVivant blesse, EtreVivant blesseur, EtreVivant etreNotifie) {
        try {
            if ( etreNotifie instanceof Personnage ) {
                ((Personnage) etreNotifie).getServeurNotification().notifier(blesseur.getNom()
                        + " attaque. "+blesse.getNom() + " perd 1 pdv. [" + etreVivantAttaquant.getNom() + ": "
                        + etreVivantAttaquant.getPointDeVie() + " pdv] - [" + etreVivantAttaque.getNom() + ": "
                        + etreVivantAttaque.getPointDeVie() + " pdv]");
            }
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche la mort d'un EtreVivant
     * @param etreMort EtreVivant mort
     * @param etreTueur EtreVivant Tueur
     */
    private void afficherMessageMort(EtreVivant etreMort, EtreVivant etreTueur) {
        if ( etreMort instanceof Personnage ) {
            try {
                ((Personnage) etreMort).getServeurNotification().notifier("Vous mourez... bye bye.");
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }
        }
        if(etreTueur instanceof Personnage) {
            try {
                ((Personnage) etreTueur).getServeurNotification().notifier(
                        "Fin du combat, vous gagnez un pdv. Vous avez " +etreTueur.getPointDeVie()+" pdv.");
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Donne le choix à un personnage de choisir entre fuir et continuer un combat, en tappant entrée, le personnage fui
     * @param fuyant personnage fuyant
     * @param fuye EtreVivant auquel le fuyant veut fuir
     */
    private void choixCombat(EtreVivant fuyant, EtreVivant fuye) {
        if ( fuyant instanceof Personnage ) {
            try {
                if ( ((Personnage) fuyant).getServeurNotification().demanderAction().equals("") ) {
                    this.fuirCombat(fuyant, fuye);
                }
            } catch ( RemoteException e ) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Notifie le personnage fuyé quand l'adversaire fuit le combat
     * @param fuyant personnage ayant fui
     * @param fuye personnage auquel le fuyant veut fuir
     */
    void fuirCombat(EtreVivant fuyant, EtreVivant fuye) {
        try {
            if ( fuyant instanceof Personnage ) {
                ((Personnage) fuyant).getServeurNotification().notifier("Vous avez fui devant "
                        + fuye.getNom() + ". Il vous reste " + fuyant.getPointDeVie() + " point de vie.");
            }
            if ( fuye instanceof Personnage ) {
                ((Personnage) fuye).getServeurNotification().notifier(fuyant.getNom()
                        + " a fui devant vous. Il vous reste " + fuye.getPointDeVie() + " point de vie.");
            }
        } catch ( RemoteException e ) {
            e.printStackTrace();
        }
        this.combatEnCours = false;
    }

    /**
     * @return Renvoie l'instance de l'EtreVivant attaqué
     */
    EtreVivant getEtreVivantAttaque() {
        return etreVivantAttaque;
    }

    /**
     * @return Renvoie l'instance de l'EtreVivant attaquant
     */
    EtreVivant getEtreVivantAttaquant() {
        return etreVivantAttaquant;
    }


    /**
     * @return Renvoie la pièce dans laquelle le combat a lieu.
     */
    Piece recupererPieceCombat() {
        return this.etreVivantAttaquant.getPieceActuelle();
    }
}

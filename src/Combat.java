import java.rmi.RemoteException;
import java.util.Random;

/******************************************************************************
 * file     : src/Combat.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 2.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 18 Février 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
class Combat {

    private EtreVivant etreVivantAttaquant;
    private EtreVivant etreVivantAttaque;

    /**
     * Constructeur de la classe Combat.
     * @param donjon Donjon dans lequel se passe le combat.
     * @param etreVivantAttaquant etreVivant impliqué dans le combat.
     * @param etreVivantAttaque Monstre impliqué dans le combat.
     */
    Combat(Donjon donjon, EtreVivant etreVivantAttaquant, EtreVivant etreVivantAttaque) {
        this.etreVivantAttaquant = donjon.recupereEtreVivant(etreVivantAttaquant.getNom());
        this.etreVivantAttaque = donjon.recupereEtreVivant(etreVivantAttaque.getNom());
    }

    /**
     * Lance le combat et donne le choix au etreVivantAttaquant de continuer ou fuir.
     */
    void lancerCombat() {
        boolean continuerCombat = true;
        while ( continuerCombat ) {
            try {
                this.effectuerTour();
                continuerCombat = this.etreVivantAttaquant.getPointDeVie() != 0 && this.etreVivantAttaque.getPointDeVie() != 0;
                if ( continuerCombat ) {
                    if(etreVivantAttaquant instanceof Personnage){
                        if(((Personnage) etreVivantAttaquant).getServeurNotification().demanderAction().equals("")){
                            ((Personnage) etreVivantAttaquant).getServeurNotification().notifier("Vous avez fui le combat. " +
                                    "Il vous reste " + etreVivantAttaquant.getPointDeVie() + " point de vie.");
                            if(etreVivantAttaque instanceof Personnage){
                                ((Personnage) etreVivantAttaque).getServeurNotification().notifier(etreVivantAttaquant.getNom()+
                                        " a fui le combat. " + "Il vous reste " + etreVivantAttaque.getPointDeVie() + " point de vie.");
                            }
                            continuerCombat = false;
                        }
                    }
                    if(etreVivantAttaque instanceof Personnage){
                        if(((Personnage) etreVivantAttaque).getServeurNotification().demanderAction().equals("")){
                            ((Personnage) etreVivantAttaque).getServeurNotification().notifier("Vous avez fui le combat. " +
                                    "Il vous reste " + etreVivantAttaque.getPointDeVie() + " point de vie.");
                            if(etreVivantAttaquant instanceof Personnage) {
                                ((Personnage) etreVivantAttaquant).getServeurNotification().notifier(etreVivantAttaque.getNom() +
                                        " a fui le combat. " + "Il vous reste " + etreVivantAttaquant.getPointDeVie() + " point de vie.");
                            }
                            continuerCombat = false;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fait perdre à un des deux participants un point de vie de façon aléatoire.
     * Notifie le etreVivantAttaquant du déroulement du combat.
     */
    private void effectuerTour() throws RemoteException {
        int ciblePerdant1PDV = new Random().nextInt(2);

        if ( ciblePerdant1PDV == 0 ) {
            if (this.etreVivantAttaquant instanceof Personnage) {
                ((Personnage) this.etreVivantAttaquant).getServeurNotification().notifier(this.etreVivantAttaque.getNom()
                        + " vous attaque, vous perdez 1 point de vie.");
            }
            this.etreVivantAttaquant.perdrePointDeVie();
            if(this.etreVivantAttaque instanceof Personnage){
                ((Personnage) this.etreVivantAttaque).getServeurNotification().notifier("Vous attaquez, "
                        + this.etreVivantAttaquant.getNom() + " perd 1 point de vie.");
            }
        } else if (ciblePerdant1PDV == 1 ) {
            if (this.etreVivantAttaque instanceof Personnage) {
                ((Personnage) this.etreVivantAttaque).getServeurNotification().notifier(this.etreVivantAttaquant.getNom()
                        + " vous attaque, vous perdez 1 point de vie.");
            }
            this.etreVivantAttaque.perdrePointDeVie();
            if(this.etreVivantAttaquant instanceof Personnage){
                ((Personnage) this.etreVivantAttaquant).getServeurNotification().notifier("Vous attaquez, "
                        + this.etreVivantAttaque.getNom() + " perd 1 point de vie.");
            }
        }

        if ( this.etreVivantAttaquant.getPointDeVie() == 0 ) {
            this.etreVivantAttaque.augmenterPointDeVie();
            if (this.etreVivantAttaquant instanceof Personnage) {
                ((Personnage) this.etreVivantAttaquant).getServeurNotification().notifier("Vous mourez... bye bye.");
            }
            if(this.etreVivantAttaque instanceof Personnage) {
                ((Personnage) this.etreVivantAttaque).getServeurNotification().notifier(
                        "Vous tuez " + this.etreVivantAttaquant.getNom() +
                                ". Fin du combat, vous gagnez donc un point de vie. " +
                                "Il vous reste " + this.etreVivantAttaque.getPointDeVie() + " points de vie.");
            }
        } else if ( this.etreVivantAttaque.getPointDeVie() == 0 ) {
            if (this.etreVivantAttaque instanceof Personnage) {
                ((Personnage) this.etreVivantAttaque).getServeurNotification().notifier("Vous mourez... bye bye.");
            }
            this.etreVivantAttaquant.augmenterPointDeVie();
            if(this.etreVivantAttaquant instanceof Personnage) {
                ((Personnage) this.etreVivantAttaquant).getServeurNotification().notifier(
                        "Vous tuez " + this.etreVivantAttaque.getNom() +
                        ". Fin du combat, vous gagnez donc un point de vie. " +
                        "Il vous reste " + this.etreVivantAttaquant.getPointDeVie() + " points de vie.");
            }
        } else {
            if (this.etreVivantAttaquant instanceof Personnage) {
                ((Personnage) this.etreVivantAttaquant).getServeurNotification().notifier("Il vous reste "
                        + this.etreVivantAttaquant.getPointDeVie() + " pdv. " +
                        this.etreVivantAttaque.nomEtreVivant+ " a " + this.etreVivantAttaque.getPointDeVie() + " pdv.");
            }
            if (this.etreVivantAttaque instanceof Personnage) {
                ((Personnage) this.etreVivantAttaque).getServeurNotification().notifier("Il vous reste "
                        + this.etreVivantAttaque.getPointDeVie() + " pdv. " +
                        this.etreVivantAttaquant.nomEtreVivant+ " a " + this.etreVivantAttaquant.getPointDeVie() + " pdv.");
            }
        }
    }

    EtreVivant getEtreVivantAttaque() {
        return etreVivantAttaque;
    }

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

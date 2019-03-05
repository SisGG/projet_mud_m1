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
class CombatMonstre {

    private Personnage personnage;
    private Monstre monstre;

    /**
     * Constructeur de la classe CombatMonstre.
     * @param donjon Donjon dans lequel se passe le combat.
     * @param personnage Personnage impliqué dans le combat.
     * @param monstre Monstre impliqué dans le combat.
     */
    CombatMonstre(Donjon donjon, Personnage personnage, Monstre monstre) {
        this.personnage = (Personnage) donjon.recupereEtreVivant(personnage.getNom());
        this.monstre = (Monstre) donjon.recupereEtreVivant(monstre.getNom());
    }

    /**
     * Lance le combat et donne le choix au personnage de continuer ou fuir.
     * @return retourne L'EtreVivant vainqueur du combat.
     * @throws RemoteException Exception déclenchée si la méthode n'est pas invoquée.
     */
    EtreVivant lancerCombat() throws RemoteException {
        boolean continuerCombat = true;
        String action;
        while ( continuerCombat ) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.effectuerTour();
            continuerCombat = this.personnage.getPointDeVie() != 0 && this.monstre.getPointDeVie() != 0;
            if ( continuerCombat ) {
                action = personnage.getServeurNotification().demanderAction("");
                if (action.equals("f")){
                    personnage.getServeurNotification().notifier("Vous avez fui le combat. " +
                            "Il vous reste " + personnage.getPointDeVie() + " point de vie.");
                    continuerCombat = false;
                }
            }
        }
        if ( this.monstre.getPointDeVie() == 0 )
            return this.monstre;
        return this.personnage;
    }

    /**
     * Fait perdre à un des deux participants un point de vie de façon aléatoire.
     * Notifie le personnage du déroulement du combat.
     * @throws RemoteException Exception déclenchée si la méthode n'est pas invoquée.
     */
    private void effectuerTour() throws RemoteException {
        int ciblePerdant1PDV = new Random().nextInt(2);
        if ( ciblePerdant1PDV == 0 ) {
            this.personnage.getServeurNotification().notifier(this.monstre.getNom()
                    + " vous attaque, vous perdez 1 point de vie.");
            this.personnage.perdrePointDeVie();
        } else if (ciblePerdant1PDV == 1 ) {
            this.personnage.getServeurNotification().notifier("Vous attaquez "
                    + this.monstre.getNom() + " perd 1 point de vie.");
            this.monstre.perdrePointDeVie();
        }

        if ( this.personnage.getPointDeVie() == 0 ) {
            this.monstre.augmenterPointDeVie();
            this.personnage.getServeurNotification().notifier("Vous mourez... bye bye.");
        } else if ( this.monstre.getPointDeVie() == 0 ) {
            this.personnage.augmenterPointDeVie();
            this.personnage.getServeurNotification().notifier("Vous tuez " + this.monstre.getNom() +
                    ". Fin du combat, vous gagnez donc un point de vie. " +
                    "Il vous reste " + this.personnage.getPointDeVie() + " points de vie.");
        } else {
            this.personnage.getServeurNotification().notifier("Il vous reste "
                    + this.personnage.getPointDeVie() + " pdv. " +
                    "Le monstre a " + this.monstre.getPointDeVie() + " pdv.");
        }
    }

    /**
     * @return Renvoie la pièce dans laquelle le combat a lieu.
     */
    Piece recupererPieceCombat() {
        return this.personnage.getPieceActuelle();
    }
}

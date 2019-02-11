import java.rmi.RemoteException;
import java.util.Random;

/******************************************************************************
 * file     : src/Combat.java
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
class CombatMonstre {

    private Personnage personnage;
    private Monstre monstre;

    CombatMonstre(Donjon donjon, Personnage personnage, Monstre monstre) {
        this.personnage = (Personnage) donjon.recupereEtreVivant(personnage.getNom());
        this.monstre = (Monstre) donjon.recupereEtreVivant(monstre.getNom());
    }

    EtreVivant lancerCombat() throws RemoteException {
        boolean continuerCombat = true;
        while ( continuerCombat ) {
            this.effectuerTour();
            continuerCombat = this.personnage.getPointDeVie() != 0 && this.monstre.getPointDeVie() != 0;
            if ( continuerCombat ) {
                String action = personnage.getServeurNotification().demanderAction("Entrez \"fuir\" pour quitter le combat" +
                        " ou n'importe quelle autre commande pour continuer : ");
                if ( action.equals("fuir") ) {
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
                    ". Fin du combat, vous regagnez donc un point de vie. " +
                    "Il vous reste " + this.personnage.getPointDeVie() + " points de vie.");
        } else {
            this.personnage.getServeurNotification().notifier("Il vous reste "
                    + this.personnage.getPointDeVie() + " pdv. " +
                    "Le monstre a " + this.monstre.getPointDeVie() + " pdv.");
        }
    }

}

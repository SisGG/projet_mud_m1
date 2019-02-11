import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/******************************************************************************
 * file     : src/ServeurCombatImpl.java
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
public class ServeurCombatImpl extends UnicastRemoteObject implements ServeurCombat {

    private static long serialVersionUID = 2L;
    private Donjon donjon;

    public ServeurCombatImpl(Donjon donjon) throws RemoteException {
        super();
        this.donjon = donjon;
    }

    public synchronized int lancerCombat(Personnage personnage) throws RemoteException {
        System.out.println("[ServeurCombat] Lancement combat.");
        Monstre monstre = new Monstre(personnage.getPieceActuelle());
        while ( this.donjon.nomEtreVivantExist(monstre.getNom()) ) {
            monstre = new Monstre(personnage.getPieceActuelle());
        }
        personnage.getServeurNotification().notifier(monstre.getNom()+" vous attaque.");
        this.donjon.ajouterEtreVivant(monstre);
        String action = "";
        int resultatTour = 0;
        while ( resultatTour == 0 ) {
            resultatTour = this.effectuerTour(personnage, monstre);
            if (resultatTour == 0) {
                action = personnage.getServeurNotification().demanderAction("Entre \"fuir\" pour quitter le combat" +
                        " ou n'importe quelle autre commande pour continuer : ");
                if (action.equals("fuir")) {
                    personnage.getServeurNotification().notifier("Vous avez fuit le combat. " +
                            "Il vous reste " + personnage.getPointDeVie() + " point de vie.");
                    break;
                }
            }
        }
        return resultatTour;
    }

    private int effectuerTour(Personnage personnage, Monstre monstre) throws RemoteException {
        int ciblePerdant1PDV = new Random().nextInt(2);
        Personnage personnageCourant = (Personnage) this.donjon.recupereEtreVivant(personnage.getNom());
        Monstre monstreCourant = (Monstre) this.donjon.recupereEtreVivant(monstre.getNom());

        if ( ciblePerdant1PDV == 0 ) {
            personnageCourant.getServeurNotification().notifier(monstreCourant.getNom()
                    + " vous attaque, vous perdez 1 point de vie.");
            personnageCourant.perdrePointDeVie();
        } else if (ciblePerdant1PDV == 1 ) {
            personnageCourant.getServeurNotification().notifier("Vous attaquer "
                    + monstreCourant.getNom() + " perd 1 point de vie.");
            monstreCourant.perdrePointDeVie();
        }

        if ( personnageCourant.getPointDeVie() == 0 ) {
            personnageCourant.getServeurNotification().notifier("Vous mourez... bye bye.");
            monstreCourant.augmenterPointDeVie();
            return 1;
        } else if ( monstreCourant.getPointDeVie() == 0 ) {
            personnageCourant.augmenterPointDeVie();
            personnageCourant.getServeurNotification().notifier("Vous tuez " + monstreCourant.getNom() +
                    ". Fin du combat, vous regagnez donc un point de vie. " +
                    "Il vous reste " + personnageCourant.getPointDeVie() + " points de vie.");
            this.donjon.supprimerEtreVivant(monstreCourant);
            personnageCourant.augmenterPointDeVie();
            return 2;
        } else {
            personnageCourant.getServeurNotification().notifier("Il vous reste "
                    + personnageCourant.getPointDeVie() + " pdv. " +
                    "Le monstre a " + monstreCourant.getPointDeVie() + " pdv.");
        }
        return 0;
    }
    
    /*
    private HashMap<String, EtreVivant> faireRegagnerPointDeVieMaxPiece(EtreVivant etreVivant){
        for ( EtreVivant etreVivantCurrent : this.donjon.getPersonnageMemePiece(etreVivant) ) {
            etreVivantCurrent.regagnerPointDeVieMax();
        }
        return null;
    }*/

}

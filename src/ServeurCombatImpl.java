import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
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
        String action = "continuer";
        int resultatTour = 0;
        while ( resultatTour == 0 ) {
            resultatTour = this.effectuerTour(personnage, monstre);
            if ( resultatTour == 0 ) {
                action = personnage.getServeurNotification().demanderAction();
                if (action.equals("fuir")) {
                    personnage.getServeurNotification().notifier("Vous avez fuit le combat.");
                    break;
                }
            }
        }
        return resultatTour;
    }

    private int effectuerTour(Personnage personnage, Monstre monstre) throws RemoteException {
        int ciblePerdant1PDV = new Random().nextInt(2);
        if ( ciblePerdant1PDV == 0 ) {
            personnage.getServeurNotification().notifier("Vous perdez 1 point de vie.");
            personnage.perdrePointDeVie();
        } else {
            personnage.getServeurNotification().notifier(monstre.getNom()+" perds 1 point de vie.");
            monstre.perdrePointDeVie();
        }

        if ( personnage.getPointDeVie() == 0 ) {
            personnage.getServeurNotification().notifier("Vous mourez... bye bye.");
            monstre.augmenterPointDeVie();
            return 1;
        } else if ( monstre.getPointDeVie() == 0 ) {
            personnage.augmenterPointDeVie();
            personnage.getServeurNotification().notifier("Vous tuez "+monstre.getNom()+
                    ". Vous regagnez donc un point de vie.\n" +
                    "Il vous reste " + personnage.getPointDeVie() + " points de vie.");
            this.donjon.supprimerEtreVivant(monstre);
            personnage.augmenterPointDeVie();
            return 2;
        } else {
            personnage.getServeurNotification().notifier("Il vous reste " + personnage.getPointDeVie()
                    + ". Le monstre a " + monstre.getPointDeVie() + ".");
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

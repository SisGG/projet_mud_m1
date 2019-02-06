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
    private HashMap<String, EtreVivant> listeEtreVivant;

    public ServeurCombatImpl() throws RemoteException {
        super();
        this.listeEtreVivant = new HashMap<>();
    }

    public synchronized void lancerCombat(Personnage personnage) throws RemoteException {
        System.out.println("[ServeurCombat] Lancement combat.");
        //personnage.getServeurNotification().notifier("Un monstre vous attaque.");
        Monstre monstre = new Monstre(personnage.getPieceActuelle());
        this.listeEtreVivant.put(monstre.getNom(), monstre);
        int resultatTour = 0;
        while ( resultatTour == 0 ) {
            resultatTour = this.effectuerTour(personnage, monstre);
        }
    }

    private int effectuerTour(Personnage personnage, Monstre monstre) throws RemoteException {
        int ciblePerdant1PDV = new Random().nextInt(2);
        //System.out.println("[ServeurCombat] " + ciblePerdant1PDV);
        if ( ciblePerdant1PDV == 0 ) {
            //personnage.getServeurNotification().notifier("Vous perdez 1 point de vie.");
            personnage.perdrePointDeVie();
        } else {
            //personnage.getServeurNotification().notifier("Le monstre perds 1 point de vie.");
            monstre.perdrePointDeVie();
        }

        if ( personnage.getPointDeVie() == 0 ) {
            //personnage.getServeurNotification().notifier("Vous mourez... bye bye.");
            return 1;
        } else if ( monstre.getPointDeVie() == 0 ) {
            //personnage.getServeurNotification().notifier("Vous tuez le monstre.");
            return 2;
        } else {
            //personnage.getServeurNotification().notifier("");
            return 0;
        }
    }

    public EtreVivant faireGagnerPointDeVie(EtreVivant etreVivant) {
        etreVivant.augmenterPointDeVie();
        return etreVivant;
    }

    private HashMap<String, EtreVivant> faireRegagnerPointDeVieMaxPiece(EtreVivant etreVivant){
        for ( EtreVivant etreVivantCurrent : this.listeEtreVivant.values() ) {
            if ( etreVivantCurrent.getPieceActuelle().equals(etreVivant.getPieceActuelle()) ) {
                etreVivantCurrent.regagnerPointDeVieMax();
            }
        }
        return this.listeEtreVivant;
    }

}

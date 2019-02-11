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

    private Donjon donjon;

    ServeurCombatImpl(Donjon donjon) throws RemoteException {
        super();
        this.donjon = donjon;
    }

    public boolean lancerCombatMonstre(Personnage personnage) throws RemoteException {
        Monstre monstre = new Monstre(personnage.getPieceActuelle());
        while ( this.donjon.nomEtreVivantExist(monstre.getNom()) ) {
            monstre = new Monstre(personnage.getPieceActuelle());
        }
        personnage.getServeurNotification().notifier(monstre.getNom() + " vous attaque.");
        this.donjon.ajouterEtreVivant(monstre);

        CombatMonstre combatMonstre = new CombatMonstre(this.donjon, personnage, monstre);
        EtreVivant etreVivantMort = combatMonstre.lancerCombat();
        this.donjon.supprimerEtreVivant(etreVivantMort);
        return etreVivantMort.equals(personnage);
    }

}

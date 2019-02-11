import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
        personnage.getServeurNotification().notifier("Vous êtes attaqué par "+monstre.getNom());
        this.donjon.ajouterEtreVivant(monstre);

        CombatMonstre combatMonstre = new CombatMonstre(this.donjon, personnage, monstre);
        EtreVivant etreVivantSortant = combatMonstre.lancerCombat();
        if(etreVivantSortant.getPointDeVie() == 0) {
            this.donjon.supprimerEtreVivant(etreVivantSortant);
            if (etreVivantSortant.equals(personnage))
                return true;
        }
        return false;
    }

}

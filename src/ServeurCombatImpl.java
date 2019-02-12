import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutionException;

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
        this.donjon.ajouterCombat(combatMonstre);
        EtreVivant etreVivantSortant = combatMonstre.lancerCombat();
        this.donjon.supprimerCombat(combatMonstre);

        if(etreVivantSortant.getPointDeVie() == 0) {
            this.donjon.supprimerEtreVivant(etreVivantSortant);
        }
        this.regagnerVieMax(personnage.getPieceActuelle());
        return (etreVivantSortant.equals(personnage) && etreVivantSortant.getPointDeVie()==0);
    }

    private void regagnerVieMax(Piece piece){
        if(!this.donjon.seDeroulerCombatPiece(piece)){
            for (EtreVivant etreVivant : this.donjon.getEtreVivantMemePiece(piece)){
                this.donjon.recupereEtreVivant(etreVivant.nomEtreVivant).regagnerPointDeVieMax();
                if(etreVivant instanceof Personnage) {
                    try {
                        ((Personnage) etreVivant).getServeurNotification().notifier(
                                "Il n'y a plus de combat en cours, vous regagnez vos points de vie maximum." +
                                        " Vous avez "+etreVivant.getPointDeVie() + " pdv.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

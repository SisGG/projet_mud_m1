import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/******************************************************************************
 * file     : src/ServeurCombatImpl.java
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
public class ServeurCombatImpl extends UnicastRemoteObject implements ServeurCombat {

    private Donjon donjon;

    /**
     * Constructeur de la classe ServeurCombatImpl.
     * @param donjon Base de données Donjon pour le serveur.
     * @throws RemoteException Exception déclenchée si ServeurCombatImpl ne crée pas l'objet.
     */
    ServeurCombatImpl(Donjon donjon) throws RemoteException {
        super();
        this.donjon = donjon;
    }

    /**
     * Permet de lancer un combat entre un Personnage et un Monstre.
     * @param personnage Personnage qui est attaquer par un Monstre.
     * @return Renvoie la valeur true si le personnage attaqué est mort, false sinon.
     * @throws RemoteException Exception déclenchée si la méthode n'est pas invoquée.
     */
    public boolean lancerCombatMonstre(Personnage personnage) throws RemoteException {
        Monstre monstre = new Monstre(personnage.getPieceActuelle());
        while ( this.donjon.nomEtreVivantExist(monstre.getNom()) ) {
            monstre = new Monstre(personnage.getPieceActuelle());
        }
        personnage.getServeurNotification().notifier("Vous êtes attaqué par "+monstre.getNom() +
                ". Entrez \'f\' pour fuir le combat.");
        this.donjon.ajouterEtreVivant(monstre);

        CombatMonstre combatMonstre = new CombatMonstre(this.donjon, personnage, monstre);
        this.donjon.ajouterCombat(combatMonstre);
        EtreVivant etreVivantSortant = combatMonstre.lancerCombat();
        this.donjon.supprimerCombat(combatMonstre);

        if ( etreVivantSortant.getPointDeVie() == 0 ) {
            this.donjon.supprimerEtreVivant(etreVivantSortant);
        }
        this.regagnerVieMax(personnage.getPieceActuelle());
        return (etreVivantSortant.equals(personnage) && etreVivantSortant.getPointDeVie()==0);
    }

    /**
     * Fais regagner tous les points de vie à tous les EtreVivants se trouvant dans la Pièce passée en paramètre.
     * @param piece Pièce dont tous les EtreVivants doivent regagner tous leurs points de vie.
     */
    private void regagnerVieMax(Piece piece){
        if ( !this.donjon.seDeroulerCombatPiece(piece) ) {
            for ( EtreVivant etreVivant : this.donjon.getEtreVivantMemePiece(piece) ) {
                this.donjon.recupereEtreVivant(etreVivant.nomEtreVivant).regagnerPointDeVieMax();
                if ( etreVivant instanceof Personnage ) {
                    try {
                        ((Personnage) etreVivant).getServeurNotification().notifier(
                                "Il n'y a plus de combat en cours, vous regagnez vos points de vie maximum." +
                                        " Vous avez "+etreVivant.getPointDeVie() + " pdv.");
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

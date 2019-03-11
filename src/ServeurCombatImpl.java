import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/******************************************************************************
 * file     : src/ServeurCombatImpl.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 3.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 18 Mars 2019
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
     */
    public void lancerCombatMonstre(Personnage personnage)  {
        Monstre monstre = new Monstre(personnage.getPieceActuelle());
        while ( this.donjon.nomEtreVivantExiste(monstre.getNom()) ) {
            monstre = new Monstre(personnage.getPieceActuelle());
        }
        this.donjon.ajouterEtreVivant(monstre);
        lancerCombat(monstre, personnage);
    }

    public void lancerCombat(EtreVivant attaquant, EtreVivant attaque) {
        afficherMessageCombat(attaquant, attaque, attaquant);
        afficherMessageCombat(attaquant, attaque, attaque);

        try {
            Thread.sleep(2000);
        }catch(Exception e){
            e.printStackTrace();
        }

        Combat combat = new Combat(this.donjon, attaquant, attaque);
        Piece pieceCombat = combat.recupererPieceCombat();
        this.donjon.ajouterCombat(combat);
        EtreVivant gagnant = combat.lancerCombat();
        if (gagnant != null) {
            if (combat.getEtreVivantAttaquant().getPointDeVie() == 0 && combat.getEtreVivantAttaque().equals(gagnant)) {
                this.afficherMessageVainqueur(combat.getEtreVivantAttaque(), combat.getEtreVivantAttaquant());
            } else if (combat.getEtreVivantAttaque().getPointDeVie() == 0 && combat.getEtreVivantAttaquant().equals(gagnant)) {
                this.afficherMessageVainqueur(combat.getEtreVivantAttaquant(), combat.getEtreVivantAttaque());
            }
        }
        this.donjon.supprimerCombat(combat);
        this.regagnerVieMax(pieceCombat);
    }

    private void afficherMessageVainqueur(EtreVivant vainqueur, EtreVivant vaincu){
        this.donjon.prevenirJoueurMemePiece(vainqueur, vaincu.getNom()+ " a été vaincu par "+ vainqueur.getNom()+".");
        this.donjon.supprimerEtreVivant(vaincu);
    }

    private void afficherMessageCombat(EtreVivant attaquant, EtreVivant attaque, EtreVivant etreNotifie){
        if( etreNotifie instanceof Personnage) {
            try {
                this.donjon.prevenirJoueurMemePiece(etreNotifie,"["+attaquant.getNom()+ " - "
                        +attaquant.getPointDeVie()+" pdv] attaque ["+attaque.getNom()+ " - "+attaque.getPointDeVie()+" pdv].") ;
                ((Personnage) etreNotifie).getServeurNotification().notifier("Appuyez sur \'Entrer\' pour fuir.");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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

    public void fuirCombat(EtreVivant etreVivant){
        Combat combat = this.getCombatEtre(etreVivant);
        if(combat.getEtreVivantAttaquant().equals(etreVivant)){
            combat.fuirCombat(etreVivant, combat.getEtreVivantAttaquant());
        }else{
            combat.fuirCombat(etreVivant, combat.getEtreVivantAttaque());
        }
    }

    public Combat getCombatEtre(EtreVivant etreVivant)  {
        for(Combat combat : this.donjon.getListeCombat()){
            if(combat.getEtreVivantAttaque().equals(etreVivant) || combat.getEtreVivantAttaquant().equals(etreVivant)){
                return combat;
            }
        }
        return null;
    }


    public boolean estEnCombat(EtreVivant etreVivant){
        return this.getCombatEtre(etreVivant) != null;
    }

}

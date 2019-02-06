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

    public synchronized HashMap<String, EtreVivant> LancerCombat(Personnage personnage) {
        Monstre monstre = new Monstre(personnage.getPieceActuelle());
        this.listeEtreVivant.put(monstre.getNom(), monstre);
        int resultatTour = 0;
        while ( resultatTour == 0 ) {
            resultatTour = this.effectuerTour(personnage, monstre);
        }
        return null;
    }

    private int effectuerTour(Personnage personnage, Monstre monstre) {
        int ciblePerdant1PDV = new Random().nextInt(1);
        if ( ciblePerdant1PDV == 0 ) {
            personnage.perdrePointDeVie();
        } else {
            monstre.perdrePointDeVie();
        }

        if ( personnage.getPointDeVie() == 0 ) {
            return 1;
        } else if ( monstre.getPointDeVie() == 0 ) {
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

    /**
     * Enregistre un etre vivant dans le serveur de combat.
     * @param etreVivant que l'on veut ajouter.
     */
    public synchronized void seConnecter(EtreVivant etreVivant) throws RemoteException {
        this.listeEtreVivant.put(etreVivant.getNom(), etreVivant);
    }

    /**
     * Enleve un etre vivant du serveur de combat lors de sa mort ou déconnexion.
     * @param etreVivant etreVivant que l'on veut enlever.
     */
    public synchronized void seDeconnecter(EtreVivant etreVivant) throws RemoteException{
        this.listeEtreVivant.remove(etreVivant.getNom(), etreVivant);
    }

    /**
     * Associe un serveur de notification à un personnage
     * @param personnage auquel on associe un serveur notification
     * @param serveurNotification qui sera associé au personnage
     * @throws RemoteException si l'appel de méthode distante rencontre un problème
     */
    public void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException {
        Personnage personnageListe = (Personnage) this.listeEtreVivant.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(serveurNotification);
    }

    /**
     * Supprime le serveur de notification d'un personnage
     * @param personnage auquel on enlève un serveur notification
     * @throws RemoteException si l'appel de méthode distante rencontre un problème
     */
    public void enleverNotification(Personnage personnage) throws RemoteException {
        Personnage personnageListe = (Personnage) this.listeEtreVivant.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(null);
    }


}

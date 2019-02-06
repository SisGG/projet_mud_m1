import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ServeurCombatImpl extends UnicastRemoteObject implements ServeurCombat {

    private static long serialVersionUID = 2L;
    private HashMap<String, EtreVivant> listeEtreVivant;

    ServeurCombatImpl() throws RemoteException {
        super();
        this.listeEtreVivant = new HashMap<>();
    }

    public void LancerCombat(Personnage personnage) {
    }

    public EtreVivant faireGagnerPointDeVie(EtreVivant etreVivant, int pointDeVieEnPlus) {
         etreVivant.augmenterPointDeVieActuel(pointDeVieEnPlus);
         return  etreVivant;
    }

    public HashMap<String, EtreVivant> faireRegagnerPointDeVieMaxPiece(EtreVivant etreVivant){
        for(EtreVivant etreVivant1 : this.listeEtreVivant.values()){
            if (etreVivant1.getPieceActuelle().equals(etreVivant.getPieceActuelle())){
                etreVivant1.regagnerPointDeVieMax();
            }
        }
        return this.listeEtreVivant;
    }

    /**
     * Enregistre un etre vivant dans le serveur de combat.
     * @param etreVivant que l'on veut ajouter.
     */
    public synchronized void seConnecter(EtreVivant etreVivant) throws RemoteException {
        this.listeEtreVivant.put(etreVivant.getNomEtreVivant(), etreVivant);
    }

    /**
     * Enleve un etre vivant du serveur de combat lors de sa mort ou déconnexion.
     * @param etreVivant etreVivant que l'on veut enlever.
     */
    public synchronized void seDeconnecter(EtreVivant etreVivant) throws RemoteException{
        this.listeEtreVivant.remove(etreVivant.getNomEtreVivant(), etreVivant);
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

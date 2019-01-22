import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/******************************************************************************
 * file     : src/ServeurDiscussionImpl.java
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
public class ServeurDiscussionImpl extends UnicastRemoteObject implements ServeurDiscussion {

    private static long serialVersionUID = 0L;

    private HashMap<String,Personnage> listePersonnage;


    /**
     * Instanciation de la listepersonnage
     * @throws RemoteException
     */
    public ServeurDiscussionImpl() throws RemoteException {
        super();
        this.listePersonnage = new HashMap<>();
    }

    /**
     * Envoyer un message à un personnage disponible dans listePersonnage qui se trouve aussi dans la même pièce
     * @param personnage personnage qui envoie le message
     * @param message chaine de caractère à envoyer
     * @throws RemoteException
     */
    public void discuter(Personnage personnage, String message) throws RemoteException{
        for (Personnage personnage1 : this.listePersonnage.values() ){
            if ( personnage1.getPieceActuelle().equals(personnage.getPieceActuelle()) ) {
                try {
                    personnage1.getServeurNotification().notifier(personnage.getNomPersonnage() + ": " + message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Ajouter une instance Personnage dans listePersonnage
     * @param personnage
     */
    public synchronized void seConnecter(Personnage personnage) {
        this.listePersonnage.put(personnage.getNomPersonnage(), personnage);
    }

    /**
     * Associer un  serveur de notification à un personnage
     * @param personnage personnage concerné par l'instanciation
     * @param serveurNotification serveur qui initialisera l'attribut serveurNotification de personnage
     * @throws RemoteException
     */
    public void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(serveurNotification);
    }

    /**
     * Remettre à null le serveur notification d'un personnage
     * @param personnage personnage concerné
     * @throws RemoteException
     */
    public void enleverNotification(Personnage personnage) throws RemoteException {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(null);
    }

    /**
     * Se déconnecter du serveur du discussion en remettant à null le serveur de notification et en enlevant le personnage
     * de listePersonnage
     * @param personnage personnage à déconnecter
     */
    public void seDeconnecter(Personnage personnage) {
        try{
            this.enleverNotification(personnage);
            this.listePersonnage.remove(personnage.getNomPersonnage());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  Mettre à jour l'instance d'un personnage dans la listePersonnage
     * @param personnage personnage à mettre à jour
     * @throws RemoteException
     */
    public void miseAJourPersonnage(Personnage personnage) throws RemoteException {
        this.listePersonnage.replace(personnage.getNomPersonnage(), personnage);
    }

}

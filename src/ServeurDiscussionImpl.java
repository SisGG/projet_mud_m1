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

    private Donjon donjon;


    /**
     * Instanciation de la listepersonnage
     * @throws RemoteException
     */
    public ServeurDiscussionImpl(Donjon donjon) throws RemoteException {
        super();
        this.donjon = donjon;
    }

    /**
     * Envoyer un message à un personnage disponible dans listePersonnage qui se trouve aussi dans la même pièce
     * @param personnage personnage qui envoie le message
     * @param message chaine de caractère à envoyer
     * @throws RemoteException
     */
    public void discuter(Personnage personnage, String message) throws RemoteException{
        for (Personnage personnage1 : this.donjon.getPersonnageMemePiece(personnage) ){
            try {
                personnage1.getServeurNotification().notifier(personnage.getNomPersonnage() + ": " + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

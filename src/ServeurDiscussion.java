import java.rmi.Remote;
import java.rmi.RemoteException;

/******************************************************************************
 * file     : src/ServeurDiscussion.java
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
public interface ServeurDiscussion extends Remote {

    void discuter(Personnage personnageEmetteur, String message) throws RemoteException;

    void seConnecter(Personnage personnage) throws RemoteException;

    void seDeconnecter(Personnage personnage) throws RemoteException;

    void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException;

    void enleverNotification(Personnage personnage) throws RemoteException;

    void miseAJourPersonnage(Personnage personnage) throws RemoteException;

}
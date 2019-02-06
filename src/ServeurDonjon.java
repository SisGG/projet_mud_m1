import java.rmi.Remote;
import java.rmi.RemoteException;

/******************************************************************************
 * file     : src/ServeurDonjon.java
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
public interface ServeurDonjon extends Remote {

    Personnage seConnecter(String nomPersonnage) throws RemoteException;

    Personnage seDeplacer(Personnage personnage, String direction) throws RemoteException;

    void seDeconnecter(Personnage personnage) throws  RemoteException;

    void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException;

    boolean existeNomPersonnage(String nomPersonnage) throws RemoteException;

}

import java.rmi.Remote;
import java.rmi.RemoteException;

/******************************************************************************
 * file     : src/ServeurSysteme.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 4.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 25 Mars 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/

public interface ServeurSysteme extends Remote {

    String getNomServeurDonjon() throws RemoteException;

    String getNomServeurDiscussion() throws RemoteException;

    String getNomServeurCombat() throws RemoteException;

    String getNomServeurPersistance() throws RemoteException;

    int getTailleDonjon() throws RemoteException;

}

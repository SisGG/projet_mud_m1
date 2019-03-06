import java.rmi.Remote;
import java.rmi.RemoteException;

/******************************************************************************
 * file     : src/ServeurNotification.java
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
public interface ServeurNotification extends Remote {

    void notifier(String notification) throws RemoteException;

    String demanderAction() throws RemoteException;

}
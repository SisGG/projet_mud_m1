/* ****************************************************************************
 *
 * Name File : src/ServeurNotification.java
 * Authors   : OLIVIER Thomas
 *             BOURAKADI Reda
 *             LAPEYRADE Sylvain
 *
 * Location  : UPSSITECH - University Paul Sabatier
 * Date      : Janvier 2019
 *
 *                        This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *
 * ***************************************************************************/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServeurNotification extends Remote {

    void notifier(String notification) throws RemoteException;
}
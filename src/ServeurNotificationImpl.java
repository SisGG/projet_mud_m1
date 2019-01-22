/* ****************************************************************************
 *
 * Name File : src/ServeurNotificationImpl.java
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

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServeurNotificationImpl extends UnicastRemoteObject implements ServeurNotification {
    /**
     * Constructeur de serveur notification Implémentation
     * @throws RemoteException en cas d'erreur sur un appel de méthode distant
     */
    ServeurNotificationImpl() throws RemoteException {
        super();
    }

    /**
     * Envoie une chaine de caractère au Client
     * @param notication Chaine de caractère à envoyer au client
     */
    public void notifier(String notication){
        System.out.println("\r" + notication);
    }
}

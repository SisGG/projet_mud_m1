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

    protected ServeurNotificationImpl() throws RemoteException {
        super();
    }

    public void notifier(String notication){
        System.out.println("\r" + notication);
    }
}

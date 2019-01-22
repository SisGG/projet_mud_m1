/* ****************************************************************************
 *
 * Name File : src/ServeurDiscussion.java
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

public interface ServeurDiscussion extends Remote {

    void discuter(Personnage personnageEmetteur, String message) throws RemoteException;

    void seConnecter(Personnage personnage) throws RemoteException;

    void seDeconnecter(Personnage personnage) throws RemoteException;

    void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException;

    void enleverNotification(Personnage personnage) throws RemoteException;

    void miseAJourPersonnage(Personnage personnage) throws RemoteException;

}
/* ****************************************************************************
 *
 * Name File : src/ServeurDonjon.java
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
import java.util.HashMap;

public interface ServeurDonjon extends Remote {

    Personnage seConnecter(String nomPersonnage) throws RemoteException;

    Personnage seDeplacer(Personnage personnage, String direction) throws RemoteException;

    void seDeconnecter(Personnage personnage) throws  RemoteException;

    void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException;

    void enleverNotification(Personnage personnage) throws RemoteException;

    boolean existeNomPersonnage(String nomPersonnage) throws RemoteException;

}

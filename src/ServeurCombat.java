import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/******************************************************************************
 * file     : src/ServeurCombat.java
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
public interface ServeurCombat extends Remote {

    HashMap<String, EtreVivant> LancerCombat(Personnage personnage) throws RemoteException;

    void seConnecter(EtreVivant etreVivant) throws RemoteException;

    void seDeconnecter(EtreVivant etreVivant) throws  RemoteException;

    void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException;

    void enleverNotification(Personnage personnage) throws RemoteException;
}

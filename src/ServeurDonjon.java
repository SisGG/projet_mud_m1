import java.rmi.Remote;
import java.rmi.RemoteException;

/******************************************************************************
 * file     : src/ServeurDonjon.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 3.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 18 Mars 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public interface ServeurDonjon extends Remote {

    Personnage seConnecter(String nomPersonnage) throws RemoteException;

    Personnage seDeplacer(Personnage personnage, String direction, ServeurCombat serveurCombat) throws RemoteException;

    void afficherEtreVivantPiece(Personnage personnage) throws  RemoteException;

    void afficherCombatPiece(Personnage personnage) throws  RemoteException;

    void deconnecter(Personnage personnage) throws  RemoteException;

    void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException;

    boolean existeNomEtreVivant(String nomPersonnage) throws RemoteException;

    Personnage getPersonnage(String nomEtreVivant)throws RemoteException;

    Monstre getMonstre(String nomEtreVivant)throws RemoteException;

    }

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface ServeurDiscussion extends Remote {


    void discuter(Personnage personnageEmetteur, String message) throws RemoteException;

    void seConnecter(Personnage personnage) throws RemoteException;

    void seDeconnecter(Personnage personnage) throws RemoteException;

    void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException;

    void enleverNotification(Personnage personnage) throws RemoteException;

    void miseAJourPersonnage(Personnage personnage) throws RemoteException;

}
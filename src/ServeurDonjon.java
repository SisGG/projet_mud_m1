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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface ServeurDonjon extends Remote {

    Personnage seConnecter(String nomPersonnage) throws RemoteException;

    Personnage seDeplacer(Personnage personnage, String direction) throws RemoteException;

    void seDeconnecter(Personnage personnage) throws  RemoteException;

    void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException;

    void enleverNotification(Personnage personnage) throws RemoteException;

    Personnage getPersonnage(String nomPersonnage) throws RemoteException;

    void prevenirEntrerPersonnageMemePiece(Personnage personnage) throws RemoteException;

    HashMap<String,Personnage> getListePersonnage() throws RemoteException;
    }

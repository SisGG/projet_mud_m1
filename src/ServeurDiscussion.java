import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public interface ServeurDiscussion extends Remote {


    void discuter(Personnage personnageEmetteur, String message) throws RemoteException;

    void seConnecter(Personnage personnage) throws RemoteException;

    void seDeconnecter(Personnage personnage) throws RemoteException;

}
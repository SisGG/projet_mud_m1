import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface ServeurCombat extends Remote {

    HashMap<String, EtreVivant> LancerCombat(Personnage personnage) throws RemoteException;

    void seConnecter(EtreVivant etreVivant) throws RemoteException;

    void seDeconnecter(EtreVivant etreVivant) throws  RemoteException;

    void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException;

    void enleverNotification(Personnage personnage) throws RemoteException;
}

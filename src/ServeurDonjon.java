import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServeurDonjon extends Remote {
    Personnage seConnecter(String nomPersonnage) throws RemoteException;

    Personnage seDeplacer(Personnage personnage, String direction) throws RemoteException;

    void seDeconnecter(Personnage personnage) throws  RemoteException;

}

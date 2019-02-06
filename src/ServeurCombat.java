import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServeurCombat extends Remote {

    void LancerCombat(Personnage personnage) throws RemoteException;
}

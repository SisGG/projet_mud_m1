import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServeurNotification extends Remote {

    void notifier(String notification) throws RemoteException;
}
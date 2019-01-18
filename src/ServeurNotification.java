import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServeurNotification extends Remote {

    public void notifier(String notification) throws RemoteException;
}

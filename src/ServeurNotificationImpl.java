import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServeurNotificationImpl extends UnicastRemoteObject implements ServeurNotification {

    ServeurNotificationImpl() throws RemoteException {
        super();
    }

    public void notifier(String notication){
        System.out.println("\r" + notication);
    }
}

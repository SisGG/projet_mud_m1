import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServeurDiscussionImpl extends UnicastRemoteObject implements ServeurDiscussion {

    private static long serialVersionUID = 0L;

    public ServeurDiscussionImpl() throws RemoteException {
    }

    public void discuter(Personnage personnageEmetteur, String message) {
    }

}

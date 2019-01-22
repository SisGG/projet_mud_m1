import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServeurNotificationImpl extends UnicastRemoteObject implements ServeurNotification {
    /**
     * Constructeur de serveur notification Implémentation
     * @throws RemoteException en cas d'erreur sur un appel de méthode distant
     */
    ServeurNotificationImpl() throws RemoteException {
        super();
    }

    /**
     * Envoie une chaine de caractère au Client
     * @param notication Chaine de caractère à envoyer au client
     */
    public void notifier(String notication){
        System.out.println("\r" + notication);
    }
}

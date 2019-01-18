import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServeurDiscussionImpl extends UnicastRemoteObject implements ServeurDiscussion {

    private static long serialVersionUID = 0L;
    private java.util.ArrayList listeJoueurs;

    public ServeurDiscussionImpl() throws RemoteException {
    }

    public void discuter(Personnage personnageEmetteur, String message) {

    }

    public void seConnecter(Personnage personnage) {
        listeJoueurs.add(personnage);
        System.out.println(listeJoueurs.size());
    }

    public void seDeconnecter(Personnage personnage) {
        listeJoueurs.remove(personnage);
        System.out.println(listeJoueurs.size());
    }

    public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(1099);
        ServeurDiscussionImpl serveurDiscussionImpl = new ServeurDiscussionImpl();
        Naming.rebind("serveurDiscussion",serveurDiscussionImpl);
        System.out.println("Serveur de chat lancé");
    }

}

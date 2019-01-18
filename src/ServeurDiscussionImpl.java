import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServeurDiscussionImpl extends UnicastRemoteObject implements ServeurDiscussion {

    private static long serialVersionUID = 0L;
    //private java.util.ArrayList listeJoueurs;
    //private List<Personnage> listeJoueurs = new ArrayList<>();
    private HashMap<String,Personnage> listePersonnage;

    public ServeurDiscussionImpl() throws RemoteException {
        super();
        listePersonnage = new HashMap<>();
    }

    public void discuter(Personnage personnageEmetteur, String message) throws RemoteException{
        for ( Personnage personnageItter : this.listePersonnage.values() ){
            if ( personnageItter.getPieceActuelle()== personnageEmetteur.getPieceActuelle() ) {
                System.out.println("wazbiiiiiii");
                ServeurNotification serveurNotification = personnageEmetteur.getServeurNotification();
                try {
                    serveurNotification.notifier(personnageEmetteur.getNomPersonnage() + " : " + message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void seConnecter(Personnage personnage, ServeurNotification serveurNotification) {
        listePersonnage.put(personnage.getNomPersonnage(),personnage);
        //listePersonnage.get(personnage.getNomPersonnage()).setServeurNotification(serveurNotification);
        System.out.println(listePersonnage.size());
    }

    public void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(serveurNotification);
    }

    public void seDeconnecter(Personnage personnage) {
        listePersonnage.remove(personnage.getNomPersonnage());
        System.out.println(listePersonnage.size());
    }


}

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ServeurDiscussionImpl extends UnicastRemoteObject implements ServeurDiscussion {

    private static long serialVersionUID = 0L;

    private HashMap<String,Personnage> listePersonnage;

    public ServeurDiscussionImpl() throws RemoteException {
        super();
        this.listePersonnage = new HashMap<>();
    }

    public void discuter(Personnage personnage, String message) throws RemoteException{
        for (Personnage personnage1 : this.listePersonnage.values() ){
            if ( personnage1.getPieceActuelle().equals(personnage.getPieceActuelle()) ) {
                try {
                    personnage1.getServeurNotification().notifier(personnage.getNomPersonnage() + ": "
                            + message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void seConnecter(Personnage personnage) {
        this.listePersonnage.put(personnage.getNomPersonnage(),personnage);
    }

    public synchronized void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(serveurNotification);
    }

    public synchronized void enleverNotification(Personnage personnage) throws RemoteException {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(null);
    }

    public synchronized void seDeconnecter(Personnage personnage) {
        try{
            this.enleverNotification(personnage);
            this.listePersonnage.remove(personnage.getNomPersonnage());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

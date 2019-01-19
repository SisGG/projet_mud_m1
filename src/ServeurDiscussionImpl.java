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
            if(personnage1.getPieceActuelle().toString().equals(personnage.getPieceActuelle().toString())
                && !personnage1.toString().equals(personnage.toString())){
                try {
                    personnage1.getServeurNotification().notifier(personnage.getNomPersonnage() + ": "
                            + message.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void seConnecter(Personnage personnage) {
        this.listePersonnage.put(personnage.getNomPersonnage(),personnage);
    }

    public void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(serveurNotification);
    }

    public void enleverNotification(Personnage personnage) throws RemoteException {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(null);
    }

    public synchronized void seDeconnecter(Personnage personnage) {
        this.listePersonnage.remove(personnage.getNomPersonnage());
        try{
            this.enleverNotification(personnage);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void  majListe(HashMap<String,Personnage> listePersonnage)throws RemoteException{
        this.listePersonnage = listePersonnage;
    }

}

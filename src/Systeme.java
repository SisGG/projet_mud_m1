import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Systeme {

    private int tailleDonjon = 5;
    private ServeurDonjonImpl serveurDonjon;

    public void lancerServeurDiscussion() {
        try {
            LocateRegistry.createRegistry(1098);
            ServeurDiscussionImpl serveurDiscussionImpl = new ServeurDiscussionImpl();
            Naming.rebind("serveurDiscussion",serveurDiscussionImpl);
            System.out.println("Serveur de chat déclaré");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void lancerServeurDonjon() {
        try {
            LocateRegistry.createRegistry(1099);
            this.serveurDonjon = new ServeurDonjonImpl(this.tailleDonjon);
            Naming.rebind("ServeurDonjon", this.serveurDonjon);
            System.out.println("Serveur donjon déclaré.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Systeme systeme = new Systeme();
        systeme.lancerServeurDonjon();
        systeme.lancerServeurDiscussion();
    }
}
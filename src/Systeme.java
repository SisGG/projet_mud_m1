import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Systeme {

    private int tailleDonjon = 5;
    private ServeurDonjonImpl serveurDonjon;
    private ServeurDiscussionImpl serveurDiscussion;

    public void lancerServeurDiscussion() {
        try {
            this.serveurDiscussion = new ServeurDiscussionImpl();
            Naming.rebind("ServeurDiscussion", this.serveurDiscussion);
            System.out.println("Serveur discussion déclaré.");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void lancerServeurDonjon() {
        try {
            this.serveurDonjon = new ServeurDonjonImpl(this.tailleDonjon);
            Naming.rebind("ServeurDonjon", this.serveurDonjon);
            System.out.println("Serveur donjon déclaré.");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Systeme systeme = new Systeme();
        try {
            LocateRegistry.createRegistry(1099);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        systeme.lancerServeurDonjon();
        systeme.lancerServeurDiscussion();
    }
}
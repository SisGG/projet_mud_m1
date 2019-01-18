import javax.sound.midi.Soundbank;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Systeme {

    private int tailleDonjon = 5;
    private ServeurDonjonImpl serveurDonjon;
    private ServeurDiscussionImpl serveurDiscussion;

    public void lancerServeurDiscussion() { }

    private void lancerServeurDonjon() {
        try {
            LocateRegistry.createRegistry(1099);
            this.serveurDonjon = new ServeurDonjonImpl(this.tailleDonjon);
            Naming.rebind("ServeurDonjon", this.serveurDonjon);
            System.out.println("Serveur donjon déclaré.");

            this.serveurDiscussion = new ServeurDiscussionImpl();
            Naming.rebind("ServeurDiscussion",this.serveurDiscussion);
            System.out.println("Serveur discussion déclaré");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Systeme systeme = new Systeme();
        systeme.lancerServeurDonjon();
    }
}
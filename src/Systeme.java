import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/******************************************************************************
 * file     : src/Systeme.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 1.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 30 Janvier 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class Systeme {

    private int tailleDonjon = 5;

    /**
     * Démarre les serveurs Donjon et Discussion en les créant après leur avoir
     * attribué un nom avec "naming.rebind"
     */
    private void lancerSysteme() {
        try {
            LocateRegistry.createRegistry(1099);
            ServeurDonjon serveurDonjon = new ServeurDonjonImpl(this.tailleDonjon);
            Naming.rebind("ServeurDonjon", serveurDonjon);
            System.out.println("Le serveur donjon est démarré.");

            ServeurDiscussion serveurDiscussion = new ServeurDiscussionImpl();
            Naming.rebind("ServeurDiscussion", serveurDiscussion);
            System.out.println("Le serveur discussion est démarré.");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Systeme systeme = new Systeme();
        systeme.lancerSysteme();
    }
}
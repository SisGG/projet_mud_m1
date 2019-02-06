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

    private static final int tailleDonjon = 5;

    private Systeme() {
        try {
            LocateRegistry.createRegistry(1099);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Démarre un serveur donjon en lui attribuant un nom spécifique.
     */
    private void lancerServeurDonjon() {
        try {
            ServeurDonjon serveurDonjon = new ServeurDonjonImpl(tailleDonjon);
            Naming.rebind("ServeurDonjon", serveurDonjon);
            System.out.println("Le serveur donjon est démarré.");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    /**
     * Démarre un serveur discussion en lui attribuant un nom spécifique.
     */
    private void lancerServeurDiscussion() {
        try {
            ServeurDiscussion serveurDiscussion = new ServeurDiscussionImpl();
            Naming.rebind("ServeurDiscussion", serveurDiscussion);
            System.out.println("Le serveur discussion est démarré.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public static void main(String[] args) {
        Systeme systeme = new Systeme();
        systeme.lancerServeurDonjon();
        systeme.lancerServeurDiscussion();
    }
}
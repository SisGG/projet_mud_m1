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
    private Donjon donjon;

    /**
     * Constructeur de la classe Systeme.
     * @param donjon Base de données d'un donjon commun à tous les serveurs.
     */
    private Systeme(Donjon donjon) {
        this.donjon = donjon;
        try {
            LocateRegistry.createRegistry(1099);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Démarre un serveur donjon en lui attribuant un nom spécifique.
     */
    private void lancerServeurDonjon() {
        try {
            ServeurDonjon serveurDonjon = new ServeurDonjonImpl(this.donjon);
            Naming.rebind("ServeurDonjon", serveurDonjon);
            System.out.println("Le serveur donjon est démarré.");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    /**
     * Démarre un serveur discussion en lui attribuant un nom spécifique.
     */
    private void lancerServeurDiscussion() {
        try {
            ServeurDiscussion serveurDiscussion = new ServeurDiscussionImpl(this.donjon);
            Naming.rebind("ServeurDiscussion", serveurDiscussion);
            System.out.println("Le serveur discussion est démarré.");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Démarre un serveur combat en lui attribuant un nom spécifique.
     */
    private void lancerServeurCombat() {
        try {
            ServeurCombat serveurCombat = new ServeurCombatImpl(this.donjon);
            Naming.rebind("ServeurCombat", serveurCombat);
            System.out.println("Le serveur combat est démarré.");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        Donjon donjon = new Donjon(tailleDonjon);
        Systeme systeme = new Systeme(donjon);
        systeme.lancerServeurDonjon();
        systeme.lancerServeurDiscussion();
        systeme.lancerServeurCombat();
    }
}
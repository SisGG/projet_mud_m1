import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/******************************************************************************
 * file     : src/ServeurSystemeImpl.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 4.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 25 Mars 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class ServeurSystemeImpl extends UnicastRemoteObject implements ServeurSysteme {

    private static final int tailleDonjon = 5;
    private Donjon donjon;
    private static final BaseDeDonnees baseDeDonnees = new BDFile("DataBase.data");

    /**
     * Constructeur de la classe ServeurSystemeImpl.
     * @param donjon Base de données d'un donjon commun à tous les serveurs.
     */
    private ServeurSystemeImpl(Donjon donjon) throws RemoteException {
        super();
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

    /**
     * Démarre un serveur persistance.
     */
    private void lancerServeurPersistance() {
        try {
            ServeurPersistance serveurPersistance = new ServeurPersistanceImpl(baseDeDonnees);
            Naming.rebind("ServeurPersistance", serveurPersistance);
            System.out.println("Le serveur persistance est démarré.");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        Donjon donjon = new Donjon(tailleDonjon);
        try {
            ServeurSystemeImpl systeme = new ServeurSystemeImpl(donjon);
            systeme.lancerServeurDonjon();
            systeme.lancerServeurDiscussion();
            systeme.lancerServeurCombat();
            systeme.lancerServeurPersistance();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
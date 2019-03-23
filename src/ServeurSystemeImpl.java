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
    private static final String nomServeurSysteme = "ServeurSysteme";
    private static final String nomServeurDonjon = "ServeurDonjon";
    private static final String nomServeurDiscussion = "ServeurDiscussion";
    private static final String nomServeurCombat = "ServeurCombat";
    private static final String nomServeurPersistance = "ServeurPersistance";
    private static final BaseDeDonnees baseDeDonnees = new BDFile("DataBase.data");
    private Donjon donjon;

    /**
     * Constructeur de la classe ServeurSystemeImpl.
     *
     * @param donjon Base de données d'un donjon commun à tous les serveurs.
     */
    private ServeurSystemeImpl(Donjon donjon) throws RemoteException {
        super();
        this.donjon = donjon;
        LocateRegistry.createRegistry(1099);
        System.out.println("Le serveur système est démarré.");
    }

    /**
     * Démarre plusieurs serveurs donjon en fonction de la taille
     * du donjon et en leur attribuant un nom spécifique.
     */
    private void lancerServeurDonjon() {
        try {
            for (int i = 0; i < tailleDonjon; i++) {
                Naming.rebind(nomServeurDonjon + i, new ServeurDonjonImpl(this.donjon));
                System.out.println("Le serveur donjon n°" + i + " est démarré.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Démarre un serveur discussion en lui attribuant un nom spécifique.
     */
    private void lancerServeurDiscussion() {
        try {
            Naming.rebind(nomServeurDiscussion, new ServeurDiscussionImpl(this.donjon));
            System.out.println("Le serveur discussion est démarré.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Démarre un serveur combat en lui attribuant un nom spécifique.
     */
    private void lancerServeurCombat() {
        try {
            Naming.rebind(nomServeurCombat, new ServeurCombatImpl(this.donjon));
            System.out.println("Le serveur combat est démarré.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Démarre un serveur persistance.
     */
    private void lancerServeurPersistance() {
        try {
            Naming.rebind(nomServeurPersistance, new ServeurPersistanceImpl(baseDeDonnees));
            System.out.println("Le serveur persistance est démarré.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public String getNomServeurDonjon() {
        return nomServeurDonjon;
    }

    public String getNomServeurDiscussion() {
        return nomServeurDiscussion;
    }

    public String getNomServeurCombat() {
        return nomServeurCombat;
    }

    public String getNomServeurPersistance() {
        return nomServeurPersistance;
    }

    public int getTailleDonjon() {
        return tailleDonjon;
    }

    public static void main(String[] args) {
        Donjon donjon = new Donjon(tailleDonjon);
        try {
            ServeurSystemeImpl serveurSysteme = new ServeurSystemeImpl(donjon);
            Naming.rebind(nomServeurSysteme, serveurSysteme);
            serveurSysteme.lancerServeurDonjon();
            serveurSysteme.lancerServeurDiscussion();
            serveurSysteme.lancerServeurCombat();
            serveurSysteme.lancerServeurPersistance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
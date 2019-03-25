import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/******************************************************************************
 * file     : src/ServeurPersistanceImpl.java
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
public class ServeurPersistanceImpl extends UnicastRemoteObject implements ServeurPersistance {

    private static final boolean debugTime = true;
    private BaseDeDonnees baseDeDonnees;

    ServeurPersistanceImpl(BaseDeDonnees baseDeDonnees) throws RemoteException {
        super();
        this.baseDeDonnees = baseDeDonnees;
    }

    public void sauvegarderPersonnage(Personnage personnage) {
        long time = System.currentTimeMillis();
        this.baseDeDonnees.ajout(personnage);
        this.showTime("Temps de sauvegarde", time);
    }

    public Personnage recuperePersonnage(String nomPersonnage) {
        Personnage personnage;
        long time = System.currentTimeMillis();
        personnage = this.baseDeDonnees.recupere(nomPersonnage);
        this.showTime("Temps de récupération", time);
        return personnage;
    }

    public void supprimerPersonnage(String nomPersonnage) {
        long time = System.currentTimeMillis();
        this.baseDeDonnees.supprime(nomPersonnage);
        this.showTime("Temps de suppression", time);
    }

    private void showTime(String message, long time) {
        if (debugTime) {
            System.out.println(message + " : " + (System.currentTimeMillis() - time) + " ms");
        }
    }
}

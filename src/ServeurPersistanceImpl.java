import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/******************************************************************************
 * file     : src/ServeurPersistanceImpl.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 3.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 18 Mars 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class ServeurPersistanceImpl extends UnicastRemoteObject implements ServeurPersistance {

    private HashMap<String,Personnage> baseDeDonnees;

    ServeurPersistanceImpl() throws RemoteException {
        super();
        this.baseDeDonnees = new HashMap<>();
    }

    public void sauvegarderPersonnage(Personnage personnage) {
        if ( !this.baseDeDonnees.containsKey(personnage.getNom()) ) {
            this.baseDeDonnees.put(personnage.getNom(), personnage);
        } else {
            this.baseDeDonnees.replace(personnage.getNom(), personnage);
        }
    }

    public Personnage recuperePersonnage(String nomPersonnage) {
        if ( this.baseDeDonnees.containsKey(nomPersonnage) ) {
            return this.baseDeDonnees.get(nomPersonnage);
        }
        return null;
    }

    public void supprimerPersonnage(Personnage personnage) {
        this.baseDeDonnees.remove(personnage.getNom());
    }
}

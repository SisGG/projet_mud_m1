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

    private BaseDeDonnees baseDeDonnees;

    ServeurPersistanceImpl(BaseDeDonnees baseDeDonnees) throws RemoteException {
        super();
        this.baseDeDonnees = baseDeDonnees;
    }

    public void sauvegarderPersonnage(Personnage personnage) {
        this.baseDeDonnees.put(personnage);
    }

    public Personnage recuperePersonnage(String nomPersonnage) {
        return this.baseDeDonnees.get(nomPersonnage);
    }

    public void supprimerPersonnage(String nomPersonnage) {
        this.baseDeDonnees.remove(nomPersonnage);
    }
}

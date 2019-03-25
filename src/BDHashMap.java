import java.util.HashMap;

/******************************************************************************
 * file     : src/BDHashMap.java
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
public class BDHashMap implements BaseDeDonnees {

    private HashMap<String, Personnage> baseDeDonnees;

    /**
     * Constructeur de la classe BDHashMap.
     */
    BDHashMap() {
        this.baseDeDonnees = new HashMap<>();
    }

    /**
     * @see BaseDeDonnees
     * @param personnage Personnage à sauvegarder.
     */
    public void put(Personnage personnage) {
        this.baseDeDonnees.put(personnage.getNom(), personnage);
    }

    /**
     * @see BaseDeDonnees
     * @param nomPersonnage Nom du personnage à supprimer.
     */
    public void remove(String nomPersonnage) {
        this.baseDeDonnees.remove(nomPersonnage);
    }

    /**
     * @see BaseDeDonnees
     * @param nomPersonnage Nom du personnage à récupérer.
     * @return Renvoie le personnage ou null s'il n'existe pas.
     */
    public Personnage get(String nomPersonnage) {
        return this.baseDeDonnees.get(nomPersonnage);
    }
}

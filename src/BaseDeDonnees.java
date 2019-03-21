/******************************************************************************
 * file     : src/BaseDeDonnees.java
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
public interface BaseDeDonnees {

    void put(Personnage personnage);

    void remove(String nomPersonnage);

    Personnage get(String nomPersonnage);

}

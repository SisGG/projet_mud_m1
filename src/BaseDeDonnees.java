/******************************************************************************
 * file     : src/BaseDeDonnees.java
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
public interface BaseDeDonnees {

    /**
     * Sauvegarde un personnage en base de donnée.
     * Le personnage est remplacer s'il existe déjà dans la base de données.
     *
     * @param personnage Personnage à enregistrer en base de données.
     */
    void ajout(Personnage personnage);

    /**
     * Supprime un personnage dont le nom de personnage corresponds au nom de personnage passé en paramètre.
     *
     * @param nomPersonnage Chaine de caractère du personnage à supprimer dans la base de données.
     */
    void supprime(String nomPersonnage);

    /**
     * Récupère le personnage dont le nom de personnage est passé en paramètre.
     *
     * @param nomPersonnage Chaine de caractère du personnage à supprimer dans la base de données.
     * @return Renvoie un personnage ou null s'il n'existe pas dans la base de données.
     */
    Personnage recupere(String nomPersonnage);

}


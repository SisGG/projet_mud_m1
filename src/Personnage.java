/******************************************************************************
 * file     : src/Personnage.java
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
public class Personnage extends EtreVivant {

    private static final int pointDeVieMax = 10;
    private ServeurNotification serveurNotification;

    /**
     * Constructeur de la classe Personnage.
     * @param nomPersonnage Chaine de caractère du Personnage.
     */
    Personnage(String nomPersonnage) {
        super(nomPersonnage, pointDeVieMax);
    }

    /**
     * Associe un Serveur Notification au Personnage.
     * @param serveurNotification ServeurNotification que l'on associe au personnage.
     */
    void setServeurNotification(ServeurNotification serveurNotification) {
        this.serveurNotification = serveurNotification;
    }

    /**
     * @return Renvoie le ServeurNotification associé au personnage.
     */
    ServeurNotification getServeurNotification() {
        return this.serveurNotification;
    }

    /**
     * Renvoie une chaine de caractère représentant le Personnage
     * @return Renvoie une chaine de caractère représentant le Personnage.
     */
    public String toString() {
        return "Personnage[" + this.nomEtreVivant + "]";
    }
}
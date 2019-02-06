import java.io.Serializable;

/******************************************************************************
 * file     : src/Personnage.java
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
public class Personnage extends EtreVivant {

    private ServeurNotification serveurNotification;
    private static final int pointDevieMax= 10;

    /**
     * Constructeur de la classe Personnage.
     * @param nomPersonnage Nom du personnage.
     */
    Personnage(String nomPersonnage) {
        super(nomPersonnage, pointDevieMax, null);
    }

    /**
     * Récupère le nom du personnage.
     * @return Renvoie la chaine de caractère du nom de personnage.
     */
    String getNomPersonnage() {
        return this.nomEtreVivant;
    }

    /***
     * Associe un Serveur Notification au personnage
     * @param serveurNotification que l'on associe au personnage
     */
    void setServeurNotification(ServeurNotification serveurNotification) {
        this.serveurNotification = serveurNotification;
    }

    /***
     * @return Serveur notification associé au personnage
     */
    ServeurNotification getServeurNotification() {
        return this.serveurNotification;
    }

    /**
     * Récupère la chaine de caractère définissant l'objet.
     * @return Renvoie une chaine de caractère.
     */
    public String toString() {
        return "Personnage[" + this.nomEtreVivant + "]";
    }

    /**
     * Redéfinition de la méthode equals pour comparer deux personnages
     * @param personnage Personnage que l'on compare avec celui courant
     * @return Boolean : True si les deux sont identiques, faux sinon
     */
    boolean equals(Personnage personnage) {
        return this.nomEtreVivant.equals(personnage.getNomPersonnage());
    }
}
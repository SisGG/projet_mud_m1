/* ****************************************************************************
 *
 * Name File : src/Personnage.java
 * Authors   : OLIVIER Thomas
 *             BOURAKADI Reda
 *             LAPEYRADE Sylvain
 *
 * Location  : UPSSITECH - University Paul Sabatier
 * Date      : Janvier 2019
 *
 *                        This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *
 * ***************************************************************************/

import java.io.Serializable;

public class Personnage implements Serializable {

    private String nomPersonnage;
    private Piece pieceActuelle;
    private ServeurNotification serveurNotification;

    /**
     * Constructeur de la classe Personnage.
     * @param nomPersonnage Nom du personnage.
     */
    public Personnage(String nomPersonnage) {
        this.nomPersonnage = nomPersonnage;
        this.pieceActuelle = null;
    }

    public void setPieceActuelle(Piece pieceActuelle) {
        this.pieceActuelle = pieceActuelle;
    }

    /**
     * Récupère la piece actuelle du personnage.
     * @return Renvoie la piece du personnage.
     */
    public Piece getPieceActuelle() {
        return this.pieceActuelle;
    }

    /**
     * Récupère le nom du personnage.
     * @return Renvoie la chaine de caractère du nom de personnage.
     */
    public String getNomPersonnage() {
        return this.nomPersonnage;
    }

    public void setServeurNotification(ServeurNotification serveurNotification) {
        this.serveurNotification = serveurNotification;
    }

    public ServeurNotification getServeurNotification() {
        return this.serveurNotification;
    }

    /**
     * Récupère la chaine de caractère définissant l'objet.
     * @return Renvoie une chaine de caractère.
     */
    public String toString() {
        return "Personnage[" + this.nomPersonnage + "]";
    }

    public boolean equals(Personnage personnage) {
        return this.nomPersonnage.equals(personnage.getNomPersonnage());
    }
}
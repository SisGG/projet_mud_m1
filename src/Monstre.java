/******************************************************************************
 * file     : src/Monstre.java
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
public class Monstre extends EtreVivant {

    private static final int pointDeVieMax= 5;
    private static int numeroMonstre = 0;

    /**
     * Constructeur  de la classe Monstre
     * @param piece pice où se trouve le monstre
     */
    public Monstre(Piece piece) {
        super("Monstre" + numeroMonstre, pointDeVieMax, piece);
        numeroMonstre++;
    }
}

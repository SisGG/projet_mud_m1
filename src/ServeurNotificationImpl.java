import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/******************************************************************************
 * file     : src/ServeurNotificationImpl.java
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
public class ServeurNotificationImpl extends UnicastRemoteObject implements ServeurNotification {

    /**
     * Constructeur de la classe ServeurNotificationImpl.
     * @throws RemoteException Exception déclenchée si ServeurNotificationImpl ne crée pas l'objet.
     */
    ServeurNotificationImpl() throws RemoteException {
        super();
    }

    /**
     * Envoie une chaine de caractère au Client.
     * @param notication Chaine de caractère à envoyer au client.
     */
    public void notifier(String notication){
        System.out.println("\r" + notication);
    }

    /**
     * Demande au Client de saisir une chaine de caractère avec un message spécifique.
     * @param message Chaine de caractère à afficher avant la saisi.
     * @return Renvoie la chaine de caractère saisi par le Client.
     */
    public String demanderAction(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        return scanner.nextLine();
    }

}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/******************************************************************************
 * file     : src/ServeurNotificationImpl.java
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
    public void notifier(String notication) {
        System.out.println(notication);
    }

    /**
     * Demande au Client de saisir une chaine de caractère.
     * @return Renvoie la chaine de caractère saisi par le Client.
     */
    public String demanderAction() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            if (!bufferedReader.ready()) {
                return "-1";
            }
            return bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "-2";
        }
    }

}

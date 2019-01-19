import java.rmi.Naming;
import java.util.Scanner;

import static java.lang.System.exit;

public class Client {

    private Personnage personnage;
    private ServeurDonjon serveurDonjon;
    private ServeurDiscussion serveurDiscussion;
    private Scanner scanner;

    private Client(){
        this.scanner = new Scanner(System.in);
    }

    private boolean seConnecter(String nomPersonnage) {
        try {
            this.serveurDonjon = (ServeurDonjon) Naming.lookup("//localhost/ServeurDonjon");
            this.serveurDiscussion = (ServeurDiscussion) Naming.lookup("//localhost/ServeurDiscussion");

            this.personnage = this.serveurDonjon.seConnecter(nomPersonnage);
            if ( this.personnage == null ) {
                this.serveurDonjon = null;
                this.serveurDiscussion = null;
                return false;
            }
            this.serveurDiscussion.seConnecter(this.personnage);

            ServeurNotification serveurNotification = new ServeurNotificationImpl();
            this.serveurDonjon.enregistrerNotification(this.personnage, serveurNotification);
            this.serveurDiscussion.enregistrerNotification(this.personnage, serveurNotification);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void seDeconnecter() {
        try {
            this.serveurDonjon.seDeconnecter(this.personnage);
            this.serveurDonjon = null;
            this.serveurDiscussion.seDeconnecter(this.personnage);
            this.serveurDiscussion = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seDeplacer(String direction) {
        try {
            this.personnage = this.serveurDonjon.seDeplacer(this.personnage, direction);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void discuter(String message) {
        try {
            this.serveurDiscussion.discuter(this.personnage, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nomPersonnage = null;
        String commande = null;
        Client client = new Client();

        while ( nomPersonnage == null ) {
            System.out.print("Entrer votre nom de personnage : ");
            nomPersonnage = scanner.nextLine();
            if (nomPersonnage.equals("")) {
                System.out.println("Ce nom n'est pas valide.");
                nomPersonnage = null;
            }
        }

        if ( client.seConnecter(nomPersonnage) ) {
            System.out.println("Le personnage " + client.personnage.getNomPersonnage() + " vient de se connecter.");
        } else {
            System.out.println("Erreur connexion serveur");
        }

        System.out.println("\nEntrer \'N\', \'E\', \'S\' ou \'O\' pour "
                + "vous déplacer ou \'\"\' pour communiquer avec d'autres joueurs ou "
                + "\'quitter\' pour vous déconnecter.\n");
        while ( commande == null ) {
            System.out.print(">> ");
            commande = scanner.nextLine();
            if ( commande.substring(0, 1).equals("\"") ) {
                client.discuter(commande);
                commande = null;
            } else if ( commande.equals("N") || commande.equals("E") || commande.equals("S") || commande.equals("O") ) {
                client.seDeplacer(commande);
                commande = null;
            } else if ( commande.equals("quitter") || commande.equals("q") || commande.equals("Q") ) {
                System.out.println("Déconnexion.");
                client.seDeconnecter();
                exit(0);
            } else {
                System.out.println("Cette commande n'est pas reconnu.");
                commande = null;
            }
        }

    }
}

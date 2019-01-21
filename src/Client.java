import java.rmi.Naming;
import java.util.Scanner;

import static java.lang.System.exit;

public class Client {

    private Personnage personnage;
    private ServeurDonjon serveurDonjon;
    private ServeurDiscussion serveurDiscussion;

    private Client(){
        try {
            this.serveurDonjon = (ServeurDonjon) Naming.lookup("//localhost/ServeurDonjon");
            this.serveurDiscussion = (ServeurDiscussion) Naming.lookup("//localhost/ServeurDiscussion");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void seConnecter(String nomPersonnage) {
        try {
            this.personnage = this.serveurDonjon.seConnecter(nomPersonnage);
            this.serveurDiscussion.seConnecter(this.personnage);

            ServeurNotification serveurNotification = new ServeurNotificationImpl();
            this.serveurDonjon.enregistrerNotification(this.personnage, serveurNotification);
            this.serveurDiscussion.enregistrerNotification(this.personnage, serveurNotification);

            System.out.println("Le personnage " + this.personnage.getNomPersonnage() + " vient de se connecter.");
            this.seDeplacer("");
        } catch(Exception e) {
            e.printStackTrace();
        }
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

    private void seDeplacer(String direction) {
        try {
            this.personnage = this.serveurDonjon.seDeplacer(this.personnage, direction);
            this.serveurDiscussion.miseAJourPersonnage(this.personnage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void discuter(String message) {
        try {
            this.serveurDiscussion.discuter(this.personnage, message.substring(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean existeNomPersonnage(String nomPersonnage) {
        try {
            ServeurDonjon serveurDonjon = (ServeurDonjon) Naming.lookup("//localhost/ServeurDonjon");
            return serveurDonjon.existeNomPersonnage(nomPersonnage);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }

    private static Client inscrirePersonnage() {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client();
        String nomPersonnage = null;
        while ( nomPersonnage == null ) {
            System.out.print("Entrer votre nom de personnage : ");
            nomPersonnage = scanner.nextLine();
            if ( nomPersonnage.equals("") ) {
                System.out.println("Ce nom n'est pas valide.");
                nomPersonnage = null;
            } else if ( Client.existeNomPersonnage(nomPersonnage) ) {
                System.out.println("Ce nom existe déjà.");
                nomPersonnage = null;
            }
        }
        client.seConnecter(nomPersonnage);
        return client;
    }

    private static void interpreterCommande(Client client) {
        Scanner scanner = new Scanner(System.in);
        Client.afficherCommande();
        while ( true ) {
            String commande = scanner.nextLine();
            if ( commande.substring(0, 1).equals("\"") ) {
                client.discuter(commande);
            } else if ( commande.equals("N") || commande.equals("E") || commande.equals("S") || commande.equals("O") ) {
                client.seDeplacer(commande);
            } else if ( commande.toLowerCase().equals("quitter") ) {
                System.out.println("Déconnexion.");
                client.seDeconnecter();
                exit(0);
            } else if ( commande.toLowerCase().equals("help") ) {
                Client.afficherCommande();
            } else {
                System.out.println("Cette commande n'est pas reconnue.");
            }
        }
    }

    private static void afficherCommande() {
        System.out.println("\nEntrer \'N\', \'E\', \'S\' ou \'O\' pour "
                + "vous déplacer ou \'\"\' pour communiquer avec d'autres joueurs ou "
                + "\'quitter\' pour vous déconnecter ou \'help\' pour voir les instructions.\n");
    }

    public static void main(String[] args) {

        Client client = Client.inscrirePersonnage();

        Client.interpreterCommande(client);

    }
}

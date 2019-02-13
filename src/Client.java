import java.rmi.Naming;
import java.util.Scanner;

import static java.lang.System.exit;

/******************************************************************************
 * file     : src/Client.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 2.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 18 Février 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class Client {

    private Personnage personnage;
    private ServeurDonjon serveurDonjon;
    private ServeurDiscussion serveurDiscussion;
    private ServeurCombat serveurCombat;

    /**
     * Constructeur de la classe Client.
     */
    private Client() {
        try {
            this.serveurDonjon = (ServeurDonjon) Naming.lookup("//localhost/ServeurDonjon");
            this.serveurDiscussion = (ServeurDiscussion) Naming.lookup("//localhost/ServeurDiscussion");
            this.serveurCombat = (ServeurCombat) Naming.lookup("//localhost/ServeurCombat");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Permet de se connecter sur les différents serveur de jeu en créant un nouveau joueur.
     * @param nomPersonnage Nom du personnage créer.
     */
    private void seConnecter(String nomPersonnage) {
        try {
            this.personnage = this.serveurDonjon.seConnecter(nomPersonnage);

            ServeurNotification serveurNotification = new ServeurNotificationImpl();
            this.serveurDonjon.enregistrerNotification(this.personnage, serveurNotification);

            System.out.println("Le personnage " + this.personnage.getNom() + " vient de se connecter.");
            this.seDeplacer("");
        } catch ( Exception e ) {
            e.printStackTrace();
            this.seDeconnecter(-1);
        }
    }

    /**
     * Permet de se déconnecter sur les différents serveur de jeu.
     * @param codeDeSortie correspond au code du processus renvoyé
     */
    private void seDeconnecter(int codeDeSortie) {
        try {
            this.serveurDonjon.seDeconnecter(this.personnage);
            this.serveurDonjon = null;
            this.serveurDiscussion = null;
            exit(codeDeSortie);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de déplacer son personnage dans une direction.
     * @param direction Chaine de caractère désignant la direction de déplacement.
     */
    private void seDeplacer(String direction) {
        try {
            Piece pieceActuelle = this.personnage.getPieceActuelle();
            this.personnage = this.serveurDonjon.seDeplacer(this.personnage, direction);
            if ( !direction.equals("") && !pieceActuelle.equals(this.personnage.getPieceActuelle()) ) {
                boolean personnageMort = this.serveurCombat.lancerCombatMonstre(this.personnage);
                if ( personnageMort ) {
                    System.exit(0);
                }
                this.afficherCommande();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Envoie un message de discution à tout les joueurs présent dans la même pièce.
     * @param message Chaine de caractère du message à envoyer.
     */
    private void discuter(String message) {
        try {
            this.serveurDiscussion.discuter(this.personnage, message.substring(1));
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Vérifie si le nom d'un personnage est présent en jeu.
     * @param nomPersonnage Nom du personnage à vérifier.
     * @return Renvoie la valeur True si le joueur existe, False sinon.
     */
    private boolean existeNomPersonnage(String nomPersonnage) {
        try {
            ServeurDonjon serveurDonjon = (ServeurDonjon) Naming.lookup("//localhost/ServeurDonjon");
            return serveurDonjon.existeNomPersonnage(nomPersonnage);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Permet de créer un nouveau client en demandant à l'utilisateur un nom de personnage
     * et se connecte ensuite avec se nom d'utilisateur.
     */
    private void inscrirePersonnage() {
        Scanner scanner = new Scanner(System.in);
        String nomPersonnage = null;
        while ( nomPersonnage == null ) {
            System.out.print("Entrer votre nom de personnage : ");
            nomPersonnage = scanner.nextLine();
            if ( nomPersonnage.equals("") ) {
                System.out.println("Ce nom n'est pas valide.");
                nomPersonnage = null;
            } else if ( this.existeNomPersonnage(nomPersonnage) ) {
                System.out.println("Ce nom existe déjà.");
                nomPersonnage = null;
            }
        }
        this.seConnecter(nomPersonnage);
    }

    /**
     * Permet d'interpréter les commandes de l'utilisateur.
     * Demande à l'utilisateur de saisir une commande.
     * L'utilisateur peut discuter en commançant la chaine de caractère par le caractère ".
     * L'utilisateur peut se déplacer en indiquant les lettres N, E, S, O.
     * Effectue ensuite l'action souhaité.
     */
    private void interpreterCommande() {
        this.afficherCommande();
        while ( true ) {
            Scanner scanner = new Scanner(System.in);
            String commande = scanner.nextLine();
            if ( commande.length() > 1 && commande.substring(0, 1).equals("\"") ) {
                this.discuter(commande);
            } else if ( commande.equals("N") || commande.equals("E") || commande.equals("S") || commande.equals("O") ) {
                this.seDeplacer(commande);
            } else if ( commande.toLowerCase().equals("quitter") ) {
                System.out.println("Déconnexion.");
                this.seDeconnecter(0);
            } else if ( commande.equals("L") ) {
                try {
                    this.serveurDonjon.afficherEtreVivantPiece(personnage);
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            } else if ( commande.toLowerCase().equals("help") ) {
                this.afficherCommande();
            } else {
                System.out.println("Cette commande n'est pas reconnue.");
            }
        }
    }

    /**
     * Afficher le message d'aide des commandes.
     */
    private void afficherCommande() {
        System.out.println("\nEntrer \'N\', \'E\', \'S\' ou \'O\' pour "
                + "vous déplacer, \'\"\' pour communiquer, \'quitter\'" +
                " pour vous déconnecter, \'L\' pour afficher les êtres dans la pièce " +
                "ou \'help\' pour voir les commandes.\n");
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.inscrirePersonnage();
        client.interpreterCommande();
    }

}

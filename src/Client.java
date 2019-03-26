import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;

import static java.lang.System.exit;

/******************************************************************************
 * file     : src/Client.java
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
public class Client {

    private Personnage personnage;
    private ServeurSysteme serveurSysteme;
    private ServeurDonjon serveurDonjon;
    private ServeurDiscussion serveurDiscussion;
    private ServeurCombat serveurCombat;
    private ServeurPersistance serveurPersistance;
    private BufferedReader bufferedReader;

    /**
     * Constructeur de la classe Client.
     */
    private Client() {
        try {
            this.serveurSysteme = (ServeurSysteme) Naming.lookup("//localhost/ServeurSysteme");
            this.serveurDiscussion = (ServeurDiscussion) Naming.lookup(this.serveurSysteme.getNomServeurDiscussion());
            this.serveurCombat = (ServeurCombat) Naming.lookup(this.serveurSysteme.getNomServeurCombat());
            this.serveurPersistance = (ServeurPersistance) Naming.lookup(this.serveurSysteme.getNomServeurPersistance());
            this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connecterPersonnage();
        client.jouer();
    }

    /**
     * Permet de se connecter sur les différents serveur de jeu en créant un nouveau joueur.
     *
     * @param nomPersonnage Nom du personnage créer.
     */
    private void seConnecter(String nomPersonnage) {
        try {
            this.personnage = this.serveurPersistance.recuperePersonnage(nomPersonnage);
            if (this.personnage == null) {
                this.personnage = this.serveurDonjon.seConnecter(nomPersonnage);
            } else {
                this.personnage = this.serveurDonjon.seConnecter(this.personnage);
                System.out.println("Vous venez de récupérer votre personnage.");
            }

            ServeurNotification serveurNotification = new ServeurNotificationImpl();
            this.serveurDonjon.enregistrerNotification(this.personnage, serveurNotification);

            System.out.println("Le personnage " + personnage + " vient de se connecter.");
            this.seDeplacer("");

            this.sauvegarderPersonnage();
        } catch (Exception e) {
            e.printStackTrace();
            this.seDeconnecter(false);
        }
    }

    /**
     * Permet de se déconnecter sur les différents serveur de jeu.
     *
     * @param etreSupprimer True si le personnage doit être supprimer du serveur de persistance,
     *                      False non.
     */
    private void seDeconnecter(boolean etreSupprimer) {
        try {
            if (etreSupprimer) {
                this.serveurPersistance.supprimerPersonnage(this.personnage.getNom());
            } else {
                this.sauvegarderPersonnage();
            }
            this.serveurDonjon.deconnecter(this.personnage);
            this.serveurDonjon = null;
            this.serveurDiscussion = null;
            this.serveurCombat = null;
            this.serveurPersistance = null;
            exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de déplacer son personnage dans une direction.
     *
     * @param direction Chaine de caractère désignant la direction de déplacement.
     */
    private void seDeplacer(String direction) {
        try {
            Piece pieceAvantDeplacemenet = this.personnage.getPieceActuelle();
            this.personnage = this.serveurDonjon.seDeplacer(this.personnage, direction);
            if (this.personnage.getPieceActuelle() != pieceAvantDeplacemenet) {
                this.serveurDonjon = (ServeurDonjon) Naming.lookup(this.serveurSysteme.getNomServeurDonjon()
                        + this.personnage.getPieceActuelle().getCoordonneeX());
                this.serveurDonjon.entrerNouvellePiece(this.personnage, direction, this.serveurCombat);
                this.sauvegarderPersonnage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Envoie un message de discution à tout les joueurs présent dans la même pièce.
     *
     * @param message Chaine de caractère du message à envoyer.
     */
    private void discuter(String message) {
        try {
            this.serveurDiscussion.discuter(this.personnage, message.substring(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Vérifie si le nom d'un personnage est présent en jeu.
     *
     * @param nomPersonnage Nom du personnage à vérifier.
     * @return True si le personnage existe, False sinon.
     */
    private boolean existeNomPersonnage(String nomPersonnage) {
        try {
            ServeurDonjon serveurDonjon;
            for (int i = 0; i < this.serveurSysteme.getTailleDonjon(); i++) {
                serveurDonjon = (ServeurDonjon) Naming.lookup(this.serveurSysteme.getNomServeurDonjon() + i);
                if (serveurDonjon != null) {
                    this.serveurDonjon = serveurDonjon;
                    return serveurDonjon.existeNomEtreVivant(nomPersonnage);
                }
            }
            this.serveurDonjon = (ServeurDonjon) Naming.lookup(this.serveurSysteme.getNomServeurDonjon() + 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Permet de créer un nouveau client en demandant à l'utilisateur un nom de personnage
     * et se connecte ensuite avec se nom d'utilisateur.
     */
    private void connecterPersonnage() {
        String nomPersonnage = null;
        while (nomPersonnage == null) {
            System.out.print("Entrer votre nom de personnage : ");
            try {
                nomPersonnage = this.bufferedReader.readLine();
                if (nomPersonnage.equals("")) {
                    System.out.println("Ce nom n'est pas valide.");
                    nomPersonnage = null;
                } else if (this.existeNomPersonnage(nomPersonnage)) {
                    System.out.println("Ce nom existe déjà.");
                    nomPersonnage = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                exit(-10);
            }
        }
        this.seConnecter(nomPersonnage);
    }

    /**
     * Permet à un personnage d'attaquer un être vivant à partir de son nom
     *
     * @param nomCible nom de l'être vivant à attaquer
     */
    private void attaquer(String nomCible) {
        if (!nomCible.equals(this.personnage.getNom())) {
            try {
                if (this.serveurDonjon.existeNomEtreVivant(nomCible)) {
                    EtreVivant etreVivantAttaque = this.serveurDonjon.getMonstre(nomCible);
                    if (etreVivantAttaque == null) {
                        etreVivantAttaque = this.serveurDonjon.getPersonnage(nomCible);
                    }
                    if (etreVivantAttaque != null) {
                        this.serveurCombat.lancerCombat(this.personnage, etreVivantAttaque);
                    }
                    this.sauvegarderPersonnage();
                } else {
                    System.out.println("Il n'y a pas d\'être de ce nom dans la pièce.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Vous ne pouvez pas vous attaquer vous-même.");
        }
    }

    /**
     * Permet de sauvegarder le personnage courant.
     */
    private void sauvegarderPersonnage() {
        try {
            this.serveurPersistance.sauvegarderPersonnage(this.personnage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet d'interpréter les commandes de l'utilisateur.
     * Demande à l'utilisateur de saisir une commande.
     * Effectue ensuite l'action souhaité.
     */
    private void interpreterCommande() {
        try {
            if (bufferedReader.ready()) {
                String texte = this.bufferedReader.readLine();
                if (texte != null) {
                    if (!this.serveurCombat.estEnCombat(this.personnage)) {
                        if (texte.length() > 1 && texte.substring(0, 1).equals("\"")) {
                            this.discuter(texte);
                        } else {
                            String[] commandes = texte.split(" ");
                            switch (commandes[0].toLowerCase()) {
                                case "n":
                                case "e":
                                case "s":
                                case "o":
                                    this.seDeplacer(commandes[0]);
                                    break;
                                case "exit":
                                case "quitter":
                                    System.out.println("Déconnexion.");
                                    this.seDeconnecter(false);
                                    break;
                                case "l":
                                    this.serveurDonjon.afficherEtreVivantPiece(personnage);
                                    break;
                                case "c":
                                    this.serveurDonjon.afficherCombatPiece(personnage);
                                    break;
                                case "attaque":
                                case "attaquer":
                                    if (commandes.length == 2) {
                                        this.attaquer(commandes[1]);
                                    } else {
                                        System.out.println("Il faut un nom de joueur.");
                                    }
                                    break;
                                case "help":
                                    this.afficherCommande();
                                    break;
                                default:
                                    System.out.println("Cette commande n'est pas reconnue.");
                                    break;
                            }
                        }
                    } else if (texte.equals("")) {
                        this.serveurCombat.fuirCombat(this.personnage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche l'ensemble des commandes disponibles pour l'utilisateur.
     */
    private void afficherCommande() {
        System.out.println("\nEntrer \'N\', \'E\', \'S\' ou \'O\' pour "
                + "vous déplacer, \'\"\' pour communiquer, \'exit\'" +
                " pour vous déconnecter, \'L\' pour afficher les êtres et \'C\' pour les combats dans la pièce," +
                " \'attaque\' pour attaquer un être ou \'help\' pour revoir les commandes.");
    }

    /**
     * Tant que le personnage est connecté, attend une nouvelle commande de sa part
     */
    private void jouer() {
        this.afficherCommande();
        try {
            while (this.serveurDonjon.existeNomEtreVivant(this.personnage.nomEtreVivant)) {
                this.personnage = this.serveurDonjon.getPersonnage(this.personnage.nomEtreVivant);
                this.interpreterCommande();
            }
            this.seDeconnecter(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

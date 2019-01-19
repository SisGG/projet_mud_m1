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

    private void seConnecter() {
        try {
            this.serveurDonjon = (ServeurDonjon) Naming.lookup("//localhost/ServeurDonjon");
            String nomPersonnage = inscrireJoueur();
            this.serveurDiscussion = (ServeurDiscussion) Naming.lookup("//localhost/ServeurDiscussion");

            this.personnage = this.serveurDonjon.seConnecter(nomPersonnage);
            this.serveurDiscussion.seConnecter(this.personnage);

            ServeurNotification serveurNotification = new ServeurNotificationImpl();
            this.serveurDonjon.enregistrerNotification(this.personnage, serveurNotification);
            // Ne sert a rien vu que la méthode au dessus fait pareil
            // this.serveurDiscussion.enregistrerNotification(this.personnage, serveurNotification);

            System.out.println("Le personnage " + this.personnage.getNomPersonnage()
                    + " est connecté dans la pièce: " + this.personnage.getPieceActuelle());
            this.serveurDonjon.prevenirEntrerPersonnageMemePiece(
                    this.serveurDonjon.getPersonnage(nomPersonnage));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void seDeconnecter() {
        try {
            this.serveurDonjon.seDeconnecter(this.personnage);
            this.serveurDiscussion.seDeconnecter(this.personnage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String inscrireJoueur(){
        String nomPersonnage = "";
        try{
            while (nomPersonnage.equals("") || this.serveurDonjon.getPersonnage(nomPersonnage) != null){
                System.out.println("Entrer votre nom de personnage:");
                nomPersonnage = this.scanner.nextLine();
                if(nomPersonnage.equals(""))
                    System.out.println("Ce nom n'est pas valide.");
                else if( this.serveurDonjon.getPersonnage(nomPersonnage) != null)
                    System.out.println("Ce nom existe déjà.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return nomPersonnage;
    }

    private void effectuerAction() {
        String commande = " ";

        while (!commande.equals("N") && !commande.equals("O") && !commande.equals("E")
                && !commande.equals("S") && !commande.substring(0,1).equals("\"")
                && !commande.equals("quitter") ) {
            System.out.println("\nEntrer \'N\', \'E\', \'S\' ou \'O\' pour " +
                    "vous déplacer ou \'\"\' pour communiquer avec d'autres joueurs ou " +
                    "\'quitter\' pour vous déconnecter.");
            commande = this.scanner.nextLine();
        }
        if (commande.substring(0,1).equals("\"")){
            try {
                this.serveurDiscussion.discuter(this.personnage, commande);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if(commande.equals("quitter")){
            System.out.println("Vous allez vous déconnecter.");
            seDeconnecter();
            exit(0);
        }
        else{
            try {
                this.personnage = this.serveurDonjon.seDeplacer(this.personnage, commande);
                this.serveurDiscussion.majListe(this.serveurDonjon.getListePersonnage());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.seConnecter();

        try {
            while (client.serveurDonjon.getPersonnage(
                    client.personnage.getNomPersonnage()) != null) {
                client.effectuerAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.seDeconnecter();
    }
}

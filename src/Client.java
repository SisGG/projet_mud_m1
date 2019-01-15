import jdk.internal.org.objectweb.asm.TypeReference;

import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    private ServeurDonjon serveurDonjon;
    private ServeurDiscussion serveurDiscussion;

    public static void main(String[] args) {
        try {

            ServeurDonjon serveurDonjon = (ServeurDonjon) Naming.lookup("//localhost/serveurDonjon");
            ServeurDiscussion serveurDiscussion = (ServeurDiscussion) Naming.lookup("//localhost/serveurDiscussion");

            Personnage personnage = serveurDonjon.seConnecter("Reda");
            System.out.println("Personnage " + personnage.getNomPersonnage() +" connecté au serveur de jeu");
            serveurDiscussion.seConnecter(personnage);
            System.out.println("Personnage " + personnage.getNomPersonnage() +" connecté au serveur de discussion");

            System.out.println("Veuillez entrer la direction dans laquelle vous souhaitez vous diriger");

            Scanner scanner = new Scanner(System.in);
            String texteClient = scanner.next();

            if ( texteClient.startsWith("\"") && texteClient.endsWith("\"")){
                serveurDiscussion.discuter(personnage,texteClient);
            }
            else if( texteClient.equals("N") || texteClient.equals("S") || texteClient.equals("O") || texteClient.equals("E")  ){
                serveurDonjon.seDeplacer(personnage,texteClient);
            }
            else
                System.out.println("Veuillez entrer une direction ou un message");




            serveurDonjon.seDeplacer(personnage,);


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

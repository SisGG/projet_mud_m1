import jdk.internal.org.objectweb.asm.TypeReference;

import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    private ServeurDonjon serveurDonjon;
    private ServeurDiscussion serveurDiscussion;

    public static void main(String[] args) {
        try {

            ServeurDonjon serveurDonjon = (ServeurDonjon) Naming.lookup("//localhost/ServeurDonjon");
            ServeurDiscussion serveurDiscussion = (ServeurDiscussion) Naming.lookup("//localhost/serveurDiscussion");

            Personnage personnage = serveurDonjon.seConnecter("Reda");
            System.out.println("Personnage " + personnage.getNomPersonnage() +" connecté au serveur de jeu");
            serveurDiscussion.seConnecter(personnage);
            System.out.println("Personnage " + personnage.getNomPersonnage() +" connecté au serveur de discussion");

            System.out.println("Veuillez entrer la direction dans laquelle vous souhaitez vous diriger");

            Scanner scanner = new Scanner(System.in);
            String texteClient;

            boolean outOfLoop;

            do {
                texteClient = scanner.next();
                outOfLoop = true;
                if (texteClient.startsWith("\"") && texteClient.endsWith("\"")) {
                    //serveurDiscussion.discuter(personnage, texteClient);
                    System.out.println("Good entry");
                    outOfLoop = false;
                } else if (texteClient.equals("N") || texteClient.equals("S") || texteClient.equals("O") || texteClient.equals("E")) {
                    //serveurDonjon.seDeplacer(personnage, texteClient);
                    System.out.println("Also good entry");
                    outOfLoop = false;
                } else
                    System.out.println("Veuillez entrer une direction ou un message");
            }
            while( outOfLoop == true );



        }catch (Exception e){
            System.out.println();
            e.printStackTrace();
        }
    }
}

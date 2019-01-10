import java.rmi.Remote;

public interface ServeurDonjon extends Remote {
    Personnage seConnecter(String nomPersonnage);

    Personnage seDeplacer(Personnage personnage, String direction);


}

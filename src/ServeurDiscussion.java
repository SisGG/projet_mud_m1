import java.rmi.Remote;

public interface ServeurDiscussion extends Remote {

    void discuter(Personnage personnageEmetteur, String message);

    void seConnecter(Personnage personnage);

    void seDeconnecter(Personnage personnage);


}
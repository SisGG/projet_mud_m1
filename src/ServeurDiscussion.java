import java.rmi.Remote;

public interface ServeurDiscussion extends Remote {

    void discuter(Personnage personnageEmetteur, String message);


}
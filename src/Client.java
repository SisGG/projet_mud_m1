import java.rmi.Naming;

public class Client {

    private Personnage personnage;
    private ServeurDonjon serveurDonjon;
    private ServeurNotification serveurNotification;

    public void setServeurDonjon(ServeurDonjon serveurDonjon) {
        this.serveurDonjon = serveurDonjon;
    }

    public void seConnecter(String nomPersonnage) {
        try {
            this.serveurDonjon = (ServeurDonjon) Naming.lookup("//localhost/ServeurDonjon");
            this.personnage = this.serveurDonjon.seConnecter(nomPersonnage);
            this.serveurNotification = new ServeurNotificationImpl();
            this.serveurDonjon.enregistrerNotification(this.personnage, this.serveurNotification);
        } catch(Exception e) {
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

    public void seDeconnecter() {
        try {
            this.serveurDonjon.seDeconnecter(this.personnage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.seConnecter("Thomas");
        //client.seDeplacer("N");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.seDeconnecter();
    }
}

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ServeurDonjonImpl extends UnicastRemoteObject implements ServeurDonjon {

    private  static final long serialVersionUID = 1L;
    private int tailleDonjon;
    private HashMap<String,Personnage> listePersonnage;
    private Piece[][] donjon;

    /**
     * Constructeur de la classe ServeurDonjonImpl.
     * @param tailleDonjon Taille du donjon.
     */
    public ServeurDonjonImpl(int tailleDonjon) throws RemoteException {
        super();
        this.tailleDonjon = tailleDonjon;
        this.listePersonnage = new HashMap<>();
        this.donjon = new Piece[tailleDonjon][tailleDonjon];
        this.genererDonjon(tailleDonjon);
    }

    /**
     * Crée un personnage, l'enregistre dans le serveur et renvoie le personnage.
     * @param nomPersonnage Nom du personnage.
     * @return Renvoie le nouveau personnage crée.
     */
    public synchronized Personnage seConnecter(String nomPersonnage) {
        Personnage personnage = new Personnage(nomPersonnage);
        personnage.setPieceActuelle(this.getPieceDepart());
        System.out.println("Connexion de " + personnage);
        this.listePersonnage.put(nomPersonnage, personnage);
        return personnage;
    }

    /**
     * Déplace un personnage dans le donjon. Met à jour la pièce du joueur et renvoie le nouveau joueur.
     * @param personnage Personnage qui se déplace.
     * @param direction Direction vers lequel le personnage se déplace.
     * @return Renvoie le personnage mis à jour.
     */
    public Personnage seDeplacer(Personnage personnage, String direction) {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        Piece pieceActuelle = personnageListe.getPieceActuelle();
        Piece pieceDirection = null;
        switch( direction.toUpperCase() ) {
            case "N":
                pieceDirection = this.getPieceNord(pieceActuelle);
                break;
            case "E":
                pieceDirection = this.getPieceEst(pieceActuelle);
                break;
            case "S":
                pieceDirection = this.getPieceSud(pieceActuelle);
                break;
            case "O":
                pieceDirection = this.getPieceOuest(pieceActuelle);
                break;
        }
        if ( pieceDirection != null ) {
            personnageListe.setPieceActuelle(pieceDirection);
            this.prevenirEntrerPersonnageMemePiece(personnageListe);
            try {
                personnageListe.getServeurNotification().notifier("Vous vous déplacez dans la piece " + pieceDirection);
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } else {
            try {
                personnageListe.getServeurNotification().notifier("Impossible d'aller dans cette direction.");
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        return personnageListe;
    }

    /**
     * Déconnecte un personnage du donjon. Il le supprime de la liste des personnage.
     * @param personnage Personnage à déconnecter.
     */
    public synchronized void seDeconnecter(Personnage personnage){
        try {
            this.enleverNotification(personnage);
            this.listePersonnage.remove(personnage.getNomPersonnage());
            System.out.println("Déconnexion de " + personnage);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère la pièce de départ du donjon.
     * @return Renvoie une pièce.
     */
    private Piece getPieceDepart() {
        return this.donjon[0][0];
    }

    /**
     * Génère les pièces du donjon en fonction de la taille du donjon.
     * @param taille Taille du donjon.
     */
    private void genererDonjon(int taille) {
        for ( int i = 0; i < taille; i++ ) {
            for ( int j = 0; j < taille; j++ ) {
                Piece piece = new Piece(i,j);
                this.donjon[i][j] = piece;
            }
        }
    }

    /**
     * Récupère la pièce au Nord de celle passé en paramètre.
     * @param piece Piece sur lequel on récupère la pièce au Nord.
     * @return Renvoie une pièce si elle existe, null sinon.
     */
    private Piece getPieceNord(Piece piece) {
        int coordonneeX = piece.getCoordonneeX();
        int coordonneeY = piece.getCoordonneeY();
        if ( coordonneeX + 1 < this.tailleDonjon ) {
            return this.donjon[coordonneeX+1][coordonneeY];
        }
        return null;
    }

    /**
     * Récupère la pièce à l'Est de celle passé en paramètre.
     * @param piece Piece sur lequel on récupère la pièce à l'Est.
     * @return Renvoie une pièce si elle existe, null sinon.
     */
    private Piece getPieceEst(Piece piece) {
        int coordonneeX = piece.getCoordonneeX();
        int coordonneeY = piece.getCoordonneeY();
        if ( coordonneeY + 1 < this.tailleDonjon ) {
            return this.donjon[coordonneeX][coordonneeY+1];
        }
        return null;
    }

    /**
     * Récupère la pièce au Sud de celle passé en paramètre.
     * @param piece Piece sur lequel on récupère la pièce au Sud.
     * @return Renvoie une pièce si elle existe, null sinon.
     */
    private Piece getPieceSud(Piece piece) {
        int coordonneeX = piece.getCoordonneeX();
        int coordonneeY = piece.getCoordonneeY();
        if ( coordonneeX - 1 >= 0 ) {
            return this.donjon[coordonneeX-1][coordonneeY];
        }
        return null;
    }

    /**
     * Récupère la pièce à l'Ouest de celle passé en paramètre.
     * @param piece Piece sur lequel on récupère la pièce à l'Ouest.
     * @return Renvoie une pièce si elle existe, null sinon.
     */
    private Piece getPieceOuest(Piece piece) {
        int coordonneeX = piece.getCoordonneeX();
        int coordonneeY = piece.getCoordonneeY();
        if ( coordonneeY - 1 >= 0 ) {
            return this.donjon[coordonneeX][coordonneeY-1];
        }
        return null;
    }

    /**
     * Quand un personnage entre dans une pìèce, envoie une notification
     * a tous les personnages deja present et le nom de tous les personnage
     * deja present au personnage entrant, sinon envoie qu'il n'y a personne
     * @param personnage Personnage entrant dans la piece
     */
     public void prevenirEntrerPersonnageMemePiece(Personnage personnage) {
        String notification = "Il y a ";
        for(Personnage personnage1 : listePersonnage.values()){
            if (personnage1.getPieceActuelle().toString().equals(personnage.getPieceActuelle().toString())
                && !personnage1.toString().equals(personnage.toString())){
                try {
                    personnage1.getServeurNotification().notifier(personnage.getNomPersonnage()
                            + " est entré dans la pièce: " + personnage.getPieceActuelle());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                if(!personnage1.equals(personnage))
                    notification += personnage1.getNomPersonnage()+ " ";
            }
        }
        if (notification.equals("Il y a "))
            notification = "Il n'y a pas d'autre joueur dans la pièce.";
        else
            notification += "dans la pièce.";
        try {
            personnage.getServeurNotification().notifier(notification);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void enregistrerNotification(Personnage personnage, ServeurNotification serveurNotification) throws RemoteException {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(serveurNotification);
    }

    public synchronized void enleverNotification(Personnage personnage) throws RemoteException {
        Personnage personnageListe = this.listePersonnage.get(personnage.getNomPersonnage());
        personnageListe.setServeurNotification(null);
    }

}

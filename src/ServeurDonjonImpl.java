
public class ServeurDonjonImpl implements ServeurDonjon {
    private Piece pieceDepart;
    private int tailleDonjon;
    private java.util.ArrayList listeJoueurs;
    private Piece[][] donjon;

    public ServeurDonjonImpl(int tailleDonjon) {
    }

    public synchronized Personnage seConnecter(String nomPersonnage) {
        return null;
    }

    public Personnage seDeplacer(Personnage personnage, String direction) {
        return null;
    }

    public synchronized void seDeconnecter(){}

    public Piece getPieceDepart() {
        return null;
    }

    public void genererDonjon(int taille) { }

    public Piece getPieceNord(Piece piece) {
        return null;
    }

    public Piece getPieceEst(Piece piece) {
        return null;
    }

    public Piece getPieceSud(Piece Piece) {
        return null;
    }

    public Piece getPieceOuest(Piece piece) {
        return null;
    }

    private void prevenirEntrerPersonnageMemePiece(Piece piece, Personnage personnage) { }
}

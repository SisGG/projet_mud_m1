import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServeurCombatImpl extends UnicastRemoteObject implements ServeurCombat {

    public ServeurCombatImpl() throws RemoteException {
        super();
    }

    public void LancerCombat(Personnage personnage) {
    }

    public void FaireGagnerVie(EtreVivant etreVivant) {
    }

    public void FaireDisparaitreEtreVivant(EtreVivant etreVivant) {
    }
}

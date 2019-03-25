import java.io.*;

/******************************************************************************
 * file     : src/BDFileObjet.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 4.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 25 Mars 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class BDFileObjet implements BaseDeDonnees {

    private static final String extensionTmp = ".tmp";
    private String nomFichier;

    /**
     * Constructeur de la classe BDFileObjet.
     * @param nomFichier Nom du fichier à sauvegarder.
     */
    BDFileObjet(String nomFichier) {
        this.nomFichier = nomFichier;
        try {
            new FileInputStream(this.nomFichier);
        } catch ( FileNotFoundException f ) {
            try {
                new FileOutputStream(this.nomFichier);
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see BaseDeDonnees
     * @param personnage Personnage à sauvegarder.
     */
    public synchronized void put(Personnage personnage) {
        ObjectOutputStream ecriture = null;
        ObjectInputStream lecture = null;
        boolean personnageEcrit = false;
        boolean suivant;
        Personnage personnageCourant;

        try {
            try {
                try {
                    lecture = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier)));
                } catch ( EOFException eof ) {
                    ecriture = new ObjectOutputStream(new BufferedOutputStream(
                            new FileOutputStream(this.nomFichier)));
                    ecriture.writeObject(personnage);
                    ecriture.close();
                    return;
                }

                try {
                    lecture = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier + extensionTmp)));
                } catch ( EOFException e ) {
                    return;
                }
                ecriture = new ObjectOutputStream(new BufferedOutputStream(
                        new FileOutputStream(this.nomFichier)));
                suivant = true;
                while ( suivant ) {
                    try {
                        personnageCourant = (Personnage) lecture.readObject();
                        if ( personnageCourant.getNom().equals(personnage.getNom()) ) {
                            ecriture.writeObject(personnage);
                            personnageEcrit = true;
                        } else {
                            ecriture.writeObject(personnageCourant);
                        }
                    } catch ( EOFException e ) {
                        suivant = false;
                    }
                }
                if ( !personnageEcrit ) {
                    ecriture.writeObject(personnage);
                }

            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(lecture, ecriture);
        }
    }

    /**
     * @see BaseDeDonnees
     * @param nomPersonnage Nom du personnage à supprimer.
     */
    public synchronized void remove(String nomPersonnage) {
        ObjectOutputStream ecriture = null;
        ObjectInputStream lecture = null;
        boolean suivant;
        Personnage personnageCourant;

        try {
            try {
                this.copyFileInTmp();
                lecture = new ObjectInputStream(new BufferedInputStream(
                        new FileInputStream(this.nomFichier + extensionTmp)));

                ecriture = new ObjectOutputStream(new BufferedOutputStream(
                        new FileOutputStream(this.nomFichier)));
                suivant = true;
                while ( suivant ) {
                    try {
                        personnageCourant = (Personnage) lecture.readObject();
                        if (!personnageCourant.getNom().equals(nomPersonnage) ) {
                            ecriture.writeObject(personnageCourant);
                        }
                    } catch ( EOFException eof ) {
                        suivant = false;
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(lecture, ecriture);
        }
    }

    /**
     * @see BaseDeDonnees
     * @param nomPersonnage Nom du personnage à récupérer.
     * @return Renvoie le personnage ou null s'il n'existe pas.
     */
    public Personnage get(String nomPersonnage) {
        ObjectInputStream ecriture = null;
        boolean suivant;
        Personnage personnage = null;
        Personnage personnageCourant;

        try {
            try {
                suivant = true;
                try {
                    ecriture = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier)));
                } catch ( EOFException eof ) {
                    suivant = false;
                }
                while ( suivant ) {
                    try {
                        personnageCourant = (Personnage) ecriture.readObject();
                        if (personnageCourant.getNom().equals(nomPersonnage)) {
                            personnage = personnageCourant;
                            suivant = false;
                        }
                    } catch ( EOFException e ) {
                        suivant = false;
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            if ( ecriture != null ) {
                try {
                    ecriture.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
        return personnage;
    }

    /**
     * Permet de copier le fichier de données dans un fichier temporaire.
     */
    private void copyFileInTmp() {
        ObjectInputStream lecture = null;
        ObjectOutputStream ecriture = null;
        boolean suivant;
        Personnage personnageCourant;
        try {
            try {
                try {
                    lecture = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier)));
                } catch ( EOFException eof ) {
                    return;
                }
                ecriture = new ObjectOutputStream(new BufferedOutputStream(
                        new FileOutputStream(this.nomFichier + extensionTmp)));
                suivant = true;
                while ( suivant ) {
                    try {
                        personnageCourant = (Personnage) lecture.readObject();
                        ecriture.writeObject(personnageCourant);
                    } catch (EOFException e) {
                        suivant = false;
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(lecture, ecriture);
        }
    }

    /**
     * Permet de fermer des descripteur de flux.
     * @param lecture Descripteur de flux en lecture.
     * @param ecriture Descripteur de flux en écriture.
     */
    private void closeStream(ObjectInputStream lecture, ObjectOutputStream ecriture) {
        try {
            if ( lecture != null ) {
                lecture.close();
            }
            if ( ecriture != null ) {
                ecriture.close();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}

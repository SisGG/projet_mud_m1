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
            } catch ( Exception ex ) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @see BaseDeDonnees
     * @param personnage Personnage à sauvegarder.
     */
    public synchronized void put(Personnage personnage) {
        boolean personnageWrited = false;
        boolean hasNext;
        Personnage personnageCourant;
        ObjectOutputStream writer = null;
        ObjectInputStream reader = null;
        try {
            try {
                try {
                    reader = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier)));
                } catch ( EOFException eof ) {
                    // Si le fichier de données n'existe pas.
                    writer = new ObjectOutputStream(new BufferedOutputStream(
                            new FileOutputStream(this.nomFichier)));
                    writer.writeObject(personnage);
                    writer.close();
                    return;
                }

                try {
                    reader = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier + extensionTmp)));
                } catch ( EOFException e ) {
                    return;
                }
                writer = new ObjectOutputStream(new BufferedOutputStream(
                        new FileOutputStream(this.nomFichier)));
                hasNext = true;
                while ( hasNext ) {
                    try {
                        personnageCourant = (Personnage) reader.readObject();
                        if ( personnageCourant.getNom().equals(personnage.getNom()) ) {
                            writer.writeObject(personnage);
                            personnageWrited = true;
                        } else {
                            writer.writeObject(personnageCourant);
                        }
                    } catch ( EOFException e ) {
                        hasNext = false;
                    }
                }
                if ( !personnageWrited ) {
                    writer.writeObject(personnage);
                }

            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(reader, writer);
        }
    }

    /**
     * @see BaseDeDonnees
     * @param nomPersonnage Nom du personnage à supprimer.
     */
    public synchronized void remove(String nomPersonnage) {
        ObjectOutputStream writer = null;
        ObjectInputStream reader = null;
        boolean hasNext;
        Personnage personnageCourant;
        try {
            try {
                try {
                    reader = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier)));
                    this.copyFileInTmp();
                    reader = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier + extensionTmp)));
                } catch ( EOFException eof ) {
                    return;
                }

                writer = new ObjectOutputStream(new BufferedOutputStream(
                        new FileOutputStream(this.nomFichier)));
                hasNext = true;
                while ( hasNext ) {
                    try {
                        personnageCourant = (Personnage) reader.readObject();
                        if (!personnageCourant.getNom().equals(nomPersonnage) ) {
                            writer.writeObject(personnageCourant);
                        }
                    } catch ( EOFException eof ) {
                        hasNext = false;
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(reader, writer);
        }
    }

    /**
     * @see BaseDeDonnees
     * @param nomPersonnage Nom du personnage à récupérer.
     * @return Renvoie le personnage ou null s'il n'existe pas.
     */
    public Personnage get(String nomPersonnage) {
        ObjectInputStream reader = null;
        boolean hasNext;
        Personnage personnage = null;
        Personnage personnageCourant;

        try {
            try {
                hasNext = true;
                try {
                    reader = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier)));
                } catch ( EOFException eof ) {
                    hasNext = false;
                }
                while ( hasNext ) {
                    try {
                        personnageCourant = (Personnage) reader.readObject();
                        if (personnageCourant.getNom().equals(nomPersonnage)) {
                            personnage = personnageCourant;
                            hasNext = false;
                        }
                    } catch ( EOFException e ) {
                        hasNext = false;
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            if ( reader != null ) {
                try {
                    reader.close();
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
        ObjectInputStream reader = null;
        ObjectOutputStream writer = null;
        boolean hasNext;
        Personnage personnageCourant;
        try {
            try {
                try {
                    reader = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier)));
                } catch ( EOFException eof ) {
                    return;
                }
                writer = new ObjectOutputStream(new BufferedOutputStream(
                        new FileOutputStream(this.nomFichier + extensionTmp)));
                hasNext = true;
                while ( hasNext ) {
                    try {
                        personnageCourant = (Personnage) reader.readObject();
                        writer.writeObject(personnageCourant);
                    } catch (EOFException e) {
                        hasNext = false;
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(reader, writer);
        }
    }

    /**
     * Permet de fermer des descripteur de flux.
     * @param ois Descripteur de flux en lecture.
     * @param oos Descripteur de flux en écriture.
     */
    private void closeStream(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            if ( ois != null ) {
                ois.close();
            }
            if ( oos != null ) {
                oos.close();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}

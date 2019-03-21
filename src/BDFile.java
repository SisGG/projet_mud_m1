import jdk.nashorn.internal.runtime.ECMAException;

import java.io.*;

/******************************************************************************
 * file     : src/BDFile.java
 * @author  : OLIVIER Thomas
 *            BOURAKADI Reda
 *            LAPEYRADE Sylvain
 * @version : 3.0
 * location : UPSSITECH - University Paul Sabatier
 * date     : 18 Mars 2019
 * licence  :              This work is licensed under a
 *              Creative Commons Attribution 4.0 International License.
 *                                    (CC BY)
 *****************************************************************************/
public class BDFile implements BaseDeDonnees {

    private static final String extensionTmp = ".tmp";
    private String nomFichier;

    BDFile(String nomFichier) {
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

                // Si le fichier de données existe et est disponible
                // Il faut copier son contenu dans un fichier temporaire.
                this.copyFileInTmp();

                // Maintenant on va copier le contenu du fichier temporaire dans le fichier de données
                try {
                    reader = new ObjectInputStream(new BufferedInputStream(
                            new FileInputStream(this.nomFichier + extensionTmp)));
                } catch ( EOFException e ) {
                    // Erreur de EOF ou autre
                    // Si le fichier temporaire est supprimer entre temps
                    // alors qu'on viens juste de le créer avant
                    // Il y a une erreur
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

    public static void main(String[] args) {
        BDFile bd = new BDFile("fileTest.data");
        //bd.put(new Personnage("Thomas"));
        //bd.put(new Personnage("Nicolas"));
        //bd.put(new Personnage("Thomas"));
        //bd.put(new Personnage("Julien"));
        //bd.put(new Personnage("Camille"));

        //System.out.println("Personnage trouver : " + bd.get("Thomas"));
        bd.remove("Thomas");

    }

}

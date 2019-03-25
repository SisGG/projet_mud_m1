import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/******************************************************************************
 * file     : src/BDFileObjet.java
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
public class BDFileString implements BaseDeDonnees {

    private Path source;
    private Path sourceTmp;
    private boolean working;

    /**
     * Constructeur de la classe BDFileObjet.
     * @param nomFichier Nom du fichier à sauvegarder.
     */
    BDFileString(String nomFichier) {
        this.source = Paths.get(nomFichier);
        this.sourceTmp = Paths.get(nomFichier + ".tmp");
        if ( !Files.exists(this.source) ) {
            try {
                Files.createFile(this.source);
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        this.working = false;
    }

    /**
     * Transforme un personnage en chaine de caractère définit décrivant le personnage.
     * @param personnage Personnage à transformer.
     * @return Renvoie une chaine de caractère dans un format définit.
     */
    private String personnage2String(Personnage personnage) {
        String texte;
        Piece piece = personnage.getPieceActuelle();
        texte = personnage.getNom() + " "
                + personnage.getPointDeVieActuel() + " "
                + personnage.getPointDeVieMax() + " "
                + piece.getCoordonneeX() + ":" + piece.getCoordonneeY()
                + "\n";
        return texte;
    }

    /**
     * Transforme une chaine de caractère en un personnage.
     * @param textePersnnage Chaine de caractère dans un format définit.
     * @return Renvoie le personnage crée.
     */
    private Personnage string2Personnage(String textePersnnage) {
        Personnage personnage;
        String[] tableauTextePersonnage = textePersnnage.split(" ");
        if ( tableauTextePersonnage.length != 4 ) {
            return null;
        }
        personnage = new Personnage(tableauTextePersonnage[0]);
        personnage.setPointDeVieActuel(Integer.parseInt(tableauTextePersonnage[1]));
        personnage.setPointDeVieMax(Integer.parseInt(tableauTextePersonnage[2]));
        int x = Integer.parseInt(tableauTextePersonnage[3].split(":")[0]);
        int y = Integer.parseInt(tableauTextePersonnage[3].split(":")[1]);
        Piece piece = new Piece(x, y);
        personnage.setPieceActuelle(piece);
        return personnage;
    }

    /**
     * @see BaseDeDonnees
     * @param personnage Personnage à sauvegarder.
     */
    public synchronized void put(Personnage personnage) {
        BufferedWriter writer = null;
        BufferedReader reader = null;
        boolean hasNext, writed = false;
        String line;
        String textPersonnage = this.personnage2String(personnage);

        this.testWorking();
        this.working = true;

        this.copyFileInTmp();

        try {
            try {
                reader = Files.newBufferedReader(this.sourceTmp, StandardCharsets.UTF_8);
                writer = Files.newBufferedWriter(this.source, StandardCharsets.UTF_8);
                hasNext = true;
                while ( hasNext ) {
                    line = reader.readLine();
                    if ( line == null ) {
                        hasNext = false;
                    } else {
                        if (line.split(" ")[0].equals(textPersonnage.split(" ")[0])) {
                            writer.write(textPersonnage);
                            writed = true;
                        } else {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }
                if ( !writed ) {
                    writer.write(textPersonnage);
                }

            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(reader, writer);
        }

        this.working = false;
    }

    /**
     * @see BaseDeDonnees
     * @param nomPersonnage Nom du personnage à supprimer.
     */
    public synchronized void remove(String nomPersonnage) {
        BufferedWriter writer = null;
        BufferedReader reader = null;
        boolean hasNext;
        String line;

        this.testWorking();
        this.working = true;

        this.copyFileInTmp();

        try {
            try {
                reader = Files.newBufferedReader(this.sourceTmp, StandardCharsets.UTF_8);
                writer = Files.newBufferedWriter(this.source, StandardCharsets.UTF_8);
                hasNext = true;
                while ( hasNext ) {
                    line = reader.readLine();
                    if (line == null) {
                        hasNext = false;
                    } else {
                        if ( !line.split(" ")[0].equals(nomPersonnage) ) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(reader, writer);
        }

        this.working = false;
    }

    /**
     * @see BaseDeDonnees
     * @param nomPersonnage Nom du personnage à récupérer.
     * @return Renvoie le personnage ou null s'il n'existe pas.
     */
    public Personnage get(String nomPersonnage) {
        Personnage personnage = null;
        BufferedReader reader = null;
        boolean hasNext;
        String line;

        try {
            try {

                reader = Files.newBufferedReader(this.source, StandardCharsets.UTF_8);
                hasNext = true;
                while ( hasNext ) {
                    line = reader.readLine();
                    if ( line == null ) {
                        hasNext = false;
                    } else {
                        if ( line.split(" ")[0].equals(nomPersonnage) ) {
                            personnage = this.string2Personnage(line);
                            hasNext = false;
                        }
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(reader, null);
        }
        return personnage;
    }

    /**
     * Permet de copier le fichier de données dans un fichier temporaire.
     */
    private void copyFileInTmp() {
        BufferedWriter writer = null;
        BufferedReader reader = null;
        boolean hasNext;
        String line;
        try {
            try {
                reader = Files.newBufferedReader(this.source, StandardCharsets.UTF_8);
                writer = Files.newBufferedWriter(this.sourceTmp, StandardCharsets.UTF_8);
                hasNext = true;
                while ( hasNext ) {
                    line = reader.readLine();
                    if ( line == null ) {
                        hasNext = false;
                    } else {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } finally {
            this.closeStream(reader, writer);
        }

    }

    private void testWorking() {
        while ( working ) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Permet de fermer des descripteur de flux.
     * @param reader Descripteur de flux en lecture.
     * @param writer Descripteur de flux en écriture.
     */
    private void closeStream(BufferedReader reader, BufferedWriter writer) {
        if ( reader != null ) {
            try {
                reader.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        if ( writer != null ) {
            try {
                writer.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

}

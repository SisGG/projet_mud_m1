import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/******************************************************************************
 * file     : src/BDFileObject.java
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

    BDFileString(String nomFichierBd) {
        this.source = Paths.get(nomFichierBd);
        this.sourceTmp = Paths.get(nomFichierBd+".tmp");
        if ( !Files.exists(this.source) ) {
            try {
                Files.createFile(this.source);
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        this.working = false;
    }

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

    public static void main(String[] args) {
        BDFileString bd = new BDFileString("fileTest.data");
        Piece piece = new Piece(0,0);
        Personnage personnage = new Personnage("Thomas");
        personnage.setPieceActuelle(piece);
        //bd.put(personnage);
        //bd.put(new Personnage("Nicolas"));
        //bd.put(new Personnage("Thomas"));
        //bd.put(new Personnage("Julien"));
        //bd.put(new Personnage("Camille"));

        System.out.println("Personnage trouver : " + bd.get("Adrien").getPointDeVieMax());
        bd.remove("Thomas");
    }

}

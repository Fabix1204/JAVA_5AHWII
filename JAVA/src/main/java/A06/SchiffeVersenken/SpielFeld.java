package A06.SchiffeVersenken;

import java.util.ArrayList;

public class SpielFeld {
    // 2D array representing the game field
    private Feld[][] felder = new Feld[10][10];

    // Counters for water hits, ship hits, and total ships
    private int wasserGetroffen;
    private int schiffeGetroffen;
    private int schiffeInsgesamt;

    // Constructor to initialize the game field
    public SpielFeld() {
    }

    // Getter for the game field
    public Feld[][] getFelder() {
        return felder;
    }

    // Getter for the number of water hits
    public int getWasserGetroffen() {
        return wasserGetroffen;
    }

    // Getter for the number of ship hits
    public int getSchiffeGetroffen() {
        return schiffeGetroffen;
    }

    // Get IDs of all remaining ships
    public int[] getIds() {
        int[] ids = new int[getAnzahlSchiffe()];
        int stelle = 0;
        for (int i = 0; i < schiffeInsgesamt; i++) {
            for (int j = 0; j < felder.length; j++) {
                boolean b = false;
                for (int j2 = 0; j2 < felder[i].length; j2++) {
                    if (felder[j][j2] instanceof SchiffTeil) {
                        SchiffTeil s = (SchiffTeil) felder[j][j2];
                        if (s.getId() == i && !(s.isGetroffen())) {
                            b = true;
                            ids[stelle] = s.getId();
                            stelle++;
                        }
                    }
                    if (b) {
                        break;
                    }
                }
                if (b) {
                    break;
                }
            }
        }
        return ids;
    }

    // Get the number of remaining ships
    public int getAnzahlSchiffe() {
        int anzahl = 0;
        for (int i = 0; i < schiffeInsgesamt; i++) {
            for (int j = 0; j < felder.length; j++) {
                boolean b = false;
                for (int j2 = 0; j2 < felder[i].length; j2++) {
                    if (felder[j][j2] instanceof SchiffTeil) {
                        SchiffTeil s = (SchiffTeil) felder[j][j2];
                        if (s.getId() == i && !(s.isGetroffen())) {
                            b = true;
                            anzahl++;
                        }
                    }
                    if (b) {
                        break;
                    }
                }
                if (b) {
                    break;
                }
            }
        }
        return anzahl;
    }

    // Initialize the game field and place ships
    public void init() {
        felder = new Feld[10][10];
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                felder[i][j] = new Feld();
            }
        }
        // Place ships of varying lengths
        SchiffFactory.create(this, 5, 0);
        SchiffFactory.create(this, 4, 1);
        SchiffFactory.create(this, 4, 2);
        SchiffFactory.create(this, 3, 3);
        SchiffFactory.create(this, 3, 4);
        SchiffFactory.create(this, 3, 5);
        SchiffFactory.create(this, 2, 6);
        SchiffFactory.create(this, 2, 7);
        SchiffFactory.create(this, 2, 8);
        SchiffFactory.create(this, 2, 9);
        schiffeInsgesamt = 10; // Total number of ships
    }

    // Check if the game is over (all ships are hit)
    public boolean fertig() {
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                if (felder[i][j] instanceof SchiffTeil && !felder[i][j].isGetroffen()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Handle a shot at a specific position
    public void schuss(Position p) {
        felder[p.getX()][p.getY()].setGetroffen(true);
        if (felder[p.getX()][p.getY()] instanceof SchiffTeil) {
            schiffeGetroffen++; // Increment ship hit counter
        } else {
            wasserGetroffen++; // Increment water hit counter
        }
    }

    // Convert the game field to a string representation
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                str += felder[i][j].toString() + " ";
            }
            str += "\n";
        }
        return str;
    }

    // Test string representation with ship IDs
    public String testToString() {
        String str = "";
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                if (felder[i][j] instanceof SchiffTeil) {
                    SchiffTeil s = (SchiffTeil) felder[i][j];
                    str += felder[i][j].toString() + s.getId() + " ";
                } else {
                    str += felder[i][j].toString() + "f ";
                }
            }
            str += "\n";
        }
        return str;
    }

    // Set a specific field at a position
    public void setFeld(Feld f, Position p) {
        felder[p.getX()][p.getY()] = f;
    }

    // Clear the game field
    public void clear() {
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder.length; j++) {
                felder[i][j] = new Feld();
            }
        }
    }

    // Get all ship parts on the field
    public ArrayList<SchiffTeil> getAllShipParts() {
        ArrayList<SchiffTeil> teile = new ArrayList<>();
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                if (felder[i][j] instanceof SchiffTeil) {
                    teile.add((SchiffTeil) felder[i][j]);
                }
            }
        }
        return teile;
    }
}
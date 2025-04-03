package A06.SchiffeVersenken;

import java.util.ArrayList;

public class SchiffFactory {

    // Checks if a ship can be placed at the given position
    public static boolean schiffMoeglich(Position p, SpielFeld sp) {
        boolean[] b = new boolean[8]; // Array to check all surrounding fields
        for (int i = 0; i < b.length; i++) {
            b[i] = false; // Initialize all checks to false
        }

        // Check the field to the right
        if (p.getX() + 1 < sp.getFelder().length) {
            if (!(sp.getFelder()[p.getX() + 1][p.getY()] instanceof SchiffTeil)) {
                b[0] = true;
            }
        } else {
            b[0] = true;
        }

        // Check the field to the left
        if (p.getX() - 1 >= 0) {
            if (!(sp.getFelder()[p.getX() - 1][p.getY()] instanceof SchiffTeil)) {
                b[1] = true;
            }
        } else {
            b[1] = true;
        }

        // Check the field below
        if (p.getY() + 1 < sp.getFelder().length) {
            if (!(sp.getFelder()[p.getX()][p.getY() + 1] instanceof SchiffTeil)) {
                b[2] = true;
            }
        } else {
            b[2] = true;
        }

        // Check the field above
        if (p.getY() - 1 >= 0) {
            if (!(sp.getFelder()[p.getX()][p.getY() - 1] instanceof SchiffTeil)) {
                b[3] = true;
            }
        } else {
            b[3] = true;
        }

        // Check the field diagonally bottom-right
        if (p.getX() + 1 < sp.getFelder().length && p.getY() + 1 < sp.getFelder().length) {
            if (!(sp.getFelder()[p.getX() + 1][p.getY() + 1] instanceof SchiffTeil)) {
                b[4] = true;
            }
        } else {
            b[4] = true;
        }

        // Check the field diagonally bottom-left
        if (p.getX() + 1 < sp.getFelder().length && p.getY() - 1 >= 0) {
            if (!(sp.getFelder()[p.getX() + 1][p.getY() - 1] instanceof SchiffTeil)) {
                b[5] = true;
            }
        } else {
            b[5] = true;
        }

        // Check the field diagonally top-left
        if (p.getX() - 1 >= 0 && p.getY() - 1 >= 0) {
            if (!(sp.getFelder()[p.getX() - 1][p.getY() - 1] instanceof SchiffTeil)) {
                b[6] = true;
            }
        } else {
            b[6] = true;
        }

        // Check the field diagonally top-right
        if (p.getX() - 1 >= 0 && p.getY() + 1 < sp.getFelder().length) {
            if (!(sp.getFelder()[p.getX() - 1][p.getY() + 1] instanceof SchiffTeil)) {
                b[7] = true;
            }
        } else {
            b[7] = true;
        }

        // Return true if all surrounding fields are valid for placing a ship
        return b[0] && b[1] && b[2] && b[3] && b[4] && b[5] && b[6] && b[7];
    }

    // Creates a ship on the game field
    public static SpielFeld create(SpielFeld sp, int laenge, int id) {
        int folge = 0;
        ArrayList<Position> positions = new ArrayList<>();
        boolean nochmal = true;

        // Horizontal placement
        while (nochmal) {
            for (int i = 0; i < sp.getFelder().length; i++) {
                for (int j = 0; j < sp.getFelder()[i].length; j++) {
                    if (schiffMoeglich(new Position(i, j, true), sp)) {
                        folge++;
                    } else {
                        folge = 0;
                    }
                    boolean geht = true;
                    for (Position position : positions) {
                        if (position.equals(new Position(i, j - folge + 1, true))) {
                            geht = false;
                            folge = 0;
                        }
                    }
                    if (folge == laenge && geht) {
                        positions.add(new Position(i, j - folge + 1, true));
                        break;
                    }
                    if (i == sp.getFelder().length - 1 && j == sp.getFelder()[i].length - 1) {
                        nochmal = false;
                    }
                }
                if (folge == laenge) {
                    folge = 0;
                    break;
                }
                folge = 0;
            }
        }

        // Vertical placement
        nochmal = true;
        folge = 0;

        while (nochmal) {
            for (int i = 0; i < sp.getFelder().length; i++) {
                for (int j = 0; j < sp.getFelder()[i].length; j++) {
                    if (schiffMoeglich(new Position(j, i, false), sp)) {
                        folge++;
                    } else {
                        folge = 0;
                    }
                    boolean geht = true;
                    for (Position position : positions) {
                        if (position.equals(new Position(j - folge + 1, i, false))) {
                            geht = false;
                            folge = 0;
                        }
                    }
                    if (folge == laenge && geht) {
                        positions.add(new Position(j - folge + 1, i, false));
                        break;
                    }
                    if (i == sp.getFelder().length - 1 && j == sp.getFelder()[i].length - 1) {
                        nochmal = false;
                    }
                }
                if (folge == laenge) {
                    folge = 0;
                    break;
                }
                folge = 0;
            }
        }

        // Randomly select a position and place the ship
        Position p = positions.get((int) (Math.random() * positions.size()));
        for (int i = 0; i < laenge; i++) {
            if (p.getRotation()) {
                sp.getFelder()[p.getX()][p.getY() + i] = new SchiffTeil(id);
            } else {
                sp.getFelder()[p.getX() + i][p.getY()] = new SchiffTeil(id);
            }
        }
        return sp;
    }
}
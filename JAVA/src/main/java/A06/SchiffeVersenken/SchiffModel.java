package A06.SchiffeVersenken;

import java.util.Observable;

public class SchiffModel extends Observable {

    // Reference to the game field
    private SpielFeld sp;

    // Counter for the number of shots taken
    private int shots = 0;

    // Constructor to initialize the model with the game field
    public SchiffModel(SpielFeld sp) {
        this.sp = sp;
    }

    // Method to handle a shot at a specific position
    public void set(Position p) {
        Feld f = sp.getFelder()[p.getX()][p.getY()]; // Get the field at the given position

        // Check if the field has already been hit
        if (f.isGetroffen()) {
            notifyUpdate(ACTIONS.FIELD_OCCUPIED, p); // Notify that the field is already occupied
        } else {
            f.setGetroffen(true); // Mark the field as hit
            shots++; // Increment the shot counter

            // Check if the field contains a ship part
            if (f instanceof SchiffTeil) {
                handleShipHit((SchiffTeil) f, p); // Handle the ship hit logic
            } else {
                notifyUpdate(ACTIONS.FIELD_HIT, p); // Notify that a field was hit
            }
        }

        // Check if the game is over
        if (sp.fertig()) {
            notifyUpdate(ACTIONS.WON, p); // Notify that the game is won
        }
    }

    // Handle the logic when a ship part is hit
    private void handleShipHit(SchiffTeil st, Position p) {
        boolean isDestroyed = true;

        // Check if all parts of the ship have been hit
        for (SchiffTeil teil : sp.getAllShipParts()) {
            if (teil.getId() == st.getId() && !teil.isGetroffen()) {
                isDestroyed = false; // Ship is not yet destroyed
                break;
            }
        }

        // Notify observers based on the ship's state
        if (isDestroyed) {
            notifyUpdate(ACTIONS.SHIP_DESTROYED, p, st.getId()); // Notify that the ship is destroyed
        } else {
            notifyUpdate(ACTIONS.SHIP_HIT, p); // Notify that a ship part was hit
        }
    }

    // Notify observers with a specific action and optional ship ID
    private void notifyUpdate(ACTIONS action, Position p, int... id) {
        setChanged(); // Mark the observable as changed
        notifyObservers(new SchiffMessage(action, p, id.length > 0 ? id[0] : -1)); // Notify observers with a message
    }

    // Getter for the number of shots taken
    public int getShots() {
        return shots;
    }

    // Getter for the number of remaining ships
    public int getShips() {
        return sp.getAnzahlSchiffe();
    }
}

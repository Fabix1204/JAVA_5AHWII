package A06.SchiffeVersenken;

// Indicates whether the field has been hit
public class Feld {
    private boolean getroffen;

    // Default constructor
    public Feld() {

    }

    // Getter for the 'getroffen' property
    public boolean isGetroffen() {
        return getroffen;
    }

    // Setter for the 'getroffen' property
    public void setGetroffen(boolean getroffen) {
        this.getroffen = getroffen;
    }

    // Override the toString method to represent the field's state
    @Override
    public String toString() {
        if (getroffen) {
            return "~"; // Represents a hit field
        } else {
            return "_"; // Represents an untouched field
        }
    }
}

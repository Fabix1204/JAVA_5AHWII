package A06.SchiffeVersenken;

// Class representing a position in the game
public class Position {
    // X-coordinate of the position
    int x;

    // Y-coordinate of the position
    int y;

    // Rotation flag (used for ship placement)
    boolean rotation;

    // Constructor for a position without rotation
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Constructor for a position with rotation
    public Position(int x, int y, boolean rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    // Getter for the X-coordinate
    public int getX() {
        return x;
    }

    // Getter for the Y-coordinate
    public int getY() {
        return y;
    }

    // Getter for the rotation flag
    public boolean getRotation() {
        return rotation;
    }

    // Override the equals method to compare positions
    @Override
    public boolean equals(Object obj) {
        // Cast the object to a Position and compare its attributes
        Position p = (Position) obj;
        return p.getX() == this.getX() && p.getY() == this.getY() && p.getRotation() == this.getRotation();
    }
}

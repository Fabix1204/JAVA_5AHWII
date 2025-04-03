package A06.SchiffeVersenken;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application implements Observer, EventHandler<ActionEvent> {

    // Model for the game logic
    private SchiffModel model;

    // Game field representation
    private SpielFeld sp = new SpielFeld();

    // 2D array of buttons representing the game grid
    private Button[][] buttons = new Button[sp.getFelder().length][sp.getFelder()[0].length];

    // Root pane for the JavaFX scene
    private Pane root = new Pane();

    // GridPane to hold the game buttons
    private GridPane gp = new GridPane();

    // Text element to display game messages
    private Text text = new Text();

    @Override
    public void start(Stage primaryStage) {
        // Initialize the game model and add this class as an observer
        this.model = new SchiffModel(sp);
        model.addObserver(this);  // Observer hinzufügen

        // Initialize the UI components
        init();

        // Add the grid and text to the root pane
        root.getChildren().addAll(gp, text);

        // Set up the scene and stage
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Schiffe Versenken");
        primaryStage.show();
    }

    public void init() {
        // Initialize the game field
        sp.init();

        // Clear the grid pane
        gp.getChildren().clear();

        // Create buttons for each field in the grid
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setId(i + " " + j); // Set button ID to its position
                buttons[i][j].setMinSize(50, 50); // Set button size
                buttons[i][j].setOnAction(this); // Add event handler for button clicks
                gp.add(buttons[i][j], i, j); // Add button to the grid
            }
        }

        // Configure the text element for displaying messages
        text.setX(150);
        text.setY(850);
        text.setStyle("-fx-font-size: 20;");
        // updateShipCount(); // Uncomment if ship count display is needed
    }

    @Override
    public void update(Observable o, Object arg) {
        // Handle updates from the model
        if (arg instanceof SchiffMessage) {
            SchiffMessage msg = (SchiffMessage) arg;
            System.out.println(msg);

            // Process the message and update the UI
            handleSchiffMessage(msg);
            updateBoard(msg);
        }
    }

    private void handleSchiffMessage(SchiffMessage msg) {
        // Handle specific game messages and update the text element
        switch (msg.getAction()) {
            case FIELD_OCCUPIED:
                text.setText("Feld bereits getroffen!"); // Field already hit
                break;
            case WON:
                text.setText("Spiel gewonnen!"); // Game won
                break;
            default:
                break;
        }
    }

    private void updateBoard(SchiffMessage msg) {
        // Update the game board based on the message
        Position p = msg.getPosition();
        int x = p.getX();
        int y = p.getY();

        switch (msg.getAction()) {
            case FIELD_HIT:
                buttons[x][y].setStyle("-fx-background-color: blue"); // Mark field as hit
                break;
            case SHIP_HIT:
                buttons[x][y].setStyle("-fx-background-color: red"); // Mark ship as hit
                break;
            case SHIP_DESTROYED:
                int id = msg.getId();
                // Mark all parts of the destroyed ship
                for (int i = 0; i < buttons.length; i++) {
                    for (int j = 0; j < buttons[i].length; j++) {
                        if (sp.getFelder()[i][j] instanceof SchiffTeil) {
                            SchiffTeil st = (SchiffTeil) sp.getFelder()[i][j];
                            if (st.getId() == id) {
                                buttons[i][j].setStyle("-fx-background-color: gray"); // Mark destroyed ship
                            }
                        }
                    }
                }
                break;
            case FIELD_OCCUPIED:
                break;
            case WON:
                text.setText("Spiel gewonnen!"); // Game won
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {
        // Handle button clicks
        String[] pos = ((Button) event.getSource()).getId().split(" ");
        Position p = new Position(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));

        // Notify the model of the selected position
        model.set(p);
    }
}

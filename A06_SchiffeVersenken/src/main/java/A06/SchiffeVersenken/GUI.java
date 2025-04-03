package A06.SchiffeVersenken;

import java.util.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application implements EventHandler<ActionEvent> {

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

    // Stage and components for the end screen
    private Stage endeStage = new Stage();
    private Button endeButton = new Button();
    private Scene endeScene;
    private TextField[] score = new TextField[2];

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the UI components
        init();

        // Add the grid and text to the root pane
        root.getChildren().setAll(gp, text);

        // Set up the scene and stage
        Scene scene = new Scene(root, 800, 900);
        primaryStage.setScene(scene);
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
                buttons[i][j].setMinSize(80, 80); // Set button size
                buttons[i][j].setOnAction(this); // Add event handler for button clicks
                gp.add(buttons[i][j], i, j); // Add button to the grid
            }
        }

        // Configure the text element for displaying messages
        text.setX(150);
        text.setY(850);
        text.setStyle("-fx-font-size: 50");

        // Synchronize the UI with the game state
        synchornisieren();
    }

    public void synchornisieren() {
        // Update the UI to reflect the current game state
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (sp.getFelder()[i][j] instanceof SchiffTeil) {
                    SchiffTeil s = (SchiffTeil) sp.getFelder()[i][j];
                    int[] a = sp.getIds();
                    for (int j2 = 0; j2 < a.length; j2++) {
                        if (sp.getFelder()[i][j].isGetroffen()) {
                            if (a[j2] == s.getId()) {
                                buttons[i][j].setStyle("-fx-background-color: red"); // Mark ship part as hit
                                break;
                            } else {
                                buttons[i][j].setStyle("-fx-background-color: gray"); // Mark destroyed ship
                            }
                        } else {
                            buttons[i][j].setStyle("-fx-background-color: lightblue"); // Mark untouched field
                        }
                    }
                } else {
                    if (!sp.getFelder()[i][j].isGetroffen()) {
                        buttons[i][j].setStyle("-fx-background-color: lightblue"); // Mark untouched field
                    } else {
                        buttons[i][j].setStyle("-fx-background-color: blue"); // Mark hit field
                    }
                }
            }
        }

        // Update the text element with the remaining ships
        text.setText("verbleibende Schiffe: " + sp.getAnzahlSchiffe());
    }

    @Override
    public void handle(ActionEvent event) {
        // Handle button clicks
        String[] s = ((Button) event.getSource()).getId().split(" ");
        Position p = new Position(Integer.parseInt(s[0]), Integer.parseInt(s[1]));

        // Check if the field has already been hit
        if (!(sp.getFelder()[p.getX()][p.getY()].isGetroffen())) {
            sp.schuss(p); // Notify the game logic of the shot
            synchornisieren(); // Update the UI
        }

        // Check if the game is over
        if (sp.fertig()) {
            ende(); // Show the end screen
        }
    }

    public void ende() {
        // Display the end screen with game statistics
        for (int i = 0; i < score.length; i++) {
            score[i] = new TextField();
            score[i].setEditable(false);
            score[i].setMinSize(300, 50);
            score[i].setStyle(
                    "-fx-background-color: lightgray; -fx-text-fill: black; -fx-font-size: 20; -fx-alignment: center");
        }

        // Display the number of water and ship hits
        score[0].setText("Wasser getroffen: " + sp.getWasserGetroffen());
        score[1].setText("Schiffe getroffen: " + sp.getSchiffeGetroffen());

        // Button to restart the game
        endeButton = new Button("Nochmal spielen");
        endeButton.setOnAction(e -> {
            endeStage.close();
            init(); // Reinitialize the game
        });
        endeButton.setStyle(
                "-fx-font-size: 20; -fx-background-color: lightgray; -fx-text-fill: black; -fx-border: solid; -fx-border-width: 1; -fx-border-color: black");
        endeButton.setMinSize(300, 50);

        // Layout for the end screen
        VBox v = new VBox();
        Pane endeRoot = new Pane();
        v.getChildren().addAll(score[0], score[1], endeButton);
        endeRoot.getChildren().addAll(v);

        // Set up the end screen scene and stage
        endeScene = new Scene(endeRoot, 300, 150);
        endeStage.setScene(endeScene);
        endeStage.show();

        // Synchronize the UI one last time
        synchornisieren();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}

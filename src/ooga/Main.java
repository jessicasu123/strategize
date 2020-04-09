package ooga;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import ooga.view.GameView;
import ooga.view.StartView;

import java.io.FileNotFoundException;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {


    /**
     * Initializes display.
     * @param stage
     */
    @Override
    public void start(Stage stage){

        try {
            GameView startScreen = new GameView(stage);
            startScreen.displayToStage(600,700);
        } catch (FileNotFoundException e) {
            System.out.println("Internal error, data file not found");
        }

    }


    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}

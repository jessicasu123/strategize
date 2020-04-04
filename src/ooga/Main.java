package ooga;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
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
            StartView startScreen = new StartView(stage,new Timeline());
            startScreen.displayToStage(500,500);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}

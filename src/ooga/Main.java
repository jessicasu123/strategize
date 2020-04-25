package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.view.StartView;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {


    /**
     * Initializes display.
     * @param stage - stage to display to
     */
    @Override
    public void start(Stage stage){

        StartView startScreen = new StartView(stage);
        startScreen.displayToStage(600,700);

    }


    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}

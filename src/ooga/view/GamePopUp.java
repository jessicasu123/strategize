package ooga.view;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class is responsible for displaying a pop up on top of the current view
 * and will automatically close if the user clicks anywhere outside
 * the pop up screen.
 *
 * @author: Jessica Su
 */
public abstract class GamePopUp {
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    public static final String PIECES_RESOURCES = DEFAULT_VIEW_RESOURCES + "images/pieces/";
    public static final int SPACING = 40;
    public static final double WIDTH_MULTIPLY_FACTOR = 2.0/3.0;
    public static final double HEIGHT_MULTIPLY_FACTOR = 4.0/5.0;
    public static final double X_OFFSET_FACTOR = 3.0;
    public static final double Y_OFFSET_FACTOR = 2.0;



    protected double popUpWidth;
    protected double popUpHeight;
    protected Pane myPopUpContents;
    protected JSONObject popUpScreenData;
    protected double xOffset;
    protected double yOffset;
    protected GameButtonManager popUpGameButtonManager;

    private String popUpFile;
    private Stage displayStage;
    private Popup popUp;

    public GamePopUp(Stage stage, int width, int height, String fileName, GameButtonManager gameButtonManager) {
        displayStage = stage;

        popUpWidth = width * WIDTH_MULTIPLY_FACTOR;
        popUpHeight = height * HEIGHT_MULTIPLY_FACTOR;

        xOffset = (width - popUpWidth)/X_OFFSET_FACTOR;
        yOffset = (height - popUpHeight)/Y_OFFSET_FACTOR;

        popUpFile = DEFAULT_RESOURCES + fileName;
        popUpGameButtonManager = gameButtonManager;
    }

    /**
     * Sets up a JSONObject to be able to read data relevant
     * to the pop-up screen (ex. names of buttons, labels, etc.)
     * @throws FileNotFoundException
     */
    protected void setUpJSONReader() throws FileNotFoundException {
        FileReader br = new FileReader(popUpFile);
        JSONTokener token = new JSONTokener(br);
        popUpScreenData = new JSONObject(token);
    }

    public Popup getPopUp() {
        return popUp;
    }

    /**
     * Force closes the pop-up
     */
    public void close() {
        popUp.hide();
    }

    /**
     * Displays the contents of the pop-up.
     * Will be called by any other view class that needs to show
     * a pop up on TOP of the current view.
     */
    public void display() {
        popUp= new Popup();
        setUpPopUp();
        createPopUpContents();
        popUp.getContent().add(myPopUpContents);
        popUp.setAutoHide(true);
        popUp.show(displayStage, displayStage.getX()+xOffset, displayStage.getY()+yOffset);
    }

    /**
     * Displays the contents of the pop up. This will be specific to
     * each kind of pop-up, as each one has different information to display.
     */
    public abstract void createPopUpContents();

    private void setUpPopUp() {
        myPopUpContents = new Pane();
        myPopUpContents.getStylesheets().add(STYLESHEET);
        myPopUpContents.getStyleClass().add("popUp");
        myPopUpContents.setMinWidth(popUpWidth);
        myPopUpContents.setMinHeight(popUpHeight);
    }



}

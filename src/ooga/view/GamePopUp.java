package ooga.view;
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
 */
public abstract class GamePopUp {
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    public static final String PIECES_RESOURCES = DEFAULT_VIEW_RESOURCES + "images/pieces/";
//    public static final String DATAFILE = DEFAULT_RESOURCES+ "CustomizationView.json";
    private String popUpFile;

    protected double popUpWidth;
    protected double popUpHeight;
    protected Pane myPopUpContents;
    protected JSONObject popUpScreenData;

    private Stage displayStage;
    private Popup popUp;
    private double xOffset;
    private double yOffset;

    public GamePopUp(Stage stage, int width, int height, String fileName) {
        displayStage = stage;

        popUpWidth = width * (2.0/3.0);
        popUpHeight = height * (4.0/5.0);

        xOffset = (width - popUpWidth)/2.5;
        yOffset = (height - popUpHeight)/2.0;

        popUpFile = DEFAULT_RESOURCES + fileName;
        setUpJSONReader();
    }

    private void setUpJSONReader()  {
        FileReader br = null;
        try {
            br = new FileReader(popUpFile);
        } catch (FileNotFoundException e) {
            System.out.println("MISSING CUSTOMIZATION JSON FILE");
            System.out.println(popUpFile);
        }
        JSONTokener token = new JSONTokener(br);
        popUpScreenData = new JSONObject(token);
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

    private void setUpPopUp() {
        myPopUpContents = new Pane();
        myPopUpContents.getStylesheets().add(STYLESHEET);
        myPopUpContents.getStyleClass().add("popUp");
        myPopUpContents.setMinWidth(popUpWidth);
        myPopUpContents.setMinHeight(popUpHeight);
    }

    /**
     * Displays the contents of the pop up. This will be specific to
     * each kind of pop-up, as each one has different information to display.
     */
    public abstract void createPopUpContents();
}

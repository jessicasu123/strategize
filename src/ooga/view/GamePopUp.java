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
 */
public abstract class GamePopUp {
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    public static final String PIECES_RESOURCES = DEFAULT_VIEW_RESOURCES + "images/pieces/";
    public static final int SPACING = 40;

    protected double popUpWidth;
    protected double popUpHeight;
    protected Pane myPopUpContents;
    protected JSONObject popUpScreenData;
    protected double xOffset;
    protected double yOffset;

    private String popUpFile;
    private Stage displayStage;
    private Popup popUp;

    public GamePopUp(Stage stage, int width, int height, String fileName) {
        displayStage = stage;

        //TODO: figure out way to let subclasses decide width and height
        popUpWidth = width * (2.0/3.0);
        popUpHeight = height * (4.0/5.0);

        xOffset = (width - popUpWidth)/3.0;
        yOffset = (height - popUpHeight)/2.0;
        popUpFile = DEFAULT_RESOURCES + fileName;
//        setUpJSONReader();
    }

    protected void setUpJSONReader()  {
        FileReader br = null;
        try {
            br = new FileReader(popUpFile);
        } catch (FileNotFoundException e) {
            System.out.println("MISSING CUSTOMIZATION JSON FILE:" + popUpFile);
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

    /**
     * Creates a horizontal container that many types of pop-ups might have.
     * @return - horizontal container with padding and spacing.
     */
    protected HBox createHorizontalContainer() {
        HBox container = new HBox();
        container.setPadding(new Insets(20,SPACING/2,0,SPACING/2));
        container.setSpacing(SPACING-10);
        return container;
    }

    /**
     * Creates a button that many types of pop-ups might have.
     * @param buttonName - the name of the button being created.
     * @return button for a pop up
     */
    //TODO: put this in its own class
    protected Button createButton(String buttonName) {
        Button b = new Button(buttonName);
        b.getStyleClass().add("gameButton");
        b.setId(buttonName.replaceAll("\\s", ""));
        b.setMinWidth(popUpWidth/3.0);
        return b;
    }
}

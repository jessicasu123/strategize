package ooga.view;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class GamePopUp {
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    public static final String PIECES_RESOURCES = DEFAULT_VIEW_RESOURCES + "images/pieces/";
    public static final String DATAFILE = DEFAULT_RESOURCES+ "CustomizationView.json";
    protected Stage displayStage;
    protected double popUpWidth;
    protected double popUpHeight;
    protected Pane myPopUpContents;
    protected JSONObject popUpScreenData;

    private double xOffset;
    private double yOffset;

    public GamePopUp(Stage stage, int width, int height) throws FileNotFoundException {
        displayStage = stage;

        popUpWidth = width * (2.0/3.0);
        popUpHeight = height * (4.0/5.0);

        xOffset = (width - popUpWidth)/2.5;
        yOffset = (height - popUpHeight)/2.0;

        setUpJSONReader();
    }

    private void setUpJSONReader() throws FileNotFoundException {
        FileReader br = new FileReader(DATAFILE);
        JSONTokener token = new JSONTokener(br);
        popUpScreenData = new JSONObject(token);
    }

    public void display() {
        Popup popUp= new Popup();
        setUpPopUp();
        createPopUpContents();
        popUp.getContent().add(myPopUpContents);
        popUp.setAutoHide(true);
        popUp.show(displayStage, displayStage.getX()+xOffset, displayStage.getY()+yOffset);
    }

    public void setUpPopUp() {
        myPopUpContents = new Pane();
        myPopUpContents.getStylesheets().add(STYLESHEET);
        myPopUpContents.getStyleClass().add("popUp");
        myPopUpContents.setMinWidth(popUpWidth);
        myPopUpContents.setMinHeight(popUpHeight);
    }

    public abstract void createPopUpContents();
}

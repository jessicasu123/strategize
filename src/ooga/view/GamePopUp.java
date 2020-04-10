package ooga.view;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public abstract class GamePopUp {
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    protected Stage displayStage;
    protected double popUpWidth;
    protected double popUpHeight;
    protected Pane myPopUpContents;

    private double xOffset;
    private double yOffset;

    public GamePopUp(Stage stage, int width, int height) {
        displayStage = stage;

        popUpWidth = width * (9.0/10.0);
        popUpHeight = height * (9.0/10.0);

        xOffset = (width - popUpWidth)/2.0;
        yOffset = (height - popUpHeight)/1.5;

    }

    public void display() {
        Popup popUp= new Popup();
        setUpPopUp();
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

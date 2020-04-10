package ooga.view;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public abstract class GamePopUp {
    protected Stage displayStage;
    protected double popUpWidth;
    protected double popUpHeight;

    public GamePopUp(Stage stage, int width, int height) {
        displayStage = stage;
        popUpWidth = width * (2.0/3.0);
        popUpHeight = height * (2.0/3.0);
    }

    public void display() {
        Popup popUp= new Popup();
        popUp.getContent().add(createPopUpContents());
        popUp.setAutoHide(true);
        popUp.show(displayStage);
    }

    public abstract Pane createPopUpContents();
}

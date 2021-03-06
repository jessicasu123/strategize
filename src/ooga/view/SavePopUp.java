package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ooga.view.components.GameTextFieldContainer;
import java.util.HashMap;
import java.util.Map;

/**
 * This class creates the Save PopUp
 * after the save button is clicked in the GameView
 * @author Brian Li
 */

public class SavePopUp extends GamePopUp {

    private Map<Button, String> buttonActionsMap;
    private GameTextFieldContainer mySaveFieldContainer;

    /**
     * Contructor for SavePopUp
     * @param stage - Stage to present the popup
     * @param width - width of popup
     * @param height - height of popup
     * @param fileName
     * @param buttonManager
     */
    public SavePopUp(Stage stage, int width, int height, String fileName, GameButtonManager buttonManager) {
        super(stage, width, height, fileName, buttonManager);
        popUpWidth = 200;
        popUpHeight = 240;
        xOffset = (width - popUpWidth)/2;
        yOffset = (height - popUpHeight)/2;

        buttonActionsMap = new HashMap<>();
        mySaveFieldContainer = new GameTextFieldContainer();
    }

    @Override
    protected void createPopUpContents() {
        myPopUpContents.getChildren().add(createSave());
    }

    protected String getFileName(){
        return mySaveFieldContainer.getText();
    }

    /**
     * @return VBox with textfield and save button
     */
    private VBox createSave(){
        VBox saveView = new VBox(SPACING);
        Label Title = new Label("Save Configuration");
        HBox textField = createTextFieldContainer();
        Button save = popUpGameButtonManager.createButton("Save", "saveConfig", popUpWidth/3.0); //createButton("Save");
        saveView.getChildren().addAll(Title,textField,save);
        saveView.setAlignment(Pos.CENTER);
        return saveView;
    }

    private HBox createTextFieldContainer() {
        String enterFile = "Enter File Name";
        String prompt = "File Name";
        return mySaveFieldContainer.createTextFieldContainer(enterFile,prompt);
    }
}

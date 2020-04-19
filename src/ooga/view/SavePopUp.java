package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import ooga.view.components.GameTextFieldContainer;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavePopUp extends GamePopUp {

    private Map<Button, String> buttonActionsMap;
    private GameTextFieldContainer mySaveFieldContainer;

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
    public void createPopUpContents() {
        myPopUpContents.getChildren().add(createSave());
    }

    public String getFileName(){
        return mySaveFieldContainer.getText();
    }


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
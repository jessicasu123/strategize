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
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavePopUp extends GamePopUp {

    private Map<Button, String> buttonActionsMap;
    public static TextField filetext;

    public SavePopUp(Stage stage, int width, int height, String fileName) {
        super(stage, width, height, fileName);
        popUpWidth = 200;
        popUpHeight = 240;
        xOffset = (width - popUpWidth)/2;
        yOffset = (height - popUpHeight)/2;

        buttonActionsMap = new HashMap<>();
    }

    @Override
    public void createPopUpContents() {
        myPopUpContents.getChildren().add(createSave());
    }

    public Map<Button, String> getButtonActionsMap() {
        return buttonActionsMap;}


    public String getFileName(){

        return filetext.toString();
    }


    private VBox createSave(){
        VBox saveView = new VBox(SPACING);
        saveView.setAlignment(Pos.CENTER);
        Label Title = new Label("Save Configuration");
        Label EnterFile = new Label("Enter File Name");
        filetext = new TextField();
        filetext.setPromptText("File Name");
        Button save = createButton("Save");
        buttonActionsMap.put(save, "saveConfig");
        saveView.getChildren().addAll(Title,EnterFile,filetext,save);
        return saveView;
    }
}

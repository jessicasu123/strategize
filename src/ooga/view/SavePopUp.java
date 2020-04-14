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

    public static final int SPACING = 40;
    private Stage myStage;
    private int mywidth;
    private int myheight;
    private JSONObject buttonInfo;
    private Map<Button, String> buttonActionsMap;
    public static TextField filetext;

    public SavePopUp(Stage stage, int width, int height) {
        super(stage, width, height);
        myStage = stage;
        myheight = height;
        mywidth = width;
        buttonActionsMap = new HashMap<>();
    }

    @Override
    public void createPopUpContents() {
        myPopUpContents.getChildren().add(createSaveView());
    }

    public Map<Button, String> getButtonActionsMap() {return buttonActionsMap;}

    private Button createButton(String buttonName) {
        Button b = new Button(buttonName);
        b.getStyleClass().add("gameButton");
        b.setMinWidth(popUpWidth/3.0);
        return b;
    }

    public String getFileName(){
        return filetext.toString();
    }


    private HBox createHorizontalContainer() {
        HBox container = new HBox();
        container.setPadding(new Insets(20,SPACING/2,0,SPACING/2));
        container.setSpacing(SPACING-10);
        return container;
    }
    private HBox createSaveView(){

        HBox saveView = createHorizontalContainer();
        saveView.setAlignment(Pos.CENTER);
        saveView.getChildren().add(createSave());
        return saveView;
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

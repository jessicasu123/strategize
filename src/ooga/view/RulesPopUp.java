package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RulesPopUp extends GamePopUp {
    public static final int SPACING = 40;

    private String gameData;
    private JSONObject gameFileData;
    private List<String> myRules;

    public RulesPopUp(Stage stage, int width, int height, String file) throws FileNotFoundException {
        super(stage, width, height);
        gameData = file;
        FileReader br = new FileReader(DEFAULT_RESOURCES + gameData);
        JSONTokener token = new JSONTokener(br);
        gameFileData = new JSONObject(token);
    }

    @Override
    public void createPopUpContents() {
        myPopUpContents.getChildren().add(createRules());
    }

    private void getList(){
        JSONArray gameData = gameFileData.getJSONArray("Rules");
        for(Object js: gameData){
            myRules.add(js.toString());
        }
    }

    private VBox createRules(){
        myRules = new ArrayList<String>();
        getList();
        VBox RuleView = new VBox(SPACING);
        RuleView.setAlignment(Pos.CENTER);
        Label Title = new Label("How to Play");
        RuleView.getChildren().add(Title);
        for(int i = 0;i<myRules.size();i++){
            Label Rule = new Label(myRules.get(i));
            Rule.setMinWidth(popUpWidth);
            Rule.setAlignment(Pos.CENTER);
            RuleView.getChildren().add(Rule);
        }
        return RuleView;
    }
}



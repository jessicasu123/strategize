package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RulesPopUp extends GamePopUp {
    public static final int SPACING = 40;

    private JSONObject gameFileData;
    private List<String> myRules;

    public RulesPopUp(Stage stage, int width, int height, String file, GameButtonManager buttonManager) throws FileNotFoundException {
        super(stage, width, height, file, buttonManager);
        setUpJSONReader();
        gameFileData = popUpScreenData;
    }

    @Override
    protected void createPopUpContents() {
        myPopUpContents.getChildren().add(createRules());
    }

    private void getList(){
        JSONArray gameData = gameFileData.getJSONArray("Rules");
        for(Object js: gameData){
            myRules.add(js.toString());
        }
    }

    private VBox createRules(){
        myRules = new ArrayList<>();
        getList();
        VBox RuleView = new VBox(SPACING);
        RuleView.setAlignment(Pos.CENTER);
        Label Title = new Label("How to Play");
        RuleView.getChildren().add(Title);
        for (String myRule : myRules) {
            Label Rule = new Label(myRule);
            Rule.setAlignment(Pos.CENTER);
            Rule.setWrapText(true);
            Rule.setMinWidth(popUpWidth);
            RuleView.getChildren().add(Rule);
        }
        RuleView.setFillWidth(true);
        return RuleView;
    }
}



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

/**
 * This class creates the Rules PopUp
 * after the rules button is clicked in the GameView
 * @author Brian Li
 */
public class RulesPopUp extends GamePopUp {
    public static final int SPACING = 40;

    private JSONObject gameFileData;
    private List<String> myRules;

    /**
     * Contructor for RulesPopUp
     * @param stage - Stage to present the popup
     * @param width - width of popup
     * @param height - height of popup
     * @param file
     * @param buttonManager
     * @throws FileNotFoundException
     */
    public RulesPopUp(Stage stage, int width, int height, String file, GameButtonManager buttonManager) throws FileNotFoundException {
        super(stage, width, height, file, buttonManager);
        setUpJSONReader();
        gameFileData = popUpScreenData;
    }

    @Override
    protected void createPopUpContents() {
        myPopUpContents.getChildren().add(createRules());
    }

    /**
     * get List of Rules from data file
     */
    private void getList(){
        JSONArray gameData = gameFileData.getJSONArray("Rules");
        for(Object js: gameData){
            myRules.add(js.toString());
        }
    }

    /**
     * Create and return a VBox of the Rule data
     */
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



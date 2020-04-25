package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for creating the panel in GameView
 * that contains all the navigation buttons.
 */
public class NavigationPanel {
    public static final int PADDING = 20;
    public static final int SPACING = 40;
    public static final int MIN_WIDTH = 100;

    private int myWidth;
    private Map<Button, String> buttonActionsMap;
    private JSONObject gameScreenData;
    private GameButtonManager navButtonManager;

    public NavigationPanel(GameButtonManager gameButtonManager, int width, JSONObject gameData) {
        myWidth = width;
        buttonActionsMap = new HashMap<>();
        gameScreenData = gameData;
        navButtonManager = gameButtonManager;
    }
    /**
     * creates a navigation bar of control buttons (restart, back to menu, save, make move)
     * @return VBox object containing buttons that allow user to control game
     */
    protected VBox createNavigationBar(){
        VBox GridContainer = new VBox(PADDING);
        HBox navcontainer = new HBox(SPACING);
        HBox movecontainer = new HBox(SPACING);

        navcontainer.setAlignment(Pos.CENTER);
        movecontainer.setAlignment(Pos.CENTER);

        JSONObject buttonTexts = gameScreenData.getJSONObject("Buttons").getJSONObject("NavigationButtons");
        Button restart = navButtonManager.createButton("Restart", buttonTexts.getString("Restart"), MIN_WIDTH);
        Button setup = navButtonManager.createButton("Back to Setup", buttonTexts.getString("Back to Setup"), MIN_WIDTH);
        Button menu = navButtonManager.createButton("Back to Main Menu", buttonTexts.getString("Back to Main Menu"), MIN_WIDTH);
        Button save = navButtonManager.createButton("Save", buttonTexts.getString("Save"), MIN_WIDTH);
        JSONObject makeMoveText = gameScreenData.getJSONObject("Buttons").getJSONObject("MakeMoveButton");
        Button makeMove =  navButtonManager.createButton("MAKE MOVE", makeMoveText.getString("MAKE MOVE"), myWidth*(2.0/3.0));

        navcontainer.getChildren().addAll(restart, setup, menu, save);
        movecontainer.getChildren().add(makeMove);

        GridContainer.getChildren().addAll(movecontainer,navcontainer);
        return GridContainer;
    }




}

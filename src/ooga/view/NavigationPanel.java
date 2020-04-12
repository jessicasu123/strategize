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
    public static final int SPACING = 50;
    public static final int MIN_WIDTH = 100;

    private int myWidth;
    private Map<Button, String> buttonActionsMap;
    private JSONObject gameScreenData;

    public NavigationPanel(int width, JSONObject gameData) {
        myWidth = width;
        buttonActionsMap = new HashMap<>();
        gameScreenData = gameData;
    }
    /**
     * creates a navigation bar of control buttons (restart, back to menu, save, make move)
     * @return VBox object containing buttons that allow user to control game
     */
    public VBox createNavigationBar(){
        VBox GridContainer = new VBox(PADDING);
        HBox navcontainer = new HBox(SPACING);
        HBox movecontainer = new HBox(SPACING);

        navcontainer.setAlignment(Pos.CENTER);
        movecontainer.setAlignment(Pos.CENTER);

        JSONObject buttonTexts = gameScreenData.getJSONObject("Buttons").getJSONObject("NavigationButtons");
        Button menu = createButton(buttonTexts, "Back to Menu");
        Button restart = createButton(buttonTexts, "Restart");
        Button save = createButton(buttonTexts, "Save");
        JSONObject makeMoveText = gameScreenData.getJSONObject("Buttons").getJSONObject("MakeMoveButton");
        Button makeMove = createButton(makeMoveText,"MAKE MOVE");
        makeMove.setMinWidth(myWidth*(2.0/3.0));

        navcontainer.getChildren().addAll(menu, restart, save);
        movecontainer.getChildren().add(makeMove);

        GridContainer.getChildren().addAll(movecontainer,navcontainer);
        return GridContainer;
    }

    /**
     * @param buttonTexts - JSON object containing button text mappings
     * @param key - key object to get button text value
     * @return Button with desired properties
     */
    private Button createButton(JSONObject buttonTexts, String key) {
        Button button = new Button(key); //buttonTexts.getString(key)
        buttonActionsMap.put(button, buttonTexts.getString(key));
        button.setId(key.replaceAll("\\s", ""));
        button.getStyleClass().add("gameButton");
        button.setMinWidth(MIN_WIDTH);
        //button.setOnAction(handler);
        return button;
    }



    /**
     * Gives the GameView the buttons created in this panel, as well
     * as the method name of the actions they should be associated with.
     * These methods are created/called in GameView.
     * @return - Map with keys as the Buttons controlling navigation and values
     * as the string names of the methods (in GameView) that the buttons trigger
     */
    //TODO: put this in super class/figure out better way to do this
    public Map<Button,String> getButtonActionsMap() {
        return buttonActionsMap;
    }
}

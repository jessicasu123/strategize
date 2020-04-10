package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

/**
 * Responsible for creating the panel at the top of GameView
 * that contains all the icons (help, settings, chat), as well
 * as the status of the user player, the agent player, and the
 * overall scores for both.
 */
//TODO: hook up buttons in status panel to popup views
public class StatusPanel {
    public static final int SPACING = 30;
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String ICON_RESOURCES = DEFAULT_VIEW_RESOURCES + "navicons/";
    public static final String PIECES_RESOURCES = DEFAULT_VIEW_RESOURCES + "images/pieces/";

    private JSONObject gameScreenData;

    public StatusPanel(JSONObject gameData) {
        gameScreenData = gameData;
    }

    /**
     * @return VBox object containing top buttons and status bar
     */
    public VBox createStatusPanel(String userImage, String agentImage){
        VBox statusPanel = new VBox(SPACING);
        statusPanel.getChildren().addAll(createTopButtons(),
                createStatusBar(userImage,agentImage));

        return statusPanel;
    }

    /**
     * @return HBox object containing status bar
     */
    private HBox createStatusBar(String userImage, String agentImage){
        HBox container = new HBox(SPACING);
        container.setAlignment(Pos.TOP_CENTER);
        JSONObject buttonTexts = gameScreenData.getJSONObject("StatusBar");
        ImageView player1icon = setUpGameIcon(userImage);
        ImageView opponenticon = setUpGameIcon(agentImage);
        TextField playerscore = new TextField();
        playerscore.setMaxWidth(50);
        playerscore.setMinHeight(30);
        TextField opponentscore = new TextField();
        opponentscore.setMaxWidth(50);
        opponentscore.setMinHeight(30);
        Label player = new Label(buttonTexts.getString("Player"));
        player.setMinHeight(30);
        Label opponent = new Label(buttonTexts.getString("Opponent"));
        opponent.setMinHeight(30);
        container.getChildren().addAll(player1icon,player,playerscore,opponenticon,opponent,opponentscore);
        return container;
    }


    /**
     * @return HBox object containing top buttons
     */
    private HBox createTopButtons(){
        HBox topButtons = new HBox(SPACING+80);
        topButtons.setAlignment(Pos.TOP_CENTER);
        JSONObject buttonIcons = gameScreenData.getJSONObject("Icons").getJSONObject("ButtonIcons");
        Button settings = setUpGameIconButton(buttonIcons,"settings");
        Button chat = setUpGameIconButton(buttonIcons,"chat");
        Button help = setUpGameIconButton(buttonIcons,"help");
        topButtons.getChildren().addAll(help, chat, settings);
        return topButtons;
    }

    /**
     * @param key - key object to get icon value
     * @return Button with desired properties
     */
    private ImageView setUpGameIcon(String key) {
        Image img = new Image(PIECES_RESOURCES + (key));
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(30);
        gameIcon.setPreserveRatio(true);
        return gameIcon;
    }

    /**
     * @param game - JSON object containing icons
     * @param key - key object to get icon value
     * @return Button with desired properties
     */
    private Button setUpGameIconButton(JSONObject game, String key) {
        Image img = new Image(ICON_RESOURCES + game.getString(key));
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(30);
        gameIcon.setPreserveRatio(true);
        Button button = new Button();
        button.getStyleClass().add("gameButton");
        button.setGraphic(gameIcon);
        return button;
    }
}

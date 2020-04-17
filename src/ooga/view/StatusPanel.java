package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.model.engine.Game;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private ImageView userIcon;
    private ImageView opponentIcon;
    private TextField userScore;
    private TextField opponentScore;

    private Map<Button,String> buttonActionsMap;
    private GameButtonManager statusButtonManager;

    public StatusPanel(GameButtonManager gameButtonManager, JSONObject gameData) {
        gameScreenData = gameData;
        statusButtonManager = gameButtonManager;
        buttonActionsMap = new HashMap<>();
    }


    /**
     * if the user chooses a new preference for player/opponent images, the icons
     * in the status bar are updated as well
     * @param playerImage - the new image that the player has chosen
     * @param opponentImage - the new image to represent the opponent(agent)
     */
    public void updatePlayerIcons(String playerImage, String opponentImage) {
        userIcon.setImage(new Image(PIECES_RESOURCES + playerImage));
        opponentIcon.setImage(new Image(PIECES_RESOURCES + opponentImage));
    }

    /**
     * When a game ends, gameView calls this method to update the TextFields
     * for current win counts of the user and opponent
     * If it's a tie, both get +1 point
     * @param userWinCount new user win count
     * @param opponentWinCount new opponent win count
     */
    public void updateWinnerCounts(int userWinCount, int opponentWinCount) {
        userScore.setText(String.valueOf(userWinCount));
        opponentScore.setText(String.valueOf(opponentWinCount));
    }

    /**
     * @return VBox object containing top buttons and status bar
     */
    public VBox createStatusPanel(String userImage, String agentImage){
        VBox statusPanel = new VBox(SPACING);
        statusPanel.getChildren().addAll(createTopButtons(),
                createStatusBar(userImage, agentImage));

        return statusPanel;
    }

    /**
     * @return HBox object containing status bar
     */
    private HBox createStatusBar(String userImage, String agentImage){
        HBox container = new HBox(SPACING);
        container.setAlignment(Pos.TOP_CENTER);
        JSONObject buttonTexts = gameScreenData.getJSONObject("StatusBar");
        userIcon = setUpGameIcon(userImage);
        opponentIcon = setUpGameIcon(agentImage);
        userScore = createTextField();
        opponentScore = createTextField();
        Label player = new Label(buttonTexts.getString("Player"));
        player.setMinHeight(30);
        Label opponent = new Label(buttonTexts.getString("Opponent"));
        opponent.setMinHeight(30);
        container.getChildren().addAll(userIcon,player, userScore,opponentIcon,opponent,opponentScore);
        return container;
    }

    private TextField createTextField() {
        TextField scoreField = new TextField();
        scoreField.setDisable(true);
        scoreField.setMaxWidth(50);
        scoreField.setMinHeight(30);
        return scoreField;
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
        String[] buttonInfo = game.getString(key).split(",");
        String imgName = buttonInfo[0];
        String actionMethodName = buttonInfo[1];
        return statusButtonManager.createButtonWithImage("", actionMethodName, 30, ICON_RESOURCES + imgName);
    }
}

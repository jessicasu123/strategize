package ooga.view;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ooga.model.engine.Game;
import ooga.view.components.GameIcon;
import javafx.scene.effect.ColorInput;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for creating the panel at the top of GameView
 * that contains all the icons (help, settings, chat), as well
 * as the status of the user player, the agent player, and the
 * overall scores for both.
 */
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
    private Label player;
    private Label opponent;

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
        userIcon = new GameIcon().createGameIcon(PIECES_RESOURCES+ userImage);
        opponentIcon = new GameIcon().createGameIcon(PIECES_RESOURCES+agentImage);
        userScore = createTextField();
        opponentScore = createTextField();
        player = new Label(buttonTexts.getString("PlayerInfoHolder"));
        player.setMinHeight(30);
        opponent = new Label(buttonTexts.getString("Opponent"));
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

    public void updateStatusBarForMultiPiecePlayers(String userColor, String opponentColor) {
        Bounds bound = userIcon.getBoundsInLocal();
        userIcon.setEffect(new ColorInput(bound.getMinX(), bound.getMinY(),
                bound.getWidth(), bound.getHeight(), Color.valueOf(userColor)));
        opponentIcon.setEffect(new ColorInput(bound.getMinX(), bound.getMinY(),
                bound.getWidth(), bound.getHeight(), Color.valueOf(opponentColor)));
    }

    /**
     * Allows another view class to change the text color of the labels.
     * Useful when changing to a different mode (light or dark mode).
     * @param color
     */
    public void setLabelTextColor(String color) {
        player.setTextFill(Color.valueOf(color));
        opponent.setTextFill(Color.valueOf(color));
    }

    /**
     * @return HBox object containing top buttons
     */
    private HBox createTopButtons(){
        HBox topButtons = new HBox(SPACING+80);
        topButtons.setAlignment(Pos.TOP_CENTER);
        JSONObject buttonIcons = gameScreenData.getJSONObject("Icons").getJSONObject("ButtonIcons");
        Button settings = setUpGameIconButton(buttonIcons,"settings");
        Button help = setUpGameIconButton(buttonIcons,"help");
        topButtons.getChildren().addAll(help,settings);
        return topButtons;
    }

    /**
     * @param game - JSON object containing icons
     * @param key - key object to get icon value
     * @return GameButton with desired properties
     */
        private Button setUpGameIconButton(JSONObject game, String key) {
        String[] buttonInfo = game.getString(key).split(",");
        String imgName = buttonInfo[0];
        String actionMethodName = buttonInfo[1];
        return statusButtonManager.createButtonWithImage("", actionMethodName, 30, ICON_RESOURCES + imgName);
    }
}

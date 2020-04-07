package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class allows a user to make selections such choosing to play as player 1
 * or player 2, change board dimensions, etc. before starting the game.
 * It is also responsible for creating the Controller and passing it to GameView
 */

public class GameSetupOptions {
    public static final int PADDING = 20;
    public static final int SPACING = 10;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String DATAFILE = DEFAULT_RESOURCES+ "GameSetupOptions.json";
    public static final String ICON_RESOURCES = DEFAULT_VIEW_RESOURCES + "icons/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    public static final double BUTTON_FONT_FACTOR = 0.125;
    private Stage myStage;
    private JSONObject gameFileData;
    private JSONObject setupData;
    private int userPlayerID;
    private int agentPlayerID;


    /**
     * Creates the GameSetupOptions object based on JSON instructions
     * Uses game file instructions to set up options for players and board dimensions
     * @param displayStage - the stage that the screen will be displayed on
     * @param gameFileName - the game file for the game the user has chosen
     * @throws FileNotFoundException - if the JSON file can't be found
     */
    public GameSetupOptions(Stage displayStage, String gameFileName) throws FileNotFoundException {
        myStage = displayStage;
        FileReader br = new FileReader(gameFileName);
        JSONTokener token = new JSONTokener(br);
        gameFileData = new JSONObject(token);

        FileReader brData = new FileReader(DATAFILE);
        JSONTokener tokenData = new JSONTokener(br);
        setupData = new JSONObject(tokenData);

        displayToStage();
    }

    private void displayToStage(){
        Scene startScene = makeSetupDisplay();
        myStage.setScene(startScene);
        myStage.show();
    }

    private Scene makeSetupDisplay(){
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(SPACING, 0, SPACING,0));
        Scene setupScene = new Scene(root, WIDTH, HEIGHT);
        setupScene.getStylesheets().add(STYLESHEET);
        root.getStyleClass().add("root");

        root.setTop(addTitle());
        root.setCenter(createGameOptionHolder(WIDTH, HEIGHT));
        root.setMaxWidth(GameSetupOptions.WIDTH);
        return setupScene;
    }

    private Text addTitle(){
        String titleText = gameFileData.getJSONObject("Text").getJSONObject("LabelText").getString("Title");
        Text title = new Text(titleText.toUpperCase());
        title.getStyleClass().add("title");
        BorderPane.setAlignment(title, Pos.CENTER);
        return title;
    }

    private VBox createGameOptionHolder(int width, int height) {
        VBox gameOptions = new VBox();
        HBox opponentOptions = setOpponentOptions();
        HBox playerOptions = setPlayerOptions();
//        Hbox boardOptions = setBoardInputBox();
//        Button start = creatStartButton();
//        gameOptions.getChildren().addAll(opponentOptions, playerOptions, boardOptions);
        // TODO: potentially add winStatus choices here (Eg: connect5?)
        return gameOptions;
    }

    /**
     * Sets options for playing against the computer or against another player.
     * Defaults to computer
     * @return
     */
    private HBox setOpponentOptions() {
        HBox opponentOptions = new HBox(SPACING);

        ToggleGroup group = new ToggleGroup();
        RadioButton vsComputer = new RadioButton(setupData.getJSONObject("Text").getJSONObject("LabelText").getString("VsComputer"));
        vsComputer.setToggleGroup(group);
        vsComputer.setSelected(true);

        RadioButton vsPlayer = new RadioButton(setupData.getJSONObject("Text").getJSONObject("LabelText").getString("VsPlayer"));
        vsPlayer.setToggleGroup(group);

        // TODO once play vs. player mode is added: Add a radio button listener to provide this information to Controller

        return opponentOptions;
    }

    private HBox setPlayerOptions() {
        HBox playerOptions = new HBox(SPACING);
        Text selectionText = new Text(setupData.getJSONObject("Text").getJSONObject("LabelText").getString("SelectPlayer"));
        Image player1Image = new Image(getClass().getResourceAsStream(gameFileData.getJSONObject("Player1").getString("Image")));
        Image player2Image = new Image(getClass().getResourceAsStream(gameFileData.getJSONObject("Player2").getString("Image")));
        Button player1Button = new Button("Player1", new ImageView(player1Image));
        Button player2Button = new Button("Player2", new ImageView(player2Image));
        playerOptions.getChildren().addAll(selectionText, player1Button, player2Button);
        return playerOptions;
    }

    private HBox boardOptions() {
        // TODO: add defaults permissible values for boards from JSON
        return null;
    }

    private Button creatStartButton() {
        return null;
    }

}

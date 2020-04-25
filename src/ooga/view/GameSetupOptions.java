package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.components.ErrorAlerts;
import ooga.view.components.GameButton;
import ooga.view.components.GameDropDown;
import ooga.view.components.GameScene;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This class allows a user to make selections such choosing to play as player 1
 * or player 2, change board dimensions, etc. before starting the game.
 * It is also responsible for creating the Controller and passing it to GameView
 */

public class GameSetupOptions {
    public static final int PADDING = 35;
    public static final int SPACING = 25;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int START_DIM = 500;
    public static final String DEFAULT_FILE_RESOURCES = "src/resources/gameFiles/";
    public static final String DATAFILE = "src/resources/GameSetupOptions.json";
    public static final String PIECE_ICON_RESOURCES = "resources/images/pieces/";
    private Stage myStage;
    private JSONObject gameFileData;
    private String gameFileName;
    private JSONObject setupData;
    private String userPlayerID;
    private String opponent;
    private GameDropDown dimensionDropdown;


    /**
     * Creates the GameSetupOptions object based on JSON instructions
     * Uses game file instructions to set up options for players and board dimensions
     * @param displayStage - the stage that the screen will be displayed on
     * @param fileName - the game file for the game the user has chosen
     * @throws FileNotFoundException - if the JSON file can't be found
     */
    public GameSetupOptions(Stage displayStage, String fileName) throws FileNotFoundException {
        gameFileName = fileName;
        myStage = displayStage;
        FileReader br = new FileReader(DEFAULT_FILE_RESOURCES + gameFileName);
        JSONTokener token = new JSONTokener(br);
        gameFileData = new JSONObject(token);

        FileReader br2 = new FileReader(DATAFILE);
        JSONTokener token2 = new JSONTokener(br2);
        setupData = new JSONObject(token2);

        displayToStage();
    }

    protected void displayToStage(){
        Scene startScene = makeSetupDisplay();
        myStage.setScene(startScene);
        myStage.show();
    }

    private Scene makeSetupDisplay(){
        VBox gameSetUp = createGameSetupHolder();
        String title = setupData.getJSONObject("Text").getJSONObject("LabelText").getString("Title");
        return new GameScene().createScene(SPACING,WIDTH,HEIGHT,gameSetUp,title);
    }

    private VBox createGameSetupHolder() {
        VBox gameOptions = new VBox(PADDING);
        JSONObject labelText = setupData.getJSONObject("Text").getJSONObject("LabelText");
        JSONObject buttonText = setupData.getJSONObject("Text").getJSONObject("ButtonText");
        HBox opponentOptions = createOpponentOptions(labelText);
        HBox playerOptions = createPlayerOptions(opponentOptions.getAlignment(), labelText);
        HBox boardOptions = createBoardOptions(playerOptions.getAlignment(), labelText);
        Button start = createStartButton(buttonText);
        Button mainMenu = createMenuButton(buttonText);
        gameOptions.getChildren().addAll(opponentOptions, playerOptions, boardOptions, start, mainMenu);
        gameOptions.setAlignment(Pos.CENTER);
        return gameOptions;
    }

    /**
     * Sets options for playing against the computer or against another player.
     * Defaults to computer
     * @return HBox with radio buttons to select opponent type
     */
    private HBox createOpponentOptions(JSONObject labelText) {
        Text selectionPrompt = new Text(labelText.getString("SelectOpponent"));
        selectionPrompt.setId("SelectOpponentText");
        ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener((ob, o, n) -> {
            RadioButton rb = (RadioButton)group.getSelectedToggle();
            if (rb != null) {
                opponent = rb.getText();
            }
        });
        RadioButton vsComputer = createOpponentButton(labelText, group, "VsComputer");
        RadioButton vsPlayer = createOpponentButton(labelText, group, "VsPlayer");
        vsComputer.setSelected(true);
        HBox opponentOptions = new HBox(SPACING, selectionPrompt, vsComputer, vsPlayer);
        opponentOptions.setAlignment(Pos.CENTER);
        return opponentOptions;
    }

    private RadioButton createOpponentButton(JSONObject labelText, ToggleGroup group, String vs) {
        RadioButton vsSelection = new RadioButton(labelText.getString(vs));
        vsSelection.setToggleGroup(group);
        vsSelection.setId(vs);
        return vsSelection;
    }

    private HBox createPlayerOptions(Pos position, JSONObject labelText) {
        HBox playerOptions = new HBox(SPACING);
        Text selectionPrompt = new Text(labelText.getString("SelectPlayer"));
        selectionPrompt.setId("SelectPlayerText");
        ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener((ob, o, n) -> {
            RadioButton rb = (RadioButton)group.getSelectedToggle();
            if (rb != null) {
                userPlayerID = rb.getText();
            }
        });
        RadioButton player1Button = createPlayerRadioButton(group,"Player1");
        RadioButton player2Button = createPlayerRadioButton(group,"Player2");
        player1Button.setSelected(true);
        playerOptions.getChildren().addAll(selectionPrompt, player1Button, player2Button);
        playerOptions.setAlignment(position);
        return playerOptions;
    }

    private RadioButton createPlayerRadioButton(ToggleGroup group, String player) {
        int iconSize = WIDTH/15;
        boolean hasMultiplePieces = gameFileData.getBoolean("MultiplePiecesPerSquare");
        boolean hasSpecialStateColors = gameFileData.getJSONObject("Player1").getJSONArray("Colors").length() > 1;
        String imageName = gameFileData.getJSONObject(player).getJSONArray("Images").getString(0).split(",")[0];
        Image playerImage = new Image(PIECE_ICON_RESOURCES + imageName, iconSize, iconSize, true, true);
        RadioButton playerButton = new RadioButton(player);

        if (hasMultiplePieces && hasSpecialStateColors) {
            String colorName = gameFileData.getJSONObject(player).getJSONArray("Colors").getString(1);
            playerButton.setStyle("-fx-mark-color: " + colorName);
        } else {
            playerButton.setGraphic(new ImageView(playerImage));
        }
        playerButton.setToggleGroup(group);
        playerButton.setId(player);
        return playerButton;
    }

    private HBox createBoardOptions(Pos position, JSONObject labelText) {
        JSONArray boardArray = gameFileData.getJSONArray("DimensionOptions");
        String prompt = labelText.getString("BoardDropdown");
        String label = labelText.getString("BoardSizeText");
        ArrayList<String> options = new ArrayList<>();
        for(int i = 0; i < boardArray.length(); i++ ){
            options.add(boardArray.getString(i));
        }
        dimensionDropdown = new GameDropDown();
        return dimensionDropdown.createDropDownContainer(position, options, prompt + gameFileData.getString("Default"), label);
    }

    private Button createStartButton(JSONObject buttonText) {
        Button start = new GameButton().createGameButton(buttonText.getString("Start"));
        start.setOnAction(e -> {
            try {
                String chosenDimension = getChosenDimension();
                Controller c = new Controller(gameFileName, userPlayerID, opponent, chosenDimension);
                new GameView(myStage, c);
            } catch(Exception ex){
                new ErrorAlerts(ex.getClass().getCanonicalName(), ex.getMessage());
                new StartView(myStage).displayToStage(WIDTH,HEIGHT);
            }
        });
        return start;
    }

    private Button createMenuButton(JSONObject buttonText) {
        Button backToMenu = new GameButton().createGameButton(buttonText.getString("Menu"));
        backToMenu.setOnAction(event -> {
            StartView sv = new StartView(myStage);
            sv.displayToStage(START_DIM,START_DIM);
        });
        return backToMenu;
    }

    private String getChosenDimension() {
        if (dimensionDropdown.getValue() == null) {
            return gameFileData.getString("Default");
        }
        return dimensionDropdown.getValue();
    }

}

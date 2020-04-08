package ooga.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String DATAFILE = DEFAULT_RESOURCES+ "GameSetupOptions.json";
    public static final String PIECE_ICON_RESOURCES = DEFAULT_VIEW_RESOURCES + "icons/pieces/";
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
        FileReader br = new FileReader(DEFAULT_RESOURCES + gameFileName);
        JSONTokener token = new JSONTokener(br);
        gameFileData = new JSONObject(token);

        FileReader br2 = new FileReader(DATAFILE);
        JSONTokener token2 = new JSONTokener(br2);
        setupData = new JSONObject(token2);

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
        root.setCenter(createGameSetupHolder(WIDTH, HEIGHT));
        root.setMaxWidth(GameSetupOptions.WIDTH);
        return setupScene;
    }

    private Text addTitle(){
        String titleText = setupData.getJSONObject("Text").getJSONObject("LabelText").getString("Title");
        Text title = new Text(titleText.toUpperCase());
        title.getStyleClass().add("title");
        BorderPane.setAlignment(title, Pos.CENTER);
        return title;
    }

    private VBox createGameSetupHolder(int width, int height) {
        VBox gameOptions = new VBox(PADDING);
        HBox opponentOptions = createOpponentOptions();
        HBox playerOptions = createPlayerOptions(opponentOptions.getAlignment());
        HBox boardOptions = createBoardOptions(playerOptions.getAlignment());
        Button start = creatStartButton(boardOptions.getAlignment());
        gameOptions.getChildren().addAll(opponentOptions, playerOptions, boardOptions, start);
        gameOptions.setAlignment(Pos.CENTER);
        // TODO: potentially add winStatus choices here (Eg: connect5?)
        return gameOptions;
    }

    /**
     * Sets options for playing against the computer or against another player.
     * Defaults to computer
     * @return
     */
    private HBox createOpponentOptions() {
        Text selectionPrompt = new Text(setupData.getJSONObject("Text").getJSONObject("LabelText").getString("SelectOpponent"));
        ToggleGroup group = new ToggleGroup();
        RadioButton vsComputer = new RadioButton(setupData.getJSONObject("Text").getJSONObject("LabelText").getString("VsComputer"));
        vsComputer.setToggleGroup(group);
        vsComputer.setSelected(true);

        RadioButton vsPlayer = new RadioButton(setupData.getJSONObject("Text").getJSONObject("LabelText").getString("VsPlayer"));
        vsPlayer.setToggleGroup(group);

        HBox opponentOptions = new HBox(SPACING, selectionPrompt, vsComputer, vsPlayer);
        opponentOptions.setAlignment(Pos.CENTER);
        // TODO once play vs. player mode is added: Add a radio button listener to provide this information to Controller

        return opponentOptions;
    }

    private HBox createPlayerOptions(Pos position) {
        HBox playerOptions = new HBox(SPACING);
        Text selectionPrompt = new Text(setupData.getJSONObject("Text").getJSONObject("LabelText").getString("SelectPlayer"));
        ToggleGroup group = new ToggleGroup();
        RadioButton player1Button = getPlayerRadioButton(group,"Player1");
        RadioButton player2Button = getPlayerRadioButton(group,"Player2");
        playerOptions.getChildren().addAll(selectionPrompt, player1Button, player2Button);
        playerOptions.setAlignment(position);
        return playerOptions;
    }

    private RadioButton getPlayerRadioButton(ToggleGroup group, String player1) {
        int iconSize = WIDTH/15;
        Image playerImage = new Image(PIECE_ICON_RESOURCES + gameFileData.getJSONObject(player1).getString("Image"), iconSize, iconSize, true, true);
        RadioButton playerButton = new RadioButton(player1);
        playerButton.setGraphic(new ImageView(playerImage));
        playerButton.setToggleGroup(group);
        return playerButton;
    }

    private HBox createBoardOptions(Pos position) {
        // TODO: add listener for defaults permissible values for boards from JSON
        HBox boardOptions = new HBox(SPACING);
        Text loadDimensionsLabel = new Text(setupData.getJSONObject("Text").getJSONObject("LabelText").getString("BoardSizeText"));
        ObservableList<String> dimensionList = FXCollections.observableList(new ArrayList<>());
        ComboBox<String> boardDropdown = new ComboBox(dimensionList);
        boardDropdown.setId("boardDropdown");
        boardOptions.getChildren().addAll(loadDimensionsLabel, boardDropdown);
        boardOptions.setAlignment(position);
        return boardOptions;
    }

    private Button creatStartButton(Pos position) {
        Button start = new Button(setupData.getJSONObject("Text").getJSONObject("ButtonText").getString("Start"));
//        String userPlayerID =
//        start.setOnAction(e -> {
//            String playerUserID =
//                }
//            if(fileField.getText() != null && !fileField.getText().trim().isEmpty()){
//                new GameSetupOptions(myStage, fileField.getText());
//            }else if(!savedFileOptions.getSelectionModel().isEmpty()){
//                new GameSetupOptions(myStage, savedFileOptions.getValue());
//            }}
//        );
        start.getStyleClass().add("gameButton");
        return start;
    }

}

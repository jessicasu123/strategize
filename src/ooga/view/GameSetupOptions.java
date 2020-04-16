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
import ooga.controller.Controller;
import ooga.model.engine.InvalidGameTypeException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
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
    public static final String PIECE_ICON_RESOURCES = DEFAULT_VIEW_RESOURCES + "images/pieces/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    public static final double BUTTON_FONT_FACTOR = 0.125;
    private Stage myStage;
    private JSONObject gameFileData;
    private String gameFileName;
    private JSONObject setupData;
    private String userPlayerID;
    private String opponent;


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
        FileReader br = new FileReader(DEFAULT_RESOURCES + gameFileName);
        JSONTokener token = new JSONTokener(br);
        gameFileData = new JSONObject(token);

        FileReader br2 = new FileReader(DATAFILE);
        JSONTokener token2 = new JSONTokener(br2);
        setupData = new JSONObject(token2);

        displayToStage();
    }

    public void displayToStage(){
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
        JSONObject labelText = setupData.getJSONObject("Text").getJSONObject("LabelText");
        HBox opponentOptions = createOpponentOptions(labelText);
        HBox playerOptions = createPlayerOptions(opponentOptions.getAlignment(), labelText);
        HBox boardOptions = createBoardOptions(playerOptions.getAlignment(), labelText);
        Button start = creatStartButton();
        gameOptions.getChildren().addAll(opponentOptions, playerOptions, boardOptions, start);
        gameOptions.setAlignment(Pos.CENTER);
        // TODO for future feature: potentially add winStatus choices here (Eg: connect5?)
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
        RadioButton vsComputer = getOpponentButton(labelText, group, "VsComputer");
        RadioButton vsPlayer = getOpponentButton(labelText, group, "VsPlayer");
        vsComputer.setSelected(true);
        HBox opponentOptions = new HBox(SPACING, selectionPrompt, vsComputer, vsPlayer);
        opponentOptions.setAlignment(Pos.CENTER);
        return opponentOptions;
    }

    private RadioButton getOpponentButton(JSONObject labelText, ToggleGroup group, String vs) {
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
        Image playerImage = new Image(PIECE_ICON_RESOURCES + gameFileData.getJSONObject(player).getString("Image"), iconSize, iconSize, true, true);
        RadioButton playerButton = new RadioButton(player);
        playerButton.setGraphic(new ImageView(playerImage));
        playerButton.setToggleGroup(group);
        playerButton.setId(player);
        return playerButton;
    }

    private HBox createBoardOptions(Pos position, JSONObject labelText) {
        // TODO for future feature: add defaults permissible values for board dimensions from JSON
        HBox boardOptions = new HBox(SPACING);
        Text loadDimensionsLabel = new Text(labelText.getString("BoardSizeText"));
        loadDimensionsLabel.setId("SelectBoardText");
        ObservableList<String> dimensionList = FXCollections.observableList(new ArrayList<>());
        ComboBox<String> boardDropdown = new ComboBox(dimensionList);
        boardDropdown.setPromptText(labelText.getString("BoardDropdown"));
        boardDropdown.setId("boardDropdown");
        boardOptions.getChildren().addAll(loadDimensionsLabel, boardDropdown);
        boardOptions.setAlignment(position);
        return boardOptions;
    }

    private Button creatStartButton() {
        Button start = new Button(setupData.getJSONObject("Text").getJSONObject("ButtonText").getString("Start"));
//        TODO: @Brian uncomment once GameView is up
        start.setOnAction(e -> {
                    try {
                        Controller c = new Controller(gameFileName, userPlayerID, opponent);

                        new GameView(myStage, c);
                    } catch (IOException | org.json.simple.parser.ParseException | InvalidGameTypeException ex) {
                        //TODO: figure out what to do with this exception
                        System.out.println(ex.getMessage());
                    }
                }
        );
        start.getStyleClass().add("gameButton");
        start.setId("Start");
        return start;
    }

}

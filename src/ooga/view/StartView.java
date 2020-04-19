package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.view.components.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class creates the GUI for the start up screen
 * The GUI contains buttons for all of the games, a textfield
 * to enter your own game, and a dropdown to select a saved game
 * Upon submission of the form (either clicking a game button or
 * the submit button) a GameView is created, starting the Game
 * @author Holly Ansel
 */
public class StartView {
    public static final int PADDING = 20;
    public static final int SPACING = 10;
    public static final String DATAFILE = "src/resources/GameCenterView.json";
    public static final String GAME_ICON_RESOURCES = "resources/images/games/";
    public static final String GAME_FILES = "src/resources/gameFiles/";
    public static final double BUTTON_FONT_FACTOR = 0.125;
    public static final String FILE_TYPE = ".json";
    public static final int NUM_SIDES = 2;
    private Stage myStage;
    private JSONObject startScreenData;

    /**
     * Creates the StartView object and finds the JSON datafile
     * @param displayStage - the stage that the screen will be displayed on
     */
    public StartView(Stage displayStage){
        try {
            myStage = displayStage;
            FileReader br = new FileReader(DATAFILE);
            JSONTokener token = new JSONTokener(br);
            startScreenData = new JSONObject(token);
        } catch (FileNotFoundException e) {
            new ErrorAlerts(e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    /**
     * Calls on this class to present its GUI to the screen
     * @param width - the width of the screen to display
     * @param height - the height of the screen to display
     */
    public void displayToStage(int width, int height){
        Scene startScene = makeStartDisplay(width, height);
        myStage.setScene(startScene);
        myStage.show();
    }

    private Scene makeStartDisplay(int width, int height){
        VBox gameOptions = createGameOptionHolder(width, height);
        String title = startScreenData.getJSONObject("Text").getJSONObject("LabelText").getString("Title");
        return new GameScene().createScene(SPACING,width,height,gameOptions,title);
    }

    private VBox createLoadFileOptions(){
        VBox fileSelections = new VBox(SPACING);
        GameTextFieldContainer fileField = new GameTextFieldContainer();
        GameDropDown savedFileOptions = new GameDropDown();

        addFileFieldAndDropDown(fileSelections, fileField, savedFileOptions);
        addSubmitButton(fileSelections, fileField, savedFileOptions);
        return fileSelections;
    }

    private void addSubmitButton(VBox fileSelections, GameTextFieldContainer fileField, GameDropDown savedFileOptions) {
        String buttonName = startScreenData.getJSONObject("Text").getJSONObject("ButtonText").getString("Submit");
        Button submit = new GameButton().createGameButton(buttonName);
        submit.setOnAction(e -> {
            try {
                if (fileField.isNotEmpty()) {
                    new GameSetupOptions(myStage, fileField.getText());
                } else if (savedFileOptions.isNotEmpty()) {
                    new GameSetupOptions(myStage, savedFileOptions.getValue());
                }
            } catch (FileNotFoundException ex) {
                new ErrorAlerts(startScreenData.getJSONArray("AlertInfo"));
            }
        });
        fileSelections.getChildren().add(submit);
    }

    private void addFileFieldAndDropDown(VBox fileSelections, GameTextFieldContainer fileField, GameDropDown savedFileOptions) {
        String label = startScreenData.getJSONObject("Text").getJSONObject("LabelText").getString("LoadGame");
        String prompt = startScreenData.getJSONObject("Text").getJSONObject("FieldText").getString("LoadGame");
        HBox loadFile = fileField.createTextFieldContainer(label, prompt);

        String dropDownLabel = startScreenData.getJSONObject("Text").getJSONObject("LabelText").getString("LoadSavedGame");
        String dropDownPrompt = startScreenData.getJSONObject("Text").getJSONObject("FieldText").getString("LoadSavedGame");
        HBox loadSavedFile = savedFileOptions.createDropDownContainer(loadFile.getAlignment(), getFileNamesForDropDown(),dropDownPrompt, dropDownLabel);

        fileSelections.getChildren().addAll(loadFile,loadSavedFile);
    }

    private List<String> getFileNamesForDropDown(){
        List<String> fileNames = new ArrayList<>();
        File folder = new File(GAME_FILES);
        for (File f : Objects.requireNonNull(folder.listFiles())) {
            String name = f.getName();
            if(name.substring(name.length() - FILE_TYPE.length()).equals(FILE_TYPE)){
                fileNames.add(name);
            }
        }
        return fileNames;
    }

    private VBox createGameOptionHolder(int width, int height){
        VBox container = new VBox();
        GridPane defaultOptions = defaultGameOptionHolder(width, height);
        VBox fileOptions = createLoadFileOptions();
        fileOptions.setAlignment(Pos.CENTER);
        container.getChildren().addAll(defaultOptions, fileOptions);
        return container;
    }

    private GridPane defaultGameOptionHolder(int width, int height){
        GridPane gameOptionHolder = new GridPane();
        gameOptionHolder.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        gameOptionHolder.setHgap(PADDING);
        gameOptionHolder.setVgap(SPACING);

        JSONArray gameArray = startScreenData.getJSONArray("Games");
        int numCols = (int) Math.round(Math.sqrt(gameArray.length()));
        double containerSize = calculateCellSize(width, height, numCols, gameArray.length());

        createGameMenuCells(gameOptionHolder, gameArray, numCols, containerSize);
        gameOptionHolder.setAlignment(Pos.TOP_CENTER);
        return gameOptionHolder;
    }

    private void createGameMenuCells(GridPane gameOptionHolder, JSONArray gameArray, int numCols, double containerSize) {
        int rowCounter = 0;
        for(int i = 0; i < gameArray.length(); i++ ){
            HBox currentGame = createGameContainer(gameArray.getJSONObject(i), numCols, containerSize);
            gameOptionHolder.add(currentGame, i % numCols, rowCounter);
            if((i + 1) % numCols == 0){
                rowCounter++;
            }
        }
    }

    private double calculateCellSize(int width, int height, int numCols, int numCells){
        int widthMinusPadding = width - (numCols * PADDING) - (PADDING * NUM_SIDES);
        int heightMinusPadding = height - (numCells/numCols * PADDING) - (PADDING * NUM_SIDES);
        return Math.min(widthMinusPadding, heightMinusPadding);
    }
    private HBox createGameContainer(JSONObject game, int cols, double containerSize){
        HBox gameContainer = new HBox(SPACING);
        ImageView gameIcon = setUpGameIcon(game, cols, containerSize);
        VBox gameText = setUpGameText(game, cols, containerSize);
        gameContainer.getChildren().addAll(gameIcon, gameText);
        gameText.setAlignment(Pos.TOP_LEFT);
        return gameContainer;
    }

    private VBox setUpGameText(JSONObject game, int cols, double size) {
        VBox gameText = new VBox(SPACING);
        double sizeConstraint = size / (cols * NUM_SIDES);
        Button gameButton = createGameNameButton(game, sizeConstraint);
        createGameDescription(game, gameText, sizeConstraint, gameButton);
        return gameText;
    }

    private void createGameDescription(JSONObject game, VBox gameText, double sizeConstraint, Button gameButton) {
        Text gameDescription= new Text(game.getString("GameDescription"));
        gameDescription.getStyleClass().add("gameDescription");
        gameDescription.setWrappingWidth(sizeConstraint);
        gameText.getChildren().addAll(gameButton, gameDescription);
    }

    private Button createGameNameButton(JSONObject game, double sizeConstraint) {
        Button gameButton = new GameButton().createGameButton(game.getString("GameLabel"), sizeConstraint);
        gameButton.setStyle(String.format("-fx-font-size: %dpx;", (int)(BUTTON_FONT_FACTOR * sizeConstraint)));
        gameButton.setOnAction(e -> {
            try {
                new GameSetupOptions(myStage, game.getString("DefaultFile"));
            } catch (FileNotFoundException ex) {
                new ErrorAlerts(startScreenData.getJSONArray("AlertInfo"));
            }
        });
        return gameButton;
    }

    private ImageView setUpGameIcon(JSONObject game, int cols, double size) {
        Image img = new Image(GAME_ICON_RESOURCES + game.getString("GameIcon"));
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(size / (cols * NUM_SIDES));
        gameIcon.setPreserveRatio(true);
        return gameIcon;
    }

}

package ooga.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
    public static final String STYLESHEET = "resources/style.css";
    public static final String GAME_FILE_PATH = "gameFiles/";
    public static final String GAME_FILES = "src/resources/gameFiles/";
    public static final double BUTTON_FONT_FACTOR = 0.125;
    public static final String FILE_TYPE = ".json";
    private Stage myStage;
    private JSONObject startScreenData;


    /**
     * Creates the StartView object and finds the JSON datafile
     * @param displayStage - the stage that the screen will be displayed on
     * @throws FileNotFoundException - if the JSON file can't be found
     */
    public StartView(Stage displayStage) throws FileNotFoundException {
        myStage = displayStage;
        FileReader br = new FileReader(DATAFILE);
        JSONTokener token = new JSONTokener(br);
        startScreenData = new JSONObject(token);
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
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(SPACING, 0, SPACING,0));
        Scene startScene = new Scene(root, width, height);
        startScene.getStylesheets().add(STYLESHEET);
        root.getStyleClass().add("root");

        root.setTop(addTitle());
        root.setCenter(createGameOptionHolder(width, height));
        root.setMaxWidth(width);
        return startScene;
    }

    private Text addTitle(){
        String titleText = startScreenData.getJSONObject("Text").getJSONObject("LabelText").getString("Title");
        Text title = new Text(titleText.toUpperCase());
        title.getStyleClass().add("title");
        BorderPane.setAlignment(title, Pos.CENTER);
        return title;
    }

    private VBox createLoadFileOptions(){
        VBox fileSelections = new VBox(SPACING);

        TextField fileField = new TextField();
        HBox loadFile = initializeTextField(fileField);

        ComboBox<String> savedFileOptions = new ComboBox<>();
        HBox loadSavedFile = initializeDropDown(loadFile.getAlignment(), savedFileOptions);

        fileSelections.getChildren().addAll(loadFile,loadSavedFile);

        Button submit = new Button(startScreenData.getJSONObject("Text").getJSONObject("ButtonText").getString("Submit"));
        submit.setOnAction(e -> {
            try {
                if (fileField.getText() != null && !fileField.getText().trim().isEmpty()) {
                    new GameSetupOptions(myStage, GAME_FILE_PATH +  fileField.getText());
                } else if (!savedFileOptions.getSelectionModel().isEmpty()) {
                    new GameSetupOptions(myStage, GAME_FILE_PATH + savedFileOptions.getValue());
                }
            } catch (FileNotFoundException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not find selected file!");
                alert.setContentText("Please enter or select another JSON file.");
                alert.showAndWait();
            }
        });
        submit.getStyleClass().add("gameButton");
        submit.setId(submit.getText());
        fileSelections.getChildren().add(submit);
        return fileSelections;
    }

    private HBox initializeDropDown(Pos position, ComboBox<String> fileOptions) {
        HBox loadSavedFile = new HBox(SPACING);
        Text loadSavedFileLabel = new Text(startScreenData.getJSONObject("Text").getJSONObject("LabelText").getString("LoadSavedGame"));
        ObservableList<String> obList = FXCollections.observableList(getFileNamesForDropDown());
        fileOptions.setItems(obList);
        fileOptions.setId("fileOptions");
        fileOptions.setPromptText(startScreenData.getJSONObject("Text").getJSONObject("FieldText").getString("LoadSavedGame"));
        loadSavedFile.getChildren().addAll(loadSavedFileLabel, fileOptions);
        loadSavedFile.setAlignment(position);
        return loadSavedFile;
    }

    private List<String> getFileNamesForDropDown(){
        List<String> fileNames = new ArrayList<>();
        File folder = new File(GAME_FILES);
        for (File f : folder.listFiles()) {
            String name = f.getName();
            if(name.substring(name.length() - FILE_TYPE.length()).equals(FILE_TYPE)){
                fileNames.add(name);
            }
        }
        return fileNames;
    }

    private HBox initializeTextField(TextField fileField) {
        HBox loadFile = new HBox(SPACING);
        Text loadFileLabel = new Text(startScreenData.getJSONObject("Text").getJSONObject("LabelText").getString("LoadGame"));
        fileField.setPromptText(startScreenData.getJSONObject("Text").getJSONObject("FieldText").getString("LoadGame"));
        fileField.setId("fileField");
        loadFile.getChildren().addAll(loadFileLabel, fileField);
        loadFile.setAlignment(Pos.CENTER);
        return loadFile;
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
        int widthMinusPadding = width - (numCols * PADDING) - (PADDING * 2);
        int heightMinusPadding = height - (numCells/numCols * PADDING) - (PADDING * 2);
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
        double sizeConstraint = size / (cols * 2);
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
        Button gameButton = new Button(game.getString("GameLabel"));
        gameButton.setId(gameButton.getText());
        gameButton.getStyleClass().add("gameButton");
        gameButton.setStyle(String.format("-fx-font-size: %dpx;", (int)(BUTTON_FONT_FACTOR * sizeConstraint)));
        gameButton.setId(gameButton.getText());
        gameButton.setOnAction(e -> {
            try {
                new GameSetupOptions(myStage, GAME_FILE_PATH + game.getString("DefaultFile"));
            } catch (FileNotFoundException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not find default file for selected game!");
                alert.setContentText("Please select another game or enter JSON file name below.");
                alert.showAndWait();
            }
        });
        return gameButton;
    }

    private ImageView setUpGameIcon(JSONObject game, int cols, double size) {
        Image img = new Image(GAME_ICON_RESOURCES + game.getString("GameIcon"));
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(size / (cols * 2));
        gameIcon.setPreserveRatio(true);
        return gameIcon;
    }
}

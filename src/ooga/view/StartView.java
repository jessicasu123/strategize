package ooga.view;

import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

import java.io.*;
import java.util.ArrayList;


public class StartView {
    public static final int PADDING = 20;
    public static final int SPACING = 10;
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String DATAFILE = DEFAULT_RESOURCES+ "GameCenterView.json";
    public static final String ICON_RESOURCES = DEFAULT_VIEW_RESOURCES + "icons/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    private Timeline myAnimation;
    private Stage myStage;
    private JSONObject startScreenData;


    public StartView(Stage displayStage, Timeline animation) throws FileNotFoundException {
        myStage = displayStage;
        myAnimation = animation;

        FileReader br = new FileReader(DATAFILE);
        JSONTokener token = new JSONTokener(br);
        startScreenData = new JSONObject(token);
    }

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
        root.setCenter(createGameOptionHolder(width));
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

        HBox loadFile = new HBox(SPACING);
        Text loadFileLabel = new Text(startScreenData.getJSONObject("Text").getJSONObject("LabelText").getString("LoadGame"));
        TextField fileField = new TextField();
        fileField.setPromptText(startScreenData.getJSONObject("Text").getJSONObject("FieldText").getString("LoadGame"));
        loadFile.getChildren().addAll(loadFileLabel, fileField);
        loadFile.setAlignment(Pos.CENTER);

        HBox loadSavedFile = new HBox(SPACING);
        Text loadSavedFileLabel = new Text(startScreenData.getJSONObject("Text").getJSONObject("LabelText").getString("LoadSavedGame"));
        //TODO: get list of all the files saved
        ObservableList<String> obList = FXCollections.observableList(new ArrayList<>());
        ComboBox<String> fileOptions = new ComboBox<>(obList);
        fileOptions.setPromptText(startScreenData.getJSONObject("Text").getJSONObject("FieldText").getString("LoadSavedGame"));
        loadSavedFile.getChildren().addAll(loadSavedFileLabel, fileOptions);
        loadSavedFile.setAlignment(loadFile.getAlignment());
        fileSelections.getChildren().addAll(loadFile,loadSavedFile);

        Button submit = new Button(startScreenData.getJSONObject("Text").getJSONObject("ButtonText").getString("Submit"));
        submit.getStyleClass().add("gameButton");
        fileSelections.getChildren().add(submit);
        return fileSelections;
    }

    private VBox createGameOptionHolder(int width){
        VBox container = new VBox();
        GridPane defaultOptions = defaultGameOptionHolder(width);
        VBox fileOptions = createLoadFileOptions();
        fileOptions.setAlignment(Pos.CENTER);
        container.getChildren().addAll(defaultOptions, fileOptions);
        return container;

    }

    private GridPane defaultGameOptionHolder(int width){
        GridPane gameOptionHolder = new GridPane();
        gameOptionHolder.setPadding(new Insets(PADDING,0,PADDING,0));
        gameOptionHolder.setHgap(PADDING);
        gameOptionHolder.setVgap(SPACING);
        JSONArray gameArray = startScreenData.getJSONArray("Games");
        double gridLayoutProportion = Math.sqrt(gameArray.length());
        int numRows = (int) Math.ceil(gridLayoutProportion);
        int numCols = (int) Math.floor(gridLayoutProportion);
        int widthMinusPadding = width - (numCols * PADDING) - (PADDING * 2);
        for(int i = 0; i < gameArray.length(); i++ ){
            HBox currentGame = createGameContainer(gameArray.getJSONObject(i),  numCols, widthMinusPadding);
            gameOptionHolder.add(currentGame, i % numCols, i % numRows );
        }
        gameOptionHolder.setAlignment(Pos.TOP_CENTER);
        return gameOptionHolder;
    }

    private HBox createGameContainer(JSONObject game, int cols, int width){
        HBox gameContainer = new HBox(SPACING);

        ImageView gameIcon = setUpGameIcon(game, cols, width);

        VBox gameText = setUpGameText(game, cols, width);

        gameContainer.getChildren().addAll(gameIcon, gameText);
        gameText.setAlignment(Pos.TOP_LEFT);
        return gameContainer;
    }

    private VBox setUpGameText(JSONObject game, int cols, float width) {
        VBox gameText = new VBox(SPACING);
        Button gameButton = new Button(game.getString("GameLabel"));
        gameButton.setId(gameButton.getText());
        gameButton.getStyleClass().add("gameButton");
        Text gameDescription= new Text(game.getString("GameDescription"));
        gameDescription.getStyleClass().add("gameDescription");
        gameDescription.setWrappingWidth(width / (cols * 2));
        gameText.getChildren().addAll(gameButton, gameDescription);
        return gameText;
    }

    private ImageView setUpGameIcon(JSONObject game, int cols, float width) {
        Image img = new Image(ICON_RESOURCES + game.getString("GameIcon"));
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(width / (cols * 2));
        gameIcon.setPreserveRatio(true);
        return gameIcon;
    }
}

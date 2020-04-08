package ooga.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class creates the GUI for the game screen once a game is selected
 * The GUI contains navigation buttons common across all games to
 * play a move, return to main menu, restart the game, and save progress.
 * The status bar reflects the wins/losses and game pieces of each players.
 * The popup bar on top allows users to access customizations, messaging and
 * game instructions.
 * Upon clicking the settings icon, a CustomizationView is created.
 * Upon clicking save, a SaveView is created.
 * @author Sanya Kochhar, Brian Li
 */

public class GameView {
    public static final int PADDING = 20;
    public static final int SPACING = 50;
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String DATAFILE = DEFAULT_RESOURCES+ "GameView.json";
    public static final String ICON_RESOURCES = DEFAULT_VIEW_RESOURCES + "icons/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    public static final double BUTTON_FONT_FACTOR = 0.125;
    private Color Black = Color.BLACK;
    private GridPane pane;
    private Stage myStage;
    private JSONObject gameScreenData;
    private Map<String, String> properties;
    private List<List<Integer>> config;
    private BorderPane root;
    public static final int MINWIDTH = 100;

    FileHandler FH = new JSONFileReader();

    /**
     * Creates the GameView object and finds the JSON datafile
     * @param displayStage - the stage that the screen will be displayed on
     * @throws FileNotFoundException - if the JSON file can't be found
     */
    public GameView(Stage displayStage) throws FileNotFoundException {
        myStage = displayStage;         // TODO: this probably shouldn't be accessible here
        FileReader br = new FileReader(DATAFILE);
        JSONTokener token = new JSONTokener(br);
        gameScreenData = new JSONObject(token);
    }

    /**
     * Calls on this class to present its GUI to the screen
     * @param width - the width of the screen to display
     * @param height - the height of the screen to display
     */
    public void displayToStage(int width, int height){
        Scene gameScene = makeGameDisplay(width, height);
        myStage.setScene(gameScene);
        myStage.show();
    }

    private Scene makeGameDisplay(int width, int height){

        root = new BorderPane();
        root.setPadding(new Insets(SPACING, 0, SPACING,0));
        root.setBottom(createNavigationBar(20,20));
        root.setTop(createTopButtons());
        makeGrid(3);
        Scene startScene = new Scene(root, width, height);
        startScene.getStylesheets().add(STYLESHEET);
        root.getStyleClass().add("root");
        root.setMaxWidth(width);
        return startScene;

    }

    private void makeGrid(int numCells){
        pane = new GridPane();
        double cellWidth;
        double cellHeight;
        cellHeight = pane.getWidth() / numCells;
        cellWidth = cellHeight;
        createCells(numCells, numCells, cellWidth, cellHeight);
        root.getChildren().add(pane);
    }

    private void createCells(int numCellsHeight, int numCellsWidth, double cellWidth, double cellHeight) {
        for (int x = 0; x < numCellsWidth; x++) {
            for (int y = 0; y < numCellsHeight; y++) {
                Rectangle rect = new Rectangle();
                rect.setFill(Color.BLUE);
                rect.setWidth(cellWidth);
                rect.setHeight(cellHeight);
                pane.add(rect, x, y);
            }
        }
    }

    private VBox createNavigationBar(int width, int height){
        VBox ButtonContainer = new VBox(PADDING);
        HBox container = new HBox(SPACING);
        HBox container2 = new HBox(SPACING);

        container.setAlignment(Pos.CENTER);
        container2.setAlignment(Pos.CENTER);

        JSONObject buttonTexts = gameScreenData.getJSONObject("Buttons").getJSONObject("NavigationButtons");
        Button menu = createButton(buttonTexts, "Menu", e -> {
            //                myStage.setScene(new StartView());
        });
        Button restart = createButton(buttonTexts, "Restart", e -> {
            //                myStage.setScene(new StartView());
        });
        Button save = createButton(buttonTexts, "Save", e -> FH.saveToFile("file",properties,config));
        container.getChildren().addAll(menu, restart, save);
        JSONObject buttonTexts2 = gameScreenData.getJSONObject("Buttons").getJSONObject("MakeMoveButton");
        Button makemove = createButton(buttonTexts2,"ButtonText", e-> MakeMove());
        makemove.setMinWidth(400);
        container2.getChildren().add(makemove);
        ButtonContainer.getChildren().addAll(container2,container);
        return ButtonContainer;
    }


    private HBox createStatusBar(){
        HBox container = new HBox();
        JSONObject buttonTexts = gameScreenData.getJSONObject("StatusBar");

        return container;
    }

    private HBox createTopButtons(){
        HBox container = new HBox();

        return container;
    }

    private Button createButton(JSONObject buttonTexts, String key,EventHandler<ActionEvent> handler) {
        Button button = new Button(buttonTexts.getString(key));
        button.setId(button.getText());
        button.getStyleClass().add("gameButton");
        button.setMinWidth(MINWIDTH);
//        button.setStyle(String.format("-fx-font-size: %dpx;", (int)(BUTTON_FONT_FACTOR * sizeConstraint)));
        // TODO: uncomment once actions for restart, save have been set up
//        button.setOnAction(handler);
        return button;
    }

    private void MakeMove(){

    }

}

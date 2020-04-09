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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
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
    public static final int PanePadding = 2;
    public static final int PaneHeight = 350;
    public static final int SPACING = 50;
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String DATAFILE = DEFAULT_RESOURCES+ "GameView.json";
    public static final String ICON_RESOURCES = DEFAULT_VIEW_RESOURCES + "navicons/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    public static final double BUTTON_FONT_FACTOR = 0.125;
    public static final Color Black = Color.BLACK;
    public static final int DIMENSION = 3; //TODO: read from JSON file
    public static final int gamepiecewidth = 115;
    private GridPane pane;
    private Stage myStage;
    private JSONObject gameScreenData;
    private Map<String, String> properties;
    private List<List<Integer>> config;
    private BorderPane root;
    public static final int MINWIDTH = 100;
    private double cellWidth;
    private double cellHeight;
    private Controller myController;
    public static int WIDTH = 600;
    public static int HEIGHT = 700;

    private int lastSquareSelectedX;
    private int lastSquareSelectedY;
    List<List<Shape>> allBoardCells;

    //TODO: get userID and agentID from controller


    /**
     * Creates the GameView object and finds the JSON datafile
     * @param displayStage - the stage that the screen will be displayed on
     * @throws FileNotFoundException - if the JSON file can't be found
     */
    public GameView(Stage displayStage, Controller c) throws FileNotFoundException {
        myStage = displayStage;
        FileReader br = new FileReader(DATAFILE);
        JSONTokener token = new JSONTokener(br);
        gameScreenData = new JSONObject(token);
        myController = c;
        allBoardCells = new ArrayList<>();
        displayToStage();
    }

    /**
     * Calls on this class to present its GUI to the screen
     */
    public void displayToStage(){
        Scene gameScene = makeGameDisplay(WIDTH,HEIGHT);
        myStage.setScene(gameScene);
        myStage.show();
    }

    /**
     * creates the display by adding to root borderpane
     * @param width - the width of the screen to display
     * @param height - the height of the screen to display
     * @return startScene
     */
    private Scene makeGameDisplay(int width, int height){

        root = new BorderPane();

        root.setPadding(new Insets(SPACING, 0, SPACING,0));
        root.setTop(createViewTop());
        root.setBottom(createNavigationBar());

        Scene startScene = new Scene(root, width, height);
        startScene.getStylesheets().add(STYLESHEET);
        root.getStyleClass().add("root");
        root.setMaxWidth(width);
        return startScene;

    }

    /**
     * creates the grid given a specific board dimension
     * @param dimension - Board dimension
     * @return Gridpane of board
     */
    private GridPane makeGrid(int dimension){
        pane = new GridPane();
        pane.setPadding(new Insets(PanePadding,PanePadding,PanePadding,PanePadding));
        pane.setHgap(1);
        pane.setVgap(1);
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
        cellHeight = PaneHeight / dimension;
        cellWidth = cellHeight;
        createCells(dimension, cellWidth, cellHeight);
        pane.setAlignment(Pos.TOP_CENTER);
        return pane;
    }

    /**
     * creates the square cells and adds to Gridpane
     * @param dimension - Board dimension
     * @param cellHeight - height of cell
     * @param cellWidth - width of cell
     */
    private void createCells(int dimension, double cellWidth, double cellHeight) {
        for (int x = 0; x < dimension; x++) {
            List<Shape> boardRow = new ArrayList<>();
            for (int y = 0; y < dimension; y++) {
                Rectangle rect = new Rectangle();
                rect.setFill(Color.WHITE);
                rect.setStroke(Black);
                rect.setWidth(cellWidth);
                rect.setHeight(cellHeight);
                int finalX = x;
                int finalY = y;
                Image Ximage = new Image("/resources/images/pieces/X.png");
                rect.setOnMouseClicked(e -> processUserClickOnSquare(rect, Ximage,finalX,finalY));
                boardRow.add(rect);
                pane.add(rect, y, x);
            }
            allBoardCells.add(boardRow);
        }

    }

    private void processUserClickOnSquare(Shape rect, Image img,int finalX, int finalY) {
        //TODO: only allow user to click one square, somehow do validation
        lastSquareSelectedX = finalX;
        lastSquareSelectedY = finalY;
        updateImageOnSquare(rect, img, finalX, finalY);

    }

    private void updateImageOnSquare(Shape rect, Image img,int finalX, int finalY) {
        rect.setFill(new ImagePattern(img,finalX,finalY,gamepiecewidth,gamepiecewidth,false));

    }

    /**
     * creates a navigation bar of buttons and board
     * @return VBox object containing 4 buttons and board
     */
    private VBox createNavigationBar(){
        VBox GridContainer = new VBox(PADDING);
        HBox navcontainer = new HBox(SPACING);
        HBox movecontainer = new HBox(SPACING);
        HBox panecontainer = new HBox(SPACING);

        navcontainer.setAlignment(Pos.CENTER);
        movecontainer.setAlignment(Pos.CENTER);

        JSONObject buttonTexts = gameScreenData.getJSONObject("Buttons").getJSONObject("NavigationButtons");
        Button menu = createButton(buttonTexts, "Menu", e -> {});
        Button restart = createButton(buttonTexts, "Restart", e -> {});
        Button save = createButton(buttonTexts, "Save", e -> {});//FH.saveToFile("file",properties,config));
        JSONObject buttonTexts2 = gameScreenData.getJSONObject("Buttons").getJSONObject("MakeMoveButton");
        Button makemove = createButton(buttonTexts2,"ButtonText", e-> makeMove());
        makemove.setMinWidth(400);

        navcontainer.getChildren().addAll(menu, restart, save);
        movecontainer.getChildren().add(makemove);
        panecontainer.getChildren().add(makeGrid(DIMENSION));
        panecontainer.setAlignment(Pos.CENTER);

        GridContainer.getChildren().addAll(panecontainer,movecontainer,navcontainer);
        return GridContainer;
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
     * @return HBox object containing status bar
     */
    private HBox createStatusBar(){
        HBox container = new HBox(SPACING-20);
        container.setAlignment(Pos.TOP_CENTER);
        JSONObject buttonTexts = gameScreenData.getJSONObject("StatusBar");
        ImageView player1icon = setUpGameIcon("X.png");
        ImageView opponenticon = setUpGameIcon("O.png");
        TextField playerscore = new TextField();
        playerscore.setMaxWidth(50);
        playerscore.setMinHeight(30);
        TextField opponentscore = new TextField();
        opponentscore.setMaxWidth(50);
        opponentscore.setMinHeight(30);
        Label player = new Label(buttonTexts.getString("Player"));
        player.setMinHeight(30);
        Label opponent = new Label(buttonTexts.getString("Opponent"));
        opponent.setMinHeight(30);
        container.getChildren().addAll(player1icon,player,playerscore,opponenticon,opponent,opponentscore);
        return container;
    }

    /**
     * @return VBox object containing top buttons and status bar
     */
    private VBox createViewTop(){
        VBox ViewTop = new VBox(SPACING);
        ViewTop.getChildren().addAll(createTopButtons(), createStatusBar());
        return ViewTop;
    }

    /**
     * @param buttonTexts - JSON object containing button text mappings
     * @param key - key object to get button text value
     * @param handler - action handler
     * @return Button with desired properties
     */
    private Button createButton(JSONObject buttonTexts, String key,EventHandler<ActionEvent> handler) {
        Button button = new Button(buttonTexts.getString(key));
        button.setId(button.getText());
        button.getStyleClass().add("gameButton");
        button.setMinWidth(MINWIDTH);
//        button.setStyle(String.format("-fx-font-size: %dpx;", (int)(BUTTON_FONT_FACTOR * sizeConstraint)));
        // TODO: uncomment once actions for restart, save have been set up
        button.setOnAction(handler);
        return button;
    }

    /**
     * @param game - JSON object containing icons
     * @param key - key object to get icon value
     * @return Button with desired properties
     */
    private Button setUpGameIconButton(JSONObject game, String key) {
        Image img = new Image(ICON_RESOURCES + game.getString(key));
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(30);
        gameIcon.setPreserveRatio(true);
        Button button = new Button();
        button.getStyleClass().add("gameButton");
        button.setGraphic(gameIcon);
        return button;
    }


    /**
     * @param key - key object to get icon value
     * @return Button with desired properties
     */
    private ImageView setUpGameIcon(String key) {
        Image img = new Image(ICON_RESOURCES + (key));
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(30);
        gameIcon.setPreserveRatio(true);
        return gameIcon;
    }

    private void updateBoardAppearance() {
        //TODO: update to be reading from data file
        List<List<Integer>> gameStates = myController.getGameVisualInfo();
        for (int r = 0; r < DIMENSION; r++) {
            for (int c = 0; c < DIMENSION; c++) {
                Shape currSquare = allBoardCells.get(r).get(c);
                if (gameStates.get(r).get(c)==2) { //TODO: read from data file
                    Image OImage = new Image("/resources/images/pieces/O.png");
                    updateImageOnSquare(currSquare, OImage, r,c);
                }
            }
        }
    }

    private void makeMove(){
        myController.squareSelected(lastSquareSelectedX, lastSquareSelectedY);
        myController.playMove(1); //TODO: read from controller
        myController.haveAgentMove(2);
        updateBoardAppearance();
    }

}

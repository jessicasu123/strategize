package ooga.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import ooga.controller.Controller;
import javafx.scene.shape.Shape;
import ooga.model.engine.Coordinate;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
 * @author Brian Li
 */

public class GameView {

    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DEFAULT_VIEW_RESOURCES = "resources/";
    public static final String DATAFILE = DEFAULT_RESOURCES+ "GameView.json";
    public static final String PIECES_RESOURCES = DEFAULT_VIEW_RESOURCES + "images/pieces/";
    public static final String STYLESHEET = DEFAULT_VIEW_RESOURCES + "style.css";
    public static final Color Black = Color.BLACK;
    public static final int PANE_HEIGHT = 350;
    public static final int START_DIM = 500;
    public static final int SPACING = 40;
    public static int WIDTH = 600;
    public static int HEIGHT = 700;
    public static int SaveWIDTH = 300;
    public static int SaveHEIGHT = 300;

    private Stage myStage;
    private JSONObject gameScreenData;
    private Controller myController;
    private int lastSquareSelectedX;
    private int lastSquareSelectedY;
    private int lastPieceSelectedX;
    private int lastPieceSelectedY;
    private boolean didSelectPiece;
    private boolean hasSelectedSquare;
    private boolean gameInProgress;
    List<List<Shape>> allBoardCells;
    List<List<Integer>> myGameStates;
    private int boardRows;
    private int boardCols;
    private int userID;
    private int agentID;
    private String userImage;
    private String agentImage;
    private String boardColor;
    private double gamePieceWidth;
    private double gamePieceHeight;
    private boolean piecesMove;
    private String possibleMoveImage;
    private BoardView grid;
    private NavigationPanel navPanel;
    private StatusPanel statusPanel;
    private CustomizationPopUp customizePopUp;
    private SavePopUp save;
    private RulesPopUp rules;
    private List<String> rulelist;
    /**
     * Creates the GameView object and finds the JSON datafile
     * @param displayStage - the stage that the screen will be displayed of
     * @throws FileNotFoundException - if the JSON file can't be found
     */
    public GameView(Stage displayStage, Controller c) throws FileNotFoundException {
        myStage = displayStage;
        initializeJSONReader();
        myController = c;
        myGameStates = myController.getGameVisualInfo();
        boardColor = "white";
        gameInProgress = true;
        didSelectPiece = false;
        getGameDisplayInfo();
        initializeSubPanels();
        initializePopUps();
        displayToStage();
    }

    private void initializeJSONReader() throws FileNotFoundException {
        FileReader br = new FileReader(DATAFILE);
        JSONTokener token = new JSONTokener(br);
        gameScreenData = new JSONObject(token);
    }

    private void initializeSubPanels() {
        statusPanel = new StatusPanel(gameScreenData);
        grid = new BoardView(PANE_HEIGHT, PANE_HEIGHT, boardRows, boardCols);
        navPanel = new NavigationPanel(WIDTH, gameScreenData);
    }

    private void initializePopUps() throws FileNotFoundException {
        customizePopUp = new CustomizationPopUp(myStage, WIDTH,HEIGHT, userImage,
                agentImage, boardColor);
        save = new SavePopUp(myStage,SaveWIDTH,SaveHEIGHT);
        rules = new RulesPopUp(myStage, WIDTH, HEIGHT, "tic-tac-toe.json");
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
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(SPACING, 0, SPACING,0));

        root.setTop(statusPanel.createStatusPanel(userImage,agentImage));
        setCenter(root);
        setBottom(root);

        Scene startScene = new Scene(root, width, height);
        startScene.getStylesheets().add(STYLESHEET);
        root.getStyleClass().add("root");
        root.setMaxWidth(width);
        return startScene;
    }

    private void setCenter(BorderPane root) {
        root.setCenter(grid.getGridContainer());
        allBoardCells = grid.getBoardCells();
        updateBoardAppearance();
    }

    private void setBottom(BorderPane root) {
        root.setBottom(navPanel.createNavigationBar());
        Map<Button,String> navAndStatusButtonActions = navPanel.getButtonActionsMap();
        navAndStatusButtonActions.putAll(statusPanel.getButtonActions());
        addActionsToButtons(navAndStatusButtonActions);
    }

    private void getGameDisplayInfo() {
        userID = myController.getUserNumber();
        agentID = myController.getAgentNumber();
        possibleMoveImage = "";
        try {
            boardRows = Integer.parseInt(myController.getStartingProperties().get("Height"));
            boardCols = Integer.parseInt(myController.getStartingProperties().get("Width"));
            userImage = myController.getUserImage(); //myController.getStartingProperties().get("Image" + Integer.toString(userID));
            agentImage = myController.getAgentImage();//myController.getStartingProperties().get("Image" + Integer.toString(agentID));
            piecesMove = myController.doPiecesMove();
            possibleMoveImage = myController.getStartingProperties().get("possibleMove");

        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Using reflection to add action handlers to the buttons created in NavigationPanel.
     * The methods associated with these buttons are private methods in this class,
     * and they are specified in the GameView.json file.
     */
    private void addActionsToButtons(Map<Button, String> buttonActionsMap) {
        for (Button b: buttonActionsMap.keySet()) {
            reflectMethodOnButton(b, buttonActionsMap.get(b));
        }
    }

    private void reflectMethodOnButton(Button b, String methodName) {
        b.setOnAction(handler -> {
            try {
                Method buttonAction = this.getClass().getDeclaredMethod(methodName, new Class[0]);
                buttonAction.invoke(GameView.this, new Object[0]);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private void restart() throws IOException, ParseException {
        GameSetupOptions gso = new GameSetupOptions(myStage, myController.getGameFileName());
        gso.displayToStage();
        System.out.println("RESTART");
    }

    //TODO: popup to save current config
    private void save() {
        save.display();
        addActionsToButtons(save.getButtonActionsMap());
        System.out.println("SAVE");
    }

    private void saveConfig() throws IOException, ParseException {
        save.close();
        myController.saveANewFile(save.getFileName(), myController.getStartingProperties());
        System.out.println("SAVE CONFIG");
    }

    private void backToMenu() throws FileNotFoundException {
        StartView sv = new StartView(myStage);
        sv.displayToStage(START_DIM,START_DIM);
        System.out.println("BACK");
    }

    private void showRules() {
        rules.display();
//        addActionsToButtons(rules.getButtonActionsMap());
        System.out.println("SHOW RULES");
    }

    private void showSocialCenter() {System.out.println("SOCIAL CENTER");}

    private void customize() {
        customizePopUp.display();
        addActionsToButtons(customizePopUp.getButtonActionsMap());
    }

    private void setCustomizationPreferences() {
        customizePopUp.close();
        userImage = customizePopUp.getUserImageChoice();
        agentImage = customizePopUp.getOpponentImageChoice();
        boardColor = customizePopUp.getBoardColorChoice();
        updateBoardAppearance();
        statusPanel.updatePlayerIcons(userImage, agentImage);
    }

    private void processUserClickOnSquare(Shape rect, Image img,int finalX, int finalY) {
        if(hasSelectedSquare){
            allBoardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).setFill(Color.valueOf(boardColor));
        }
        hasSelectedSquare = true;
        lastSquareSelectedX = finalX;
        lastSquareSelectedY = finalY;

        if(didSelectPiece && piecesMove){
            allBoardCells.get(lastPieceSelectedX).get(lastPieceSelectedY).setFill(Color.valueOf(boardColor));
        }
        updateImageOnSquare(rect, img);

    }

    private void updateImageOnSquare(Shape rect, Image img) {
        rect.setFill(new ImagePattern(img));
    }

    private void updateBoardAppearance() {
        myGameStates = myController.getGameVisualInfo();
        for (int r = 0; r < boardRows; r++) {
            for (int c = 0; c < boardCols; c++) {
                Shape currSquare = allBoardCells.get(r).get(c);
                updateCellAppearance(currSquare, r, c);
            }
        }
    }



    private void updateCellAppearance(Shape currSquare, int r, int c) {
        currSquare.setFill(Color.valueOf(boardColor));
        currSquare.setStroke(Black);
        if (myController.getPossibleMovesForView().get(r).get(c)==1 && !possibleMoveImage.equals("")) {
            Image possibleMove = new Image(PIECES_RESOURCES + possibleMoveImage);
            updateImageOnSquare(currSquare, possibleMove);
        }
        if (myGameStates.get(r).get(c) == userID) {
            Image player1Image = new Image(PIECES_RESOURCES + userImage);
            updatePlayerCell(player1Image, currSquare, r, c);
        }
        else if (myGameStates.get(r).get(c)==agentID) {
            Image player2Image = new Image(PIECES_RESOURCES + agentImage);
            updatePlayerCell(player2Image, currSquare, r, c);
        } else {
            updateEmptyCell(currSquare, r,c);
        }
    }

    private void updatePlayerCell(Image playerImage, Shape currSquare, int r, int c) {
        updateImageOnSquare(currSquare, playerImage);
        currSquare.setOnMouseClicked(e -> handlePieceSelected(r,c));
    }

    private void updateEmptyCell(Shape currSquare, int r, int c) {
        Image playerImg = new Image(PIECES_RESOURCES + userImage);
        EventHandler<MouseEvent> userClick = e -> { processUserClickOnSquare(currSquare,playerImg,r,c); };
        currSquare.setOnMouseClicked(userClick);
        //TODO: fix
        currSquare.removeEventHandler(MouseEvent.MOUSE_CLICKED, userClick);//can't click on square with player already
    }

    private void handlePieceSelected(int r, int c) {
        if(piecesMove){
            didSelectPiece = true;
            lastPieceSelectedX = r;
            lastPieceSelectedY = c;
        }
    }

    private void makeMove(){
        if(gameInProgress) {
            if (didSelectPiece) {
                myController.pieceSelected(lastPieceSelectedX, lastPieceSelectedY);
            }
            myController.squareSelected(lastSquareSelectedX, lastSquareSelectedY);
            myController.playMove();
            checkGameOver();
            if(gameInProgress){
                myController.haveAgentMove();
            }
            updateBoardAppearance();
            checkGameOver();
            hasSelectedSquare = false;
            didSelectPiece = false;
        }
    }

    private void checkGameOver() {
        if (myController.isGameOver()) {
            gameInProgress = false;
            endGame(myController.gameWinner());
        }
    }

    private void endGame(int winner){
        String endMessage;
        if(winner == myController.getUserNumber()){
            endMessage = "You Won!";
        }else if(winner == 2){
            endMessage = "You Lost:(";
        }else{
            endMessage = "It was a tie";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setContentText(endMessage);
        alert.showAndWait();
    }

}

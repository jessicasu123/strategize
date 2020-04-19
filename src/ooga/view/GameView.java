package ooga.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.components.ErrorAlerts;
import ooga.view.components.GameScene;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    public static final int STATE_ID_POS = 0;
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DATAFILE = DEFAULT_RESOURCES + "GameView.json";
    public static final String CUSTOMIZATION_FILE = "CustomizationView.json";
    public static final String ENDGAME_FILE = "EndView.json";
    public static final String FILE_PATH = "gameFiles/";
    public static final String STYLESHEET = "resources/style.css";
    public static final int PANE_HEIGHT = 350;
    public static final int START_DIM = 500;
    public static final int SPACING = 40;
    public static int WIDTH = 600;
    public static int HEIGHT = 700;

    private Stage myStage;
    private JSONObject gameScreenData;
    private Controller myController;
    private boolean gameInProgress;
    private BoardView grid;
    private NavigationPanel navPanel;
    private StatusPanel statusPanel;
    private CustomizationPopUp customizePopUp;
    private EndPopUp gameEnd;
    private SavePopUp save;
    private RulesPopUp rules;
    private int userWinCount;
    private int opponentWinCount;
    private int myUserID;
    private GameButtonManager gameButtonManager;

    /**
     * Creates the GameView object and finds the JSON datafile
     * @param displayStage - the stage that the screen will be displayed of
     * @throws FileNotFoundException - if the JSON file can't be found
     */
    public GameView(Stage displayStage, Controller c) throws FileNotFoundException {
        myStage = displayStage;
        initializeJSONReader();
        myController = c;
        gameInProgress = true;
        myUserID = myController.getUserStateInfo().get(STATE_ID_POS);
        int myAgentID = myController.getAgentStateInfo().get(STATE_ID_POS);
        String userImage = myController.getStateImageMapping().get(myUserID);
        String agentImage = myController.getStateImageMapping().get(myAgentID);
        gameButtonManager = new GameButtonManager();
        initializeComponents(userImage, agentImage);
        displayToStage(userImage, agentImage);
        grid.makeAgentMove();
    }

    private void initializeComponents(String userImage, String agentImage){
        initializeSubPanels();
        String boardColor = "white";
        try {
            initializePopUps(userImage, agentImage, boardColor);
        } catch (FileNotFoundException e) {
            new ErrorAlerts(gameScreenData.getJSONArray("AlertInfo"));
        }
    }

    private void initializeJSONReader() throws FileNotFoundException {
        FileReader br = new FileReader(DATAFILE);
        JSONTokener token = new JSONTokener(br);
        gameScreenData = new JSONObject(token);
    }

    private void initializeSubPanels() {
        statusPanel = new StatusPanel(gameButtonManager, gameScreenData);
        initializeBoardView();
        navPanel = new NavigationPanel(gameButtonManager, WIDTH, gameScreenData);
    }

    private void initializeBoardView()  {
        try {
            int boardRows = Integer.parseInt(myController.getStartingProperties().get("Height"));
            int boardCols = Integer.parseInt(myController.getStartingProperties().get("Width"));
            grid = new BoardView(PANE_HEIGHT, PANE_HEIGHT, boardRows, boardCols, myController);
        } catch (IOException | ParseException e) {
            new ErrorAlerts(gameScreenData.getJSONArray("AlertInfo"));
        }
    }

    private void initializePopUps(String userImage, String agentImage, String boardColor) throws FileNotFoundException {
        customizePopUp = new CustomizationPopUp(myStage, WIDTH,HEIGHT, CUSTOMIZATION_FILE,
                userImage, agentImage, boardColor, gameButtonManager);
        save = new SavePopUp(myStage,WIDTH,HEIGHT, "", gameButtonManager);
        rules = new RulesPopUp(myStage, WIDTH, HEIGHT, FILE_PATH + myController.getGameFileName(), gameButtonManager);
    }

    /**
     * Calls on this class to present its GUI to the screen
     */
    public void displayToStage(String userImage, String agentImage){
        Scene gameScene = makeGameDisplay(WIDTH,HEIGHT, userImage, agentImage);
        myStage.setScene(gameScene);
        myStage.show();
    }

    /**
     * creates the display by adding to root borderpane
     * @param width - the width of the screen to display
     * @param height - the height of the screen to display
     * @return startScene
     */
    private Scene makeGameDisplay(int width, int height, String userImage,String agentImage){
        VBox gameDisplayElements = viewElements(userImage,agentImage);
        grid.updateBoardAppearance();
        addActionsToButtons(gameButtonManager.getButtonActionsMap());
        return new GameScene().createScene(SPACING,width,height,gameDisplayElements);
    }

    private VBox viewElements(String userImage,String agentImage ){
        VBox elements = new VBox(SPACING);
        elements.getChildren().addAll(statusPanel.createStatusPanel(userImage,agentImage),
                grid.getGridContainer(),navPanel.createNavigationBar());
        return elements;
    }

    /**
     * Using reflection to add action handlers to the buttons created in NavigationPanel.
     * The methods associated with these buttons are private methods in this class,
     * and they are specified in the GameView.json file.
     */
    private void addActionsToButtons(Map<Button, String> buttonActionsMap) {
        for (Button b: buttonActionsMap.keySet()) {
            if (gameButtonManager.needsToAddActionToButton(b)) {
                reflectMethodOnButton(b, buttonActionsMap.get(b));
                gameButtonManager.hasHandledButton(b);
            }
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
        if (gameEnd != null) {
            gameEnd.close();
        }
        gameInProgress = true;
        myController.restartGame();
        grid.updateBoardAppearance();
    }

    private void backToSetup() throws IOException, ParseException {
        if (gameEnd != null) {
            gameEnd.close();
        }
        GameSetupOptions gso = new GameSetupOptions(myStage, myController.getGameFileName());
        gso.displayToStage();
    }

    private void save() {
        save.display();
        addActionsToButtons(gameButtonManager.getButtonActionsMap());
    }

    private void saveConfig() throws IOException, ParseException {
        save.close();
        myController.saveANewFile(save.getFileName(), myController.getStartingProperties());
    }


    private void backToMenu() throws FileNotFoundException {
        if (gameEnd != null) {
            gameEnd.close();
        }
        StartView sv = new StartView(myStage);
        sv.displayToStage(START_DIM,START_DIM);
    }

    private void showRules() { rules.display(); }

    private void showSocialCenter() {System.out.println("SOCIAL CENTER");}

    private void customize() {
        customizePopUp.display();
        addActionsToButtons(gameButtonManager.getButtonActionsMap());
    }

    private void setCustomizationPreferences() {
        customizePopUp.close();
        String userImage = customizePopUp.getUserImageChoice();
        String agentImage = customizePopUp.getOpponentImageChoice();
        String boardColor = customizePopUp.getBoardColorChoice();
        grid.updateVisuals(userImage, agentImage, boardColor);
        statusPanel.updatePlayerIcons(userImage, agentImage);
    }

    private void makeMove(){
        if(gameInProgress && myController.userTurn()) {
            grid.makeUserMove();
            checkGameStatus();
            if(gameInProgress){
                grid.makeAgentMove();
            }
            checkGameStatus();
        }
    }

    private void checkGameStatus(){
        checkGameOver();
        checkPass();
    }
    //TODO: figure out what to do with a pass. also figure out if we want to determine which player passed.
    private void checkPass() {
        if (gameInProgress && myController.playerPass()) {
            System.out.println("PASS");
        }
    }

    private void checkGameOver() {
        if (myController.isGameOver()) {
            gameInProgress = false;
            endGame(myController.gameWinner());
        }
    }

    private void endGame(int winner){
        String endStatus;
        if (winner == myUserID) {
            endStatus = "Win";
            userWinCount += 1;
        } else if(winner == 3) {
            endStatus = "Tie";
        } else {
            endStatus = "Loss";
            opponentWinCount += 1;
        }
        statusPanel.updateWinnerCounts(userWinCount, opponentWinCount);
        try {
            gameEnd = new EndPopUp(myStage, WIDTH, HEIGHT, ENDGAME_FILE, endStatus, gameButtonManager);
            gameEnd.display();
            addActionsToButtons(gameButtonManager.getButtonActionsMap());
        } catch (FileNotFoundException e) {
            new ErrorAlerts(gameScreenData.getJSONArray("AlertInfo"));
        }
    }
}

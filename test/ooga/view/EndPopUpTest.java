package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.engine.exceptions.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EndPopUpTest extends DukeApplicationTest {

    private Controller testController;
    private EndPopUp testEndPopUp;
    private GameButtonManager gameButtonManager;
    private Text endStatusText;
    private Text endStatusSubtext;
    private Button playAgain;
    private Button backToSetup;
    private Button backToMenu;

    public EndPopUpTest() throws InvalidNeighborhoodException, InvalidConvertibleNeighborFinderException, InvalidMoveCheckException, InvalidWinTypeException, InvalidEvaluationFunctionException {
        testController = new Controller("tic-tac-toe.json", "Player1", "3 x 3");
        gameButtonManager = new GameButtonManager();
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        GameView testGameView = new GameView(stage, testController);
        testEndPopUp = new EndPopUp(stage, 600, 700, "EndView.json","Win", gameButtonManager);
        testEndPopUp.display();

        endStatusText = lookup("#WinText").query();
        endStatusSubtext = lookup("#WinSubtext").query();
        playAgain = lookup("#PlayAgain").query();
        backToSetup = lookup("#ReturntoSetup").query();
        backToMenu = lookup("#ReturntoMainMenu").query();
    }

    @Test
    void testWinStatusMessaging() {
        assertEquals("CONGRATULATIONS", endStatusText.getText());
        assertEquals("You won!", endStatusSubtext.getText());
    }

    @Test
    void testButtonContents() {
        assertEquals("Play Again", playAgain.getText());
        assertEquals("Return to Setup", backToSetup.getText());
        assertEquals("Return to Main Menu", backToMenu.getText());
    }

}
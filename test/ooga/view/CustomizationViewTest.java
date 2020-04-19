package ooga.view;

import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.engine.InvalidGameTypeException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class CustomizationViewTest extends DukeApplicationTest {

    private ComboBox<String> userChoice;
    private ComboBox<String> opponentChoice;
    private ComboBox<String> colorChoice;
    private Controller testController;
    private CustomizationPopUp customizationPopUp;
    private GameButtonManager gameButtonManager;

    public CustomizationViewTest() throws IOException, ParseException, InvalidGameTypeException {
        testController = new Controller("tic-tac-toe.json", "Player1", "Computer");
        gameButtonManager = new GameButtonManager();
    }
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        GameView gameView = new GameView(stage, testController);
        customizationPopUp = new CustomizationPopUp(stage, 600,700, "CustomizationView.json",
                "X.png", "O.png", "white", gameButtonManager);
        customizationPopUp.display();

        userChoice = lookup("#PlayerImage").queryComboBox();
        opponentChoice = lookup("#OpponentImage").queryComboBox();
        colorChoice = lookup("#ChooseColor").queryComboBox();

    }

    @Test
    void testChooseUserImage() {
        select(userChoice, "fire");
        assertEquals("fire.png", customizationPopUp.getUserImageChoice());
    }

    @Test
    void testChooseOpponentImage() {
        select(opponentChoice, "water");
        assertEquals("water.png", customizationPopUp.getOpponentImageChoice());
    }

    @Test
    void testChooseColor() {
        select(colorChoice, "red");
        assertEquals("red", customizationPopUp.getBoardColorChoice());
    }

}
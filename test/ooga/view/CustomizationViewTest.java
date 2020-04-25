package ooga.view;

import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.engine.exceptions.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomizationViewTest extends DukeApplicationTest {

    private ComboBox<String> userChoice;
    private ComboBox<String> opponentChoice;
    private ComboBox<String> colorChoice;
    private Controller testController;
    private CustomizationPopUp customizationPopUp;
    private GameButtonManager gameButtonManager;

    public CustomizationViewTest() throws InvalidNeighborhoodException, InvalidConvertibleNeighborFinderException, InvalidMoveCheckException, InvalidWinTypeException, InvalidEvaluationFunctionException {
        testController = new Controller("tic-tac-toe.json", "Player1", "3 x 3");
        gameButtonManager = new GameButtonManager();
    }
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        GameView gameView = new GameView(stage, testController);
        customizationPopUp = new CustomizationPopUp(stage, 600,700, "CustomizationView.json",
                "X.png", "O.png", "white", gameButtonManager);
        customizationPopUp.display();

        userChoice = lookup("#ChoosePlayer").queryComboBox();
        opponentChoice = lookup("#ChooseOpponent").queryComboBox();
        colorChoice = lookup("#Color").queryComboBox();

    }

    @Test
    void testChooseUserImage() {
        select(userChoice, "fire");
        assertEquals(List.of("fire.png"), customizationPopUp.getUserImageChoice());
    }

    @Test
    void testChooseOpponentImage() {
        select(opponentChoice, "water");
        assertEquals(List.of("water.png"), customizationPopUp.getOpponentImageChoice());
    }

    @Test
    void testChooseColor() {
        select(colorChoice, "red");
        assertEquals("red", customizationPopUp.getBoardColorChoice());
    }

}

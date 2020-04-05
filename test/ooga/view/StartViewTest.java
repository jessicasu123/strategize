package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StartViewTest extends DukeApplicationTest {

    private StartView startScreen;
    private TextField textBox;
    private ComboBox<String> fileOptions;
    private Button submit;
    private Button ticTacToe;
    private Button mancala;
    private Button othello;
    private Button checkers;
    private Button chopsticks;
    private Button connect4;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        startScreen = new StartView(stage);
        startScreen.displayToStage(500,500);

        submit = lookup("#Submit").query();
        textBox = lookup("#fileField").query();
        fileOptions = lookup("#fileOptions").query();
        ticTacToe = lookup("#Tic-Tac-Toe").query();
        mancala = lookup("#Mancala").query();
        othello = lookup("#Othello").query();
        checkers = lookup("#Checkers").query();
        chopsticks = lookup("#Chopsticks").query();
        connect4 = lookup("#Connect4").query();

    }

    @Test
    void testdisplayToStage() {
        assertEquals("Submit", submit.getText());
        assertEquals("Enter JSON file name", textBox.getPromptText());
        assertEquals("Select a saved file", fileOptions.getPromptText());
        assertEquals("Tic-Tac-Toe", ticTacToe.getText());
        assertEquals("Mancala", mancala.getText());
        assertEquals("Othello", othello.getText());
        assertEquals("Checkers", checkers.getText());
        assertEquals("Connect4", connect4.getText());
        assertEquals("Chopsticks", chopsticks.getText());
    }
}
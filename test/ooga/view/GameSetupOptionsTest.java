package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GameSetupOptionsTest extends DukeApplicationTest {
    private Text playerText;
    private RadioButton player1;
    private RadioButton player2;
    private ComboBox<String> boardDropdown;
    private Button start;
    private Button menu;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        GameSetupOptions startScreen = new GameSetupOptions(stage, "tic-tac-toe.json");
        playerText = lookup("#SelectPlayerText").query();
        player1 = lookup("#Player1").query();
        player2 = lookup("#Player2").query();
        start = lookup("#Start").query();
        menu = lookup("#BacktoMenu").query();
    }

    @Test
    void testDisplayToStage() {
        assertEquals("Select Your Player:", playerText.getText());
        assertEquals("Player1", player1.getText());
        assertEquals("Player2", player2.getText());
        assertEquals("Start", start.getText());
        assertEquals("Back to Menu", menu.getText());
    }
}
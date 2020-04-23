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

    private Text opponentText;
    private RadioButton vsComputer;
    private RadioButton vsPlayer;
    private Text playerText;
    private RadioButton player1;
    private RadioButton player2;
    private Text boardText;
    private ComboBox<String> boardDropdown;
    private Button start;
    private Button menu;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        GameSetupOptions startScreen = new GameSetupOptions(stage, "tic-tac-toe.json");

        opponentText = lookup("#SelectOpponentText").query();
        vsComputer = lookup("#VsComputer").query();
        vsPlayer = lookup("#VsPlayer").query();
        playerText = lookup("#SelectPlayerText").query();
        player1 = lookup("#Player1").query();
        player2 = lookup("#Player2").query();
//        boardDropdown = lookup("#Default:3x3").query();
        start = lookup("#Start").query();
        menu = lookup("#BacktoMenu").query();
    }

    @Test
    void testDisplayToStage() {
        assertEquals("Select Opponent:", opponentText.getText());
        assertEquals("vs. Computer", vsComputer.getText());
        assertEquals("vs. Player", vsPlayer.getText());
        assertEquals("Select Your Player:", playerText.getText());
        assertEquals("Player1", player1.getText());
        assertEquals("Player2", player2.getText());
//        assertEquals("Default: 3 x 3", boardDropdown.getPromptText());
        assertEquals("Start", start.getText());
        assertEquals("Back to Menu", menu.getText());
    }
}
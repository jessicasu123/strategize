//package ooga.view;
//
//import javafx.scene.control.Button;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
//
//import java.io.FileNotFoundException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class EndPopUpTest extends DukeApplicationTest {
//
//    private Button playAgain;
//    private Button backtoSetup;
//    private Button backToMenu;
//
//    @Override
//    public void start(Stage stage) throws FileNotFoundException {
//        EndPopUp testPopUp = new EndPopUp(stage, 600, 700, "EndView.json","Win");
//        testPopUp.display();
//        playAgain = lookup("#PlayAgain").query();
//        backtoSetup = lookup("#ReturntoSetup").query();
//        backToMenu = lookup("#ReturntoMainMenu").query();
//    }
//
//
//    @Test
//    void testCreatePopUpContents() {
//        assertEquals("Play Again", playAgain.getText());
//        assertEquals("Return to Setup", backtoSetup.getText());
//        assertEquals("Back to Main Menu", backToMenu.getText());
//    }
//}
package ooga.view;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.engine.InvalidGameTypeException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.control.Button;
import java.util.*;
import java.util.List;

import javafx.scene.paint.Paint;

import javafx.scene.shape.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameViewTest extends DukeApplicationTest {


    private Controller testController;
    private Button backToMenu;
    private Button restart;
    private Button save;
    private Button makeMove;
    private int numRows;
    private int numCols;
    List<List<Rectangle>> myBoardCells;
    public GameViewTest() throws IOException, ParseException, InvalidGameTypeException {
        testController = new Controller("tic-tac-toe.json", "Player1", "Computer");
        myBoardCells = new ArrayList<>();
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        GameView gameView = new GameView(stage, testController);

        backToMenu = lookup("#BacktoMainMenu").query();
        restart = lookup("#Restart").query();
        save = lookup("#Save").query();
        makeMove = lookup("#MAKEMOVE").query();
    }

    @Test
    void testDisplayToStage() {
        assertEquals("Back to Main Menu", backToMenu.getText());
        assertEquals("Restart", restart.getText());
        assertEquals("Save", save.getText());
        assertEquals("MAKE MOVE", makeMove.getText());
    }

    @Test
    void testInitialGridAppearance() {
        updateBoardCells();
        //before a move, all the cells should be empty (fill should be white)
        for (int r = 0; r < numRows;r++) {
            for (int c = 0; c < numCols;c++) {
                assertEquals(Paint.valueOf("white"), myBoardCells.get(r).get(c).getFill());
            }
        }
    }

    private void updateBoardCells() {
        try {
            numRows = Integer.parseInt(testController.getStartingProperties().get("Height"));
            numCols = Integer.parseInt(testController.getStartingProperties().get("Width"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int r = 0; r < numRows; r++) {
            List<Rectangle> row = new ArrayList<>();
            for (int c = 0; c < numCols; c++) {
                Rectangle cell = lookup("#cell" + r + c).query();
                row.add(cell);
            }
            myBoardCells.add(row);
        }
    }

    @Test
    void testBoardUpdateAfterMakeMove() {
        updateBoardCells();
        //clicking on middle cell
        javafxRun(() -> clickOn(myBoardCells.get(0).get(0)));
        //making a move
        javafxRun(() -> clickOn(makeMove));
        updateBoardCells();
        //there should be an image (NOT WHITE) on the cell clicked
        assertEquals(ImagePattern.class, myBoardCells.get(0).get(0).getFill().getClass());

        //there should also be an image (NOT WHITE) on the cell where the agent moved
        List<Integer> agentCoords = findOpponentMove();
        int row = agentCoords.get(agentCoords.size()-2);
        int col = agentCoords.get(agentCoords.size()-1);
        sleep(300);
        assertEquals(ImagePattern.class,
                myBoardCells.get(row).get(col).getFill().getClass());
    }

    private List<Integer> findOpponentMove() {
        List<Integer> coords = new ArrayList<>();
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (testController.getGameVisualInfo().get(r).get(c)==2) {
                    coords.add(r);
                    coords.add(c);
                }
            }
        }
        return coords;
    }
}

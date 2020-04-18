package ooga.controller;

import ooga.model.engine.InvalidGameTypeException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



class ControllerTest {
    Controller testController;
    Controller testController2;

    ControllerTest() throws IOException, ParseException, InvalidGameTypeException {
        testController = new Controller("tic-tac-toe.json", "Player1", "Computer");
        testController2 = new Controller("tic-tac-toe-test.json", "Player2", "Computer");
    }

    public List<List<Integer>> createTestConfig(Integer[][] testConfig) {
        List<List<Integer>> config = new ArrayList<>();
        for (int i = 0; i < 3;i++) {
            config.add(Arrays.asList(testConfig[i]));
        }
        return config;
    }

    @Test
    void testSaveANewFile() {
        // TODO: add once saving file feature has been added
    }

    @Test
    void testGetStartingProperties() throws IOException, ParseException {
        Map<String, String> gameProperties = testController.getStartingProperties();
        assertEquals("Tic-Tac-Toe", gameProperties.get("Gametype"));
        assertEquals("", gameProperties.get("Neighborhood"));
        assertEquals("Black", gameProperties.get("Color1"));
        assertEquals("Black", gameProperties.get("Color2"));
    }

    @Test
    void testPieceSelected() {
        // TODO: add test once games like checkers with moving pieces have been added
    }

    @Test
    void testSquareSelected() {
        Integer[][] newConfig = {
                {0,0,0},
                {0,1,0},
                {0,0,0}
        };
        List<List<Integer>> expectedConfig = createTestConfig(newConfig);
        testController.squareSelected(1,1);
        testController.playMove();
        assertEquals(expectedConfig, testController.getGameVisualInfo());
    }

    @Test
    void testPlayMove() {
        List<List<Integer>> originalBoardConfig = testController.getGameVisualInfo();
        testController.squareSelected(1,1);
        testController.playMove();
        assertNotEquals(testController.getGameVisualInfo(), originalBoardConfig);
    }

    @Test
    void testHaveAgentMove() {
        List<List<Integer>> originalBoardConfig = testController.getGameVisualInfo();
        testController.squareSelected(1,1);
        testController.playMove();
        List<List<Integer>> afterUserTurnBoardConfig = testController.getGameVisualInfo();
        testController.playMove();
        assertNotEquals(testController.getGameVisualInfo(), afterUserTurnBoardConfig);
    }

    @Test
    void testGetGameVisualInfo() {
        Integer[][] newConfig = {
                {0,0,0},
                {0,0,0},
                {0,0,0}
        };
        List<List<Integer>> expectedConfig = createTestConfig(newConfig);
        assertEquals(expectedConfig, testController.getGameVisualInfo());
    }


    @Test
    void testIsGameOver() {
        assertFalse(testController.isGameOver());


        // TODO: add testcases for when the game has been won/has tied
    }

    @Test
    void testGameWinner() {
        assertEquals(0, testController.gameWinner());
        // TODO: add testcases for when the game has been won/has tied
    }
}
package ooga.controller;

import ooga.model.engine.exceptions.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



class ControllerTest {
    Controller testController;
    Controller testController2;

    ControllerTest() throws InvalidNeighborhoodException, InvalidConvertibleNeighborFinderException, InvalidMoveCheckException, InvalidWinTypeException, InvalidEvaluationFunctionException {
        testController = new Controller("tic-tac-toe.json", "Player1", "3 x 3");
        testController2 = new Controller("tic-tac-toe-test.json", "Player2", "3 x 3");
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
        testController.squareSelected(0,0);
        testController.playMove();
        assertFalse(testController.isGameOver());
        // TODO: add testcases for when the game has been won/has tied
    }

    @Test
    void testGameWinner() {
        testController.squareSelected(0,1);
        testController.playMove();
        assertEquals(0, testController.gameWinner());
        // TODO: add testcases for when the game has been won/has tied
    }
}
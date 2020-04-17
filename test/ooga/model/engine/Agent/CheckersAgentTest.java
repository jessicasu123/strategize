package ooga.model.engine.Agent;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckersAgentTest {
    private CheckersAgent myCheckersAgent = new CheckersAgent(1,3,2,4,0,1);
    private boolean noMovesLeft = false;
    @Test
    void testEvaluateCurrentGameStateTied() {
        //test tied state
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(0,myCheckersAgent.evaluateCurrentGameState(boardConfig,noMovesLeft));
    }

    @Test
    void testEvaluateCurrentGameStatePieceValue() {
        //test king value (same number of pieces) for max
        List<Integer> row1 = new ArrayList<>(List.of(0,0,2,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,0));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(40 + (-4 * 4),myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));

        //test pawn value (different number of pieces) for max
        row1 = new ArrayList<>(List.of(1,0,1,0));
        row2 = new ArrayList<>(List.of(0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,3,0,0));
        boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(60,myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));

        //test king value (same number of pieces) for min
        row1 = new ArrayList<>(List.of(0,0,1,0));
        row2 = new ArrayList<>(List.of(0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,4,0,0));
        boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(-40 + (-4 * -4),myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));

        //test pawn value (different number of pieces) for min
        row1 = new ArrayList<>(List.of(0,0,1,0));
        row2 = new ArrayList<>(List.of(0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,3,0,3));
        boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(-60,myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));
    }


    @Test
    void testEvaluateCurrentGameStatePositions() {
        //test better position for pawns for max
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(1,0,0,1));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(28, myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));

        //test better position for pawns for min
        row1 = new ArrayList<>(List.of(1,0,1,0));
        row2 = new ArrayList<>(List.of(0,3,0,3));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,0));
        boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(-28,myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));

        //test better position for kings for min
        row1 = new ArrayList<>(List.of(2,0,2,0));
        row2 = new ArrayList<>(List.of(0,4,0,4));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,0));
        boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(-28,myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));

        //test better position for kings for max
        row1 = new ArrayList<>(List.of(0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0));
        row3 = new ArrayList<>(List.of(2,0,2,0));
        row4 = new ArrayList<>(List.of(0,4,0,4));
        boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(28,myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));
    }

    @Test
    void testEvaluateCurrentGameStateDistances() {
        //test max king has lower distance
        List<Integer> row1 = new ArrayList<>(List.of(0,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,2,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,3,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,4,0,0));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(4 * (-1 * ((2 + 2) - (2 + 4))) ,myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));

        //test min king has lower distance
        row1 = new ArrayList<>(List.of(0,0,2,0));
        row2 = new ArrayList<>(List.of(0,1,0,0));
        row3 = new ArrayList<>(List.of(0,0,4,0));
        row4 = new ArrayList<>(List.of(0,3,0,0));
        boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(4 * (-1 * ((2 + 4) - (2 + 2))),myCheckersAgent.evaluateCurrentGameState(boardConfig, noMovesLeft));

    }

    @Test
    void testIsWin() {
        //not winning state
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertFalse(myCheckersAgent.isWin(1,boardConfig, noMovesLeft));
        assertFalse(myCheckersAgent.isWin(3,boardConfig, noMovesLeft));

        //winning state for player1
        row1 = new ArrayList<>(List.of(1,0,1,0));
        row2 = new ArrayList<>(List.of(0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,0));
        boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertTrue(myCheckersAgent.isWin(1,boardConfig, noMovesLeft));
        assertFalse(myCheckersAgent.isWin(3,boardConfig, noMovesLeft));


        //winning state for player2
        row1 = new ArrayList<>(List.of(0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,3,0,3));
        boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertTrue(myCheckersAgent.isWin(3,boardConfig, noMovesLeft));
        assertFalse(myCheckersAgent.isWin(1,boardConfig, noMovesLeft));
    }
}
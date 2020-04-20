package ooga.model.engine.Agent.oldAgent;

import ooga.model.engine.Agent.oldAgent.OthelloAgent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class OthelloAgentTest {
    OthelloAgent othelloAgent = new OthelloAgent(2,1);

    @Test
    void testMoreCornerPieces() {
        List<Integer> row1 = new ArrayList<>(List.of(2,0,0,0,0,0,0,2));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> row6 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> row7 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> row8 = new ArrayList<>(List.of(1,0,0,0,0,0,0,2));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6,row7,row8));
        //numCornerPieces more for 2 = 2
        //num pieces more for 2 = 2
        //weight for 2 - weight for 1 = 8
        //2 + 2 + 8 = 12
        assertEquals(12, othelloAgent.evaluateCurrentGameState(boardConfig, false));
    }


    @Test
    void testBetterPosWeights() {
        List<Integer> row1 = new ArrayList<>(List.of(2,1,2,2,2,2,1,2));
        List<Integer> row2 = new ArrayList<>(List.of(1,1,1,1,1,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,1,0,0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,1,0,2,0,0,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,0,1,0,2,2,2,0));
        List<Integer> row6 = new ArrayList<>(List.of(0,0,1,0,2,0,0,0));
        List<Integer> row7 = new ArrayList<>(List.of(0,0,1,1,1,1,0,0));
        List<Integer> row8 = new ArrayList<>(List.of(1,2,2,2,2,2,2,2));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6,row6,row8));
        //player 2 (max player) has better positions on the board - 3 corners, as well as the entire
        //stable border rows on the top and bottom
        assertEquals(46, othelloAgent.evaluateCurrentGameState(boardConfig, false));
    }

    @Test
    void testNoWin() {
        //test for no win
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(1,1,1,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,2,1,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(false, othelloAgent.isWin(2, boardConfig, false));
        assertEquals(false, othelloAgent.isWin(1, boardConfig, false));
    }
    @Test
    void testPlayer1Win() {
        //test for player 1 win
        List<Integer> row1 = new ArrayList<>(List.of(1,1,2,1));
        List<Integer> row2 = new ArrayList<>(List.of(1,1,1,1));
        List<Integer> row3 = new ArrayList<>(List.of(1,2,1,2));
        List<Integer> row4 = new ArrayList<>(List.of(2,2,1,1));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(false, othelloAgent.isWin(2, boardConfig, true));
        assertEquals(true, othelloAgent.isWin(1, boardConfig, true));
    }

    @Test
    void testPlayer2Win() {
        List<Integer> row1 = new ArrayList<>(List.of(2,2,2,1));
        List<Integer> row2 = new ArrayList<>(List.of(1,2,1,1));
        List<Integer> row3 = new ArrayList<>(List.of(1,2,1,2));
        List<Integer> row4 = new ArrayList<>(List.of(2,2,2,2));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertEquals(true, othelloAgent.isWin(2, boardConfig, true));
        assertEquals(false, othelloAgent.isWin(1, boardConfig, true));
    }

}

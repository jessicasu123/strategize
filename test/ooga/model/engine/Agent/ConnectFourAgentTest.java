package ooga.model.engine.Agent;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectFourAgentTest {
    private ConnectFourAgent myConnectAgent = new ConnectFourAgent(1,2,4);

    @Test
    void testEvaluateCurrentGameState() {
        //test 1
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,0,2,0,0,0,0));
        List<Integer> row6 = new ArrayList<>(List.of(0,0,1,1,0,0,0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        assertEquals(2, myConnectAgent.evaluateCurrentGameState(sampleConfig));

        //test 2
        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row5 = new ArrayList<>(List.of(0,0,0,2,0,0,0));
        row6 = new ArrayList<>(List.of(0,2,1,1,1,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        assertEquals(0, myConnectAgent.evaluateCurrentGameState(sampleConfig));

//        empty
        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row5 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row6 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        assertEquals(0, myConnectAgent.evaluateCurrentGameState(sampleConfig));

    }

    @Test
    void testisWinForMax() {
        //test horizontal
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,0,2,2,2,0,0));
        List<Integer> row6 = new ArrayList<>(List.of(0,0,1,1,1,1,0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        assertTrue( myConnectAgent.isWin(1,sampleConfig));

        //test vertical
        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,1,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        row5 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        row6 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        assertTrue( myConnectAgent.isWin(1,sampleConfig));


        //test diagonal
        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0,0,0,1));
        row4 = new ArrayList<>(List.of(0,0,0,0,2,1,2));
        row5 = new ArrayList<>(List.of(0,0,0,0,1,2,2));
        row6 = new ArrayList<>(List.of(0,0,0,1,2,1,1));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        assertTrue( myConnectAgent.isWin(1,sampleConfig));
    }

    @Test
    void testisWinForMin() {
        //test horizontal
        List<Integer> row1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0));
        List<Integer> row2 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0));
        List<Integer> row3 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0));
        List<Integer> row4 = new ArrayList<>(List.of(0, 0, 1, 1, 0, 0, 0));
        List<Integer> row5 = new ArrayList<>(List.of(0, 0, 2, 2, 2, 2, 0));
        List<Integer> row6 = new ArrayList<>(List.of(0, 0, 1, 1, 1, 2, 0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1, row2, row3, row4, row5, row6));
        assertTrue(myConnectAgent.isWin(2, sampleConfig));

        //test vertical
        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0,2,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        row5 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        row6 = new ArrayList<>(List.of(0,0,1,1,2,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        assertTrue( myConnectAgent.isWin(2,sampleConfig));

        //test diagonal
        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,2,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,1,2,1,2));
        row5 = new ArrayList<>(List.of(0,0,0,1,1,2,2));
        row6 = new ArrayList<>(List.of(0,0,1,1,2,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        assertTrue( myConnectAgent.isWin(2,sampleConfig));

    }

    @Test
    void testIsWinWHenNoWinner(){
        //full board no winnder
        List<Integer> row1 = new ArrayList<>(List.of(1, 2, 1, 2, 1, 2, 1));
        List<Integer> row2 = new ArrayList<>(List.of(1, 2, 1, 2, 1, 2, 1));
        List<Integer> row3 = new ArrayList<>(List.of(1, 2, 1, 2, 1, 2, 1));
        List<Integer> row4 = new ArrayList<>(List.of(2, 1, 2, 1, 2, 1, 2));
        List<Integer> row5 = new ArrayList<>(List.of(2, 1, 2, 1, 2, 1, 2));
        List<Integer> row6 = new ArrayList<>(List.of(2, 1, 2, 1, 2, 1, 2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1, row2, row3, row4, row5, row6));
        assertFalse(myConnectAgent.isWin(2, sampleConfig));
        assertFalse(myConnectAgent.isWin(2, sampleConfig));


        //empty no winnder
        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row5 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row6 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        assertFalse(myConnectAgent.isWin(2, sampleConfig));
        assertFalse(myConnectAgent.isWin(2, sampleConfig));

    }

}
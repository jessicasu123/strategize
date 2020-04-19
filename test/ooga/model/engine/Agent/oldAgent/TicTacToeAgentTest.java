package ooga.model.engine.Agent.oldAgent;

import ooga.model.engine.Agent.oldAgent.TicTacToeAgent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeAgentTest {
    private TicTacToeAgent myTicTacToeAgent = new TicTacToeAgent(1,2,3, 0);
    private boolean noMovesLeft = false;
    @Test
    void testEvaluateCurrentGameState() {
        //test 1
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,1,2));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertEquals(2, myTicTacToeAgent.evaluateCurrentGameState(sampleConfig, noMovesLeft));

        //test 2 (full board)
        row1 = new ArrayList<>(List.of(1,1,2));
        row2 = new ArrayList<>(List.of(2,2,1));
        row3 = new ArrayList<>(List.of(1,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertEquals(0, myTicTacToeAgent.evaluateCurrentGameState(sampleConfig, noMovesLeft));

        //test 3 (empty board)
        row1 = new ArrayList<>(List.of(0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertEquals(0, myTicTacToeAgent.evaluateCurrentGameState(sampleConfig, noMovesLeft));

        //test 4
        row1 = new ArrayList<>(List.of(1,1,0));
        row2 = new ArrayList<>(List.of(1,2,0));
        row3 = new ArrayList<>(List.of(2,1,1));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertEquals(1, myTicTacToeAgent.evaluateCurrentGameState(sampleConfig, noMovesLeft));
    }

    @Test
    void testIsWinForMax() {
        //test horizontal win
        List<Integer> row1 = new ArrayList<>(List.of(1,1,1));
        List<Integer> row2 = new ArrayList<>(List.of(0,2,0));
        List<Integer> row3 = new ArrayList<>(List.of(2,0,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isWin(1,sampleConfig, noMovesLeft));

        //test vertical win
        row1 = new ArrayList<>(List.of(2,1,2));
        row2 = new ArrayList<>(List.of(0,1,0));
        row3 = new ArrayList<>(List.of(2,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isWin(1,sampleConfig, noMovesLeft));

        //test diagonal1 win
        row1 = new ArrayList<>(List.of(1,2,2));
        row2 = new ArrayList<>(List.of(0,1,0));
        row3 = new ArrayList<>(List.of(2,2,1));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isWin(1,sampleConfig, noMovesLeft));

        //test diagonal2 win
        row1 = new ArrayList<>(List.of(2,0,1));
        row2 = new ArrayList<>(List.of(0,1,0));
        row3 = new ArrayList<>(List.of(1,2,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isWin(1,sampleConfig, noMovesLeft));
    }

    @Test
    void testIsWinForMin() {
        //test horizontal win
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1));
        List<Integer> row2 = new ArrayList<>(List.of(1,2,0));
        List<Integer> row3 = new ArrayList<>(List.of(2,2,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isWin(2,sampleConfig, noMovesLeft));

        //test vertical win
        row1 = new ArrayList<>(List.of(1,1,2));
        row2 = new ArrayList<>(List.of(0,0,2));
        row3 = new ArrayList<>(List.of(1,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isWin(2,sampleConfig, noMovesLeft));

        //test diagonal1 win
        row1 = new ArrayList<>(List.of(2,1,1));
        row2 = new ArrayList<>(List.of(0,2,0));
        row3 = new ArrayList<>(List.of(2,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isWin(2,sampleConfig, noMovesLeft));

        //test diagonal2 win
        row1 = new ArrayList<>(List.of(1,1,2));
        row2 = new ArrayList<>(List.of(0,2,0));
        row3 = new ArrayList<>(List.of(2,2,1));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isWin(2,sampleConfig, noMovesLeft));

    }

    @Test
    void testIsWinWhenNoWinner() {
        //full board no winner
        List<Integer> row1 = new ArrayList<>(List.of(1,1,2));
        List<Integer> row2 = new ArrayList<>(List.of(2,2,1));
        List<Integer> row3 = new ArrayList<>(List.of(1,1,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertFalse(myTicTacToeAgent.isWin(1,sampleConfig, noMovesLeft));
        assertFalse(myTicTacToeAgent.isWin(2,sampleConfig, noMovesLeft));

        //empty board no winner
        row1 = new ArrayList<>(List.of(0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertFalse(myTicTacToeAgent.isWin(1,sampleConfig, noMovesLeft));
        assertFalse(myTicTacToeAgent.isWin(2,sampleConfig, noMovesLeft));

        //incomplete board no winner
        row1 = new ArrayList<>(List.of(1,1,0));
        row2 = new ArrayList<>(List.of(0,2,0));
        row3 = new ArrayList<>(List.of(1,2,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertFalse(myTicTacToeAgent.isWin(1,sampleConfig, noMovesLeft));
        assertFalse(myTicTacToeAgent.isWin(2,sampleConfig, noMovesLeft));

    }
    @Test
    void isGameWonTrue() {
        //max wins
        //test horizontal win
        List<Integer> row1 = new ArrayList<>(List.of(1,1,1));
        List<Integer> row2 = new ArrayList<>(List.of(0,2,0));
        List<Integer> row3 = new ArrayList<>(List.of(2,0,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isGameWon(sampleConfig, noMovesLeft));
        //min wins
        row1 = new ArrayList<>(List.of(1,1,2));
        row2 = new ArrayList<>(List.of(0,0,2));
        row3 = new ArrayList<>(List.of(1,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isGameWon(sampleConfig, noMovesLeft));
    }

    @Test
    void isGameWonFalse() {
        //full board no winner
        List<Integer> row1 = new ArrayList<>(List.of(1,1,2));
        List<Integer> row2 = new ArrayList<>(List.of(2,2,1));
        List<Integer> row3 = new ArrayList<>(List.of(1,1,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertFalse(myTicTacToeAgent.isGameWon(sampleConfig, noMovesLeft));

        //empty board no winner
        row1 = new ArrayList<>(List.of(0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertFalse(myTicTacToeAgent.isGameWon(sampleConfig, noMovesLeft));

        //incomplete board no winner
        row1 = new ArrayList<>(List.of(1,1,0));
        row2 = new ArrayList<>(List.of(0,2,0));
        row3 = new ArrayList<>(List.of(1,2,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertFalse(myTicTacToeAgent.isGameWon(sampleConfig, noMovesLeft));

    }

    @Test
    void testFindGameWinnerNoWinner(){
        //full board no winner
        List<Integer> row1 = new ArrayList<>(List.of(1,1,2));
        List<Integer> row2 = new ArrayList<>(List.of(2,2,1));
        List<Integer> row3 = new ArrayList<>(List.of(1,1,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertEquals(0, myTicTacToeAgent.findGameWinner(sampleConfig, noMovesLeft));

        //empty board no winner
        row1 = new ArrayList<>(List.of(0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertEquals(0, myTicTacToeAgent.findGameWinner(sampleConfig, noMovesLeft));

        //incomplete board no winner
        row1 = new ArrayList<>(List.of(1,1,0));
        row2 = new ArrayList<>(List.of(0,2,0));
        row3 = new ArrayList<>(List.of(1,2,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertEquals(0, myTicTacToeAgent.findGameWinner(sampleConfig, noMovesLeft));
    }


    @Test
    void testFindGameWinnerPlayer1Wins(){
        //max wins
        //test horizontal win
        List<Integer> row1 = new ArrayList<>(List.of(1,1,1));
        List<Integer> row2 = new ArrayList<>(List.of(0,2,0));
        List<Integer> row3 = new ArrayList<>(List.of(2,0,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isGameWon(sampleConfig, noMovesLeft));
        assertEquals(1, myTicTacToeAgent.findGameWinner(sampleConfig, noMovesLeft));
    }


    @Test
    void testFindGameWinnerPlayer2Wins(){
        //min wins
        List<Integer> row1 = new ArrayList<>(List.of(1,1,2));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,2));
        List<Integer> row3 = new ArrayList<>(List.of(1,1,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        assertTrue(myTicTacToeAgent.isGameWon(sampleConfig, noMovesLeft));
        assertEquals(2, myTicTacToeAgent.findGameWinner(sampleConfig, noMovesLeft));
    }
}
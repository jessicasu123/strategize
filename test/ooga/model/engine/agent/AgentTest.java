package ooga.model.engine.agent;

import ooga.model.engine.Board;
import ooga.model.engine.BoardConfiguration;
import ooga.model.engine.agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.agent.evaluationFunctions.MorePieces;
import ooga.model.engine.agent.evaluationFunctions.PositionWeights;
import ooga.model.engine.agent.evaluationFunctions.SumOfDistances;
import ooga.model.engine.agent.winTypes.NoPiecesForOpponent;
import ooga.model.engine.agent.winTypes.WinType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AgentTest {
    WinType noPieces = new NoPiecesForOpponent(0);
    List<Integer> user = new ArrayList<>(List.of(3,4));
    List<Integer> agent = new ArrayList<>(List.of(1,2));
    List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
    List<Integer> row2 = new ArrayList<>(List.of(0,1,0,1));
    List<Integer> row3 = new ArrayList<>(List.of(3,0,3,0));
    List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
    BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
    List<Integer> weightrow1 = new ArrayList<>(List.of(-2,0,-2,0));
    List<Integer> weightrow2 = new ArrayList<>(List.of(0,-1,0,-1));
    List<Integer> weightrow3 = new ArrayList<>(List.of(1,0,1,0));
    List<Integer> weightrow4 = new ArrayList<>(List.of(0,2,0,2));
    BoardConfiguration weightboardConfig = new BoardConfiguration(new ArrayList<>(List.of(weightrow1,weightrow2,weightrow3,weightrow4)));
    EvaluationFunction morePieces = new MorePieces(0,agent, user,boardConfig,true);
    EvaluationFunction weights = new PositionWeights(weightboardConfig,agent,user,1,-1);
    EvaluationFunction sumOfDistances = new SumOfDistances(1,agent,user);
    List<EvaluationFunction> evals = new ArrayList<>(List.of(morePieces,weights,sumOfDistances));
    Agent checkersAgent = new Agent(noPieces,evals,agent,user);
    private boolean noMovesLeft = false;

    @Test
    void testEvaluateCurrentGameStateTied() {
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals(0,checkersAgent.evaluateCurrentGameState(boardConfig,boardConfig, noMovesLeft));
    }

    @Test
    void testEvaluateCurrentGameStatePieceDifferences() {
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,0));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));

        assertEquals(1 + (-2 + -2 - -2), checkersAgent.evaluateCurrentGameState(boardConfig,boardConfig, noMovesLeft));


        row1 = new ArrayList<>(List.of(0,0,1,0));
        row2 = new ArrayList<>(List.of(0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,3,0,3));
        boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals(-1 + (2 + 2 - 2),checkersAgent.evaluateCurrentGameState(boardConfig, boardConfig,noMovesLeft));

    }
    @Test
    void testEvaluateCurrentGameStatePositionDifferences(){
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals((1+1) - (-2 + -2), checkersAgent.evaluateCurrentGameState(boardConfig,boardConfig, noMovesLeft));

        //test better position for pawns for min
        row1 = new ArrayList<>(List.of(1,0,1,0));
        row2 = new ArrayList<>(List.of(0,3,0,3));
        row3 = new ArrayList<>(List.of(0,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,0));
        boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals((-2+-2) - (1 + 1), checkersAgent.evaluateCurrentGameState(boardConfig, boardConfig,noMovesLeft));
    }

    @Test
    void testEvaluateCurrentGameStateSumOfDistances(){
        //test max king has lower distance
        List<Integer> row1 = new ArrayList<>(List.of(0,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,2,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,3,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,4,0,0));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals((-1 * ((2 + 2) - (2 + 4))),checkersAgent.evaluateCurrentGameState(boardConfig,boardConfig, noMovesLeft));

        //test min king has lower distance
        row1 = new ArrayList<>(List.of(0,0,2,0));
        row2 = new ArrayList<>(List.of(0,1,0,0));
        row3 = new ArrayList<>(List.of(0,0,4,0));
        row4 = new ArrayList<>(List.of(0,3,0,0));
        boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals((-1 * ((2 + 4) - (2 + 2))),checkersAgent.evaluateCurrentGameState(boardConfig,boardConfig, noMovesLeft));

    }
    @Test
    void testIsGameWonNoWinner() {
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertFalse(checkersAgent.isGameWon(boardConfig,boardConfig,noMovesLeft));

    }

    @Test
    void testIsGameWonPlayer1Wins(){
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertTrue(checkersAgent.isGameWon(boardConfig,boardConfig,noMovesLeft));
    }

    @Test
    void testIsGameWonPlayer2Wins(){
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertTrue(checkersAgent.isGameWon(boardConfig,boardConfig,noMovesLeft));
    }

    @Test
    void testFindGameWinnerNoWinner() {
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals(0, checkersAgent.findGameWinner(boardConfig,boardConfig,noMovesLeft));
    }

    @Test
    void testFindGameWinnerPlayer1Wins() {
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals(1, checkersAgent.findGameWinner(boardConfig,boardConfig,noMovesLeft));
    }

    @Test
    void testFindGameWinnerPlayer2Wins() {
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertEquals(3, checkersAgent.findGameWinner(boardConfig,boardConfig,noMovesLeft));
    }
}
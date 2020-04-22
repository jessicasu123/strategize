package ooga.model.engine;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.Agent.evaluationFunctions.NumOpenLines;
import ooga.model.engine.Agent.winTypes.ConsecutivePieces;
import ooga.model.engine.Agent.winTypes.WinType;
import ooga.model.engine.pieces.GamePieceFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameTest {
    Integer[][] startingConfig = {
            {0,0,0},
            {0,1,0},
            {0,0,0}
    };

    Integer[][] player1Win = {
            {1,1,1},
            {0,2,1},
            {2,2,0}
    };

    Integer[][] player2Win = {
            {1,2,1},
            {0,2,1},
            {2,2,0}
    };

    Integer[][] noMovesConfig = {
            {1,2,1},
            {1,2,1},
            {2,1,2}
    };
    public List<List<Integer>> createTestConfig(Integer[][] testConfig) {
        List<List<Integer>> config = new ArrayList<>();
        for (int i = 0; i < 3;i++) {
            config.add(Arrays.asList(testConfig[i]));
        }
        return config;
    }
    List<Integer> user = new ArrayList<>(List.of(1));
    List<Integer> agent = new ArrayList<>(List.of(2));
    EvaluationFunction eval = new NumOpenLines(0, agent, user, 3);
    WinType win = new ConsecutivePieces(3);
    Agent myAgent = new Agent(win, new ArrayList<>(List.of(eval)),agent,user);
    GamePieceFactory ticTacToeFactory = new GamePieceFactory("Tic-Tac-Toe", user,agent,0, 0,0);
    List<Integer> zeros = new ArrayList<>(List.of(0,0,0));
    List<List<Integer>> objectConfig = new ArrayList<>(List.of(zeros,zeros,zeros));
    Game inProgressGame = new Game(ticTacToeFactory, createTestConfig(startingConfig),objectConfig,
            new ArrayList<>(), user,agent, true, myAgent);
    Game noMovesLeftGame = new Game(ticTacToeFactory, createTestConfig(noMovesConfig),objectConfig,
            new ArrayList<>(), user,agent, true, myAgent);
    Game player1WinGame = new Game(ticTacToeFactory, createTestConfig(player1Win),objectConfig,
            new ArrayList<>(), user,agent,true, myAgent);
    Game player2WinGme = new Game(ticTacToeFactory, createTestConfig(player2Win),objectConfig,
            new ArrayList<>(), user,agent, true, myAgent);

    @Test
    void testMakeUserAndAgentMove() {
        //making the user move to (2,2) - bottom right
        inProgressGame.makeGameMove(Arrays.asList(new Integer[]{2,2,2,2}));
        Integer[][] userMove = {
                {0,0,0},
                {0,1,0},
                {0,0,1}
        };
        assertEquals(createTestConfig(userMove), inProgressGame.getVisualInfo());

        //making the agent move somewhere, as calculated by AI algo
        inProgressGame.makeGameMove(Arrays.asList(new Integer[]{2,2,2,2}));
        int numAgents = 0;
        for (List<Integer> row: inProgressGame.getVisualInfo()) {
            numAgents += Collections.frequency(row, 2); //agentID = 2
        }
        assertEquals(1,numAgents);
    }


    @Test
    void testGetEndGameStatus() {
        //for game that is in progress
        inProgressGame.makeGameMove(Arrays.asList(new Integer[]{0,0,0,0}));
        assertEquals(0, inProgressGame.getEndGameStatus());
        //for game that player1 wins
        assertEquals(1, player1WinGame.getEndGameStatus());
        //for game that player2 wins
        assertEquals(2, player2WinGme.getEndGameStatus());
        //for game that has no moves left
        assertEquals(3, noMovesLeftGame.getEndGameStatus());
    }
}

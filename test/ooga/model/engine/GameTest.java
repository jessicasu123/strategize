package ooga.model.engine;

import ooga.model.engine.GameTypeFactory.TicTacToeFactory;
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

    Game inProgressGame = new Game(new TicTacToeFactory(1,2), createTestConfig(startingConfig),
            new ArrayList<>(), 1, 2);
    Game noMovesLeftGame = new Game(new TicTacToeFactory(1,2), createTestConfig(noMovesConfig),
            new ArrayList<>(), 1, 2);
    Game player1WinGame = new Game(new TicTacToeFactory(1,2), createTestConfig(player1Win),
            new ArrayList<>(), 1, 2);
    Game player2WinGme = new Game(new TicTacToeFactory(1,2), createTestConfig(player2Win),
            new ArrayList<>(), 1, 2);

    @Test
    void testMakeUserAndAgentMove() {
        //making the user move to (2,2) - bottom right
        inProgressGame.makeUserMove(Arrays.asList(new Integer[]{2,2,2,2}));
        Integer[][] userMove = {
                {0,0,0},
                {0,1,0},
                {0,0,1}
        };
        assertEquals(createTestConfig(userMove), inProgressGame.getVisualInfo());

        //making the agent move somewhere, as calculated by AI algo
        inProgressGame.makeAgentMove();
        int numAgents = 0;
        for (List<Integer> row: inProgressGame.getVisualInfo()) {
            numAgents += Collections.frequency(row, 2); //agentID = 2
        }
        assertEquals(1,numAgents);
    }


    @Test
    void testGetEndGameStatus() {
        //for game that is in progress
        assertEquals(0, inProgressGame.getEndGameStatus());
        //for game that player1 wins
        assertEquals(1, player1WinGame.getEndGameStatus());
        //for game that player2 wins
        assertEquals(2, player2WinGme.getEndGameStatus());
        //for game that has no moves left
        assertEquals(3, noMovesLeftGame.getEndGameStatus());
    }
}

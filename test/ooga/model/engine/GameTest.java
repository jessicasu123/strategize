package ooga.model.engine;

import ooga.model.engine.agent.Agent;
import ooga.model.engine.agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.agent.evaluationFunctions.NumOpenLines;
import ooga.model.engine.agent.winTypes.ConsecutivePieces;
import ooga.model.engine.agent.winTypes.WinType;
import ooga.model.engine.player.PlayerInfoHolder;
import ooga.model.engine.pieces.ChangeToNewStateMove;
import ooga.model.engine.pieces.GamePieceCreator;
import ooga.model.engine.pieces.moveChecks.EmptyStateCheck;
import ooga.model.engine.pieces.moveChecks.MoveCheck;
import ooga.model.engine.pieces.MoveType;
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
   List<Integer> zeros = new ArrayList<>(List.of(0,0,0));
    List<List<Integer>> objectConfig = new ArrayList<>(List.of(zeros,zeros,zeros));

    List<Integer> direction = new ArrayList<>(List.of(1));
    MoveCheck checkEmptyState = new EmptyStateCheck(0);
    MoveType changeToNewState = new ChangeToNewStateMove();
    PlayerInfoHolder player1InfoTicTacToe = new PlayerInfoHolder(user, direction,new ArrayList<>(List.of(checkEmptyState)),new ArrayList<>(),new ArrayList<>(List.of(changeToNewState)),true);
    PlayerInfoHolder player2InfoTicTacToe = new PlayerInfoHolder(agent,direction,new ArrayList<>(List.of(checkEmptyState)),new ArrayList<>(),new ArrayList<>(List.of(changeToNewState)),false);

    GamePieceCreator gamePieceCreator = new GamePieceCreator(player1InfoTicTacToe, player2InfoTicTacToe);

    Game inProgressGame = new Game(gamePieceCreator, new Grid(createTestConfig(startingConfig)), new Grid(objectConfig),
            new ArrayList<>(), player1InfoTicTacToe,player2InfoTicTacToe, myAgent,0);
    Game noMovesLeftGame = new Game(gamePieceCreator, new Grid(createTestConfig(noMovesConfig)),new Grid(objectConfig),
            new ArrayList<>(), player1InfoTicTacToe,player2InfoTicTacToe, myAgent,0);
    Game player1WinGame = new Game(gamePieceCreator, new Grid(createTestConfig(player1Win)),new Grid(objectConfig),
            new ArrayList<>(), player1InfoTicTacToe,player2InfoTicTacToe, myAgent,0);
    Game player2WinGme = new Game(gamePieceCreator, new Grid(createTestConfig(player2Win)),new Grid(objectConfig),
            new ArrayList<>(), player1InfoTicTacToe,player2InfoTicTacToe, myAgent,0);

    @Test
    void testMakeUserAndAgentMove() {
        //making the user move to (2,2) - bottom right
        inProgressGame.makeGameMove(Arrays.asList(new Integer[]{2,2,2,2}));
        Integer[][] userMove = {
                {0,0,0},
                {0,1,0},
                {0,0,1}
        };
        assertEquals(new Grid(createTestConfig(userMove)), inProgressGame.getVisualInfo());

        //making the agent move somewhere, as calculated by AI algo
        inProgressGame.makeGameMove(Arrays.asList(new Integer[]{2,2,2,2}));
        int numAgents = 0;
        for(int r = 0 ; r < inProgressGame.getVisualInfo().numRows(); r++){
            for(int c = 0 ; c < inProgressGame.getVisualInfo().numCols(); c++){
                if(inProgressGame.getVisualInfo().getVal(r,c) == 2){
                    numAgents++;
                }
            }
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

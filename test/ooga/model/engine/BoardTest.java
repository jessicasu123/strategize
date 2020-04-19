package ooga.model.engine;

import ooga.model.engine.GameTypeFactory.CheckersFactory;
import ooga.model.engine.GameTypeFactory.MancalaFactory;
import ooga.model.engine.GameTypeFactory.OthelloFactory;
import ooga.model.engine.GameTypeFactory.TicTacToeFactory;
import ooga.model.engine.exceptions.InvalidMoveException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.*;

public class BoardTest {
    Integer[][] startingConfig = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    };

    Integer[][] noMovesConfig = {
            {1, 2, 1},
            {1, 2, 1},
            {2, 1, 2}
    };

    public List<List<Integer>> createTestConfig(Integer[][] testConfig) {
        List<List<Integer>> config = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            config.add(Arrays.asList(testConfig[i]));
        }
        return config;
    }

    //creating tic tac toe board with one player in center
    List<List<Integer>> config = createTestConfig(startingConfig);
    List<String> neighborhoods = new ArrayList<>();
    List<Integer> user = new ArrayList<>(List.of(1));
    List<Integer> agent = new ArrayList<>(List.of(2));
    Board ticTacToeBoard = new Board(new TicTacToeFactory(user, agent,0), config, neighborhoods);

    //board that has no more moves
    List<List<Integer>> noMoves = createTestConfig(noMovesConfig);
    Board noMovesBoard = new Board(new TicTacToeFactory(user, agent,0), noMoves, neighborhoods);

    List<Integer> row1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0));
    List<Integer> row2 = new ArrayList<>(List.of(0, 0, 1, 0, 0, 0, 0, 0));
    List<Integer> row3 = new ArrayList<>(List.of(0, 1, 1, 0, 0, 0, 0, 0));
    List<Integer> row4 = new ArrayList<>(List.of(2, 2, 2, 2, 1, 0, 0, 0));
    List<Integer> row5 = new ArrayList<>(List.of(0, 0, 0, 1, 2, 0, 0, 0));
    List<Integer> row6 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0));
    List<Integer> row7 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0));
    List<Integer> row8 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0));
    List<List<Integer>> othelloConfig = new ArrayList<>(List.of(row1, row2, row3, row4, row5, row6, row7, row8));
    List<String> othelloNeighborhoods = List.of("horizontal", "vertical", "diagonal");
    Board othelloBoard = new Board(new OthelloFactory(user, agent), othelloConfig, othelloNeighborhoods);

    @Test
    void testOthelloBoard() {
        Map<Coordinate, List<Coordinate>> moves = othelloBoard.getAllLegalMoves(user);
        for (Coordinate c : moves.keySet()) {
            System.out.println(moves.get(c));
        }
    }

    @Test
    void testCreateBoardFromStartingConfig() {
        /**
         * stateInfo is a reflection of the Board contents -
         * it populates its values based on the STATE of the
         * PIECES initialized by the board. If the board is initialized
         * with game pieces correctly, their state should match the starting
         * config values.
         */

        List<List<Integer>> stateInfo = ticTacToeBoard.getStateInfo();
        assertEquals(stateInfo, config);
    }

    @Test
    void testCopyBoard() {
        BoardFramework newBoard = ticTacToeBoard.copyBoard();
        //making a move on newBoard - this should NOT affect other board, b
        newBoard.makeMove(1, new Coordinate(0, 0), new Coordinate(0, 0));

        //checking that states for newBoard are updated, but b remains same
        assertNotEquals(newBoard.getStateInfo(), ticTacToeBoard.getStateInfo());

        //b should still reflect starting config
        assertEquals(ticTacToeBoard.getStateInfo(), config);
    }

    @Test
    void testMakeMove() {
        //test for a valid move
        BoardFramework testMoveBoard = ticTacToeBoard.copyBoard();
        testMoveBoard.makeMove(1, new Coordinate(2, 2), new Coordinate(2, 2));
        Integer[][] desiredConfig = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        assertEquals(createTestConfig(desiredConfig), testMoveBoard.getStateInfo());

        //test for invalid move - can't try to move on a square that already has a player
        assertThrows(InvalidMoveException.class,
                () -> testMoveBoard.makeMove(2, new Coordinate(1, 1), new Coordinate(1, 1)));
    }

    @Test
    void testGetAllLegalMoves() {
        Map<Coordinate, List<Coordinate>> moves = ticTacToeBoard.getAllLegalMoves(user);
        Coordinate squareWithPlayer = new Coordinate(1, 1);

        //checking that a coordinate with an empty square is a legal "move"
        Coordinate emptySquare = new Coordinate(1, 2);
        assertEquals(1, moves.get(emptySquare).size());
    }

    @Test
    void testNoMovesLeft() {
        //should be no moves left on a full board with no winner
        assertEquals(true, noMovesBoard.checkNoMovesLeft(user,agent));
        //should be moves left on board with only one player
        assertEquals(false, ticTacToeBoard.checkNoMovesLeft(user,agent));
    }

    @Test
    void testCheckersBoard() {
        row1 =new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        row2 =new ArrayList<>(List.of(0,0,1,0,0,0,0,0));
        row3 =new ArrayList<>(List.of(0,1,1,0,0,0,0,0));
        row4 =new ArrayList<>(List.of(2,2,2,2,1,0,0,0));
        row5 =new ArrayList<>(List.of(0,0,0,1,2,0,0,0));
        row6 =new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        row7 =new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        row8 =new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<List<Integer>> checkersConfig = new ArrayList<>(List.of(row1, row2, row3, row4, row5, row6, row7, row8));
        List<String> checkersNeighborhoods = List.of("horizontal", "vertical", "diagonal");
        List<Integer> user2 = new ArrayList<>(List.of(1,3));
        List<Integer> agent2 = new ArrayList<>(List.of(2,4));
        Board checkersBoard = new Board(new CheckersFactory(user2, agent2,true,0),
                checkersConfig, checkersNeighborhoods);

        Coordinate start = new Coordinate(1,2);
        Coordinate end = new Coordinate(2,3);
        checkersBoard.makeMove(1,start,end);
        assertNotEquals(checkersConfig, checkersBoard.getStateInfo());
    }

    @Test
    void testMancalaBoard() {
        row1 =new ArrayList<>(List.of(1,2,2,2,2,2,2,0));
        row2 =new ArrayList<>(List.of(0,4,4,4,4,4,4,3));
        List<List<Integer>> mancalaConfig = new ArrayList<>(List.of(row1, row2));
        List<String> mancalaNeighborhoods = List.of("horizontal", "vertical");
        List<Integer> user2 = new ArrayList<>(List.of(2,1));
        List<Integer> agent2 = new ArrayList<>(List.of(4,3));
        Board mancalaBoard = new Board(new MancalaFactory(user2,agent2,false,0,4),
                mancalaConfig, mancalaNeighborhoods);
        Coordinate start = new Coordinate(0,2);
        Coordinate end = new Coordinate(0,2);
        mancalaBoard.makeMove(2,start,end);
        assertNotEquals(mancalaConfig, mancalaBoard.getStateInfo());
    }
}


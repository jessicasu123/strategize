package ooga.model.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.*;

public class BoardTest {
    Integer[][] startingConfig = {
            {0,0,0},
            {0,0,0},
            {0,0,0}
    };

    public List<List<Integer>> createTestConfig() {
        List<List<Integer>> config = new ArrayList<>();
        for (int i = 0; i < 3;i++) {
            config.add(Arrays.asList(startingConfig[i]));
        }
        return config;
    }

    //creating board object
    List<List<Integer>> config = createTestConfig();
    Board b = new Board("Tic-Tac-Toe", config);

    @Test
    void testCreateBoardFromStartingConfig() {
        /**
         * stateInfo is a reflection of the Board contents -
         * it populates its values based on the STATE of the
         * PIECES initialized by the board. If the board is initialized
         * with game pieces correctly, their state should match the starting
         * config values.
         */

        List<List<Integer>> stateInfo = b.getStateInfo();
        assertEquals(stateInfo, config);
    }

    @Test
    void testCopyBoard() {
        BoardFramework newBoard = b.copyBoard();
        //making a move on newBoard - this should NOT affect other board, b
        //newBoard.makeMove(1, new Coordinate(0,0), new Coordinate(0,0));

        //checking that states for newBoard are updated, but b remains same
        //assertNotEquals(newBoard.getStateInfo(), b.getStateInfo());

        //b should still reflect starting config
        //assertEquals(b.getStateInfo(), config);
    }

}


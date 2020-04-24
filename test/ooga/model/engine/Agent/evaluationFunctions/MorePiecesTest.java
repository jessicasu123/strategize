package ooga.model.engine.Agent.evaluationFunctions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MorePiecesTest {

    //TEST FOR OTHELLO
    @Test
    void testMorePiecesOthello() {
        List<Integer> initial_row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> initial_row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> initial_row3 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> initial_row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> initial_row5 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> initial_row6 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> initial_row7 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<Integer> initial_row8 = new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<List<Integer>> initialConfig = new ArrayList<>(List.of(initial_row1,initial_row2,initial_row3,initial_row4,
                initial_row5,initial_row6,initial_row7,initial_row8));

        List<Integer> user = new ArrayList<>(List.of(1));
        List<Integer> agent = new ArrayList<>(List.of(2));
        int stateIndex = 0;

        List<Integer> row1 = new ArrayList<>(List.of(2,0,0,0,0,0,0,2));
        List<Integer> row2 = new ArrayList<>(List.of(0,2,2,2,2,2,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0,2,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,1,1,2,2,2,2));
        List<Integer> row5 = new ArrayList<>(List.of(0,0,1,0,2,0,0,2));
        List<Integer> row6 = new ArrayList<>(List.of(0,0,0,1,0,0,0,2));
        List<Integer> row7 = new ArrayList<>(List.of(0,0,0,0,1,0,0,2));
        List<Integer> row8 = new ArrayList<>(List.of(1,0,0,0,0,1,2,2));
        //18 of max pieces
        //7 of min pieces
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6,row7,row8));
        MorePieces moreOthelloPiecesEval = new MorePieces(stateIndex, agent, user,initialConfig, true);
        assertEquals(11, moreOthelloPiecesEval.evaluate(boardConfig,boardConfig, false));
    }

    @Test
    void testMorePiecesMancala() {
        List<Integer> initial_row1 = new ArrayList<>(List.of(4,3,3,3,3,3,3,0));
        List<Integer> initial_row2 = new ArrayList<>(List.of(0,1,1,1,1,1,1,2));
        List<List<Integer>> initialConfig = new ArrayList<>(List.of(initial_row1,initial_row2));

        List<Integer> agent = new ArrayList<>(List.of(3,4));
        List<Integer> user = new ArrayList<>(List.of(1,2));
        int stateIndex = 1;

        List<Integer> row1 = new ArrayList<>(List.of(14,12,0,0,0,2,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,1,1,1,0,0,0,17));
        //15 max pieces in agent goal pos
        //18 min pieces in user goal pos
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2));
        MorePieces moreMancalaPiecesEval = new MorePieces(stateIndex, agent, user, initialConfig, false);
        assertEquals(-3, moreMancalaPiecesEval.evaluate(boardConfig,boardConfig, false));
    }

}

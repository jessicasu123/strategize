package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.BoardConfiguration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumOpenLinesTest {

    @Test
    void testNumOpenLinesConnect4() {

        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,0,2,0,0,0,0));
        List<Integer> row6 = new ArrayList<>(List.of(0,0,1,1,0,0,0));
        BoardConfiguration boardconfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6)));

        int stateIndex = 0;
        List<Integer> minstates = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> maxstates = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        NumOpenLines numopen = new NumOpenLines(stateIndex, maxstates,minstates,4);
        assertEquals(0, numopen.evaluate(boardconfig,boardconfig, false));

    }

    @Test
    void testNumOpenLinesTicTacToe(){
        List<Integer> row1 = new ArrayList<>(List.of(1,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,1,0));
        List<Integer> row3 = new ArrayList<>(List.of(2,2,1));
        BoardConfiguration boardconfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3)));

        int stateIndex = 0;
        List<Integer> minstates = new ArrayList<>(List.of(0,0,0));
        List<Integer> maxstates = new ArrayList<>(List.of(1,0,0));
        NumOpenLines numopen = new NumOpenLines(stateIndex, maxstates,minstates,3);
        assertEquals(2, numopen.evaluate(boardconfig,boardconfig, true));

    }
}
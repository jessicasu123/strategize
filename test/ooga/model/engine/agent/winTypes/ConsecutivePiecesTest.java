package ooga.model.engine.agent.winTypes;

import ooga.model.engine.Grid;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsecutivePiecesTest {
    @Test
    void isWinForMaxConnect4() {
        ConsecutivePieces cp = new ConsecutivePieces(4);
        //test horizontal
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        List<Integer> row5 = new ArrayList<>(List.of(0,0,2,2,2,0,0));
        List<Integer> row6 = new ArrayList<>(List.of(0,0,1,1,1,1,0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        List<Integer> playerstate = new ArrayList<>(List.of(0,1,2));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));

        //test vertical
        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,1,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        row5 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        row6 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        playerstate = new ArrayList<>(List.of(0,1,2));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));


        //test diagonal
        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0,0,0,1));
        row4 = new ArrayList<>(List.of(0,0,0,0,2,1,2));
        row5 = new ArrayList<>(List.of(0,0,0,0,1,2,2));
        row6 = new ArrayList<>(List.of(0,0,0,1,2,1,1));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        playerstate = new ArrayList<>(List.of(1));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));
    }

    @Test
    void isWinForMinConnect4() {
        ConsecutivePieces cp = new ConsecutivePieces(4);
        List<Integer> row1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0));
        List<Integer> row2 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0));
        List<Integer> row3 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0));
        List<Integer> row4 = new ArrayList<>(List.of(0, 0, 1, 1, 0, 0, 0));
        List<Integer> row5 = new ArrayList<>(List.of(0, 0, 2, 2, 2, 2, 0));
        List<Integer> row6 = new ArrayList<>(List.of(0, 0, 1, 1, 1, 2, 0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        List<Integer> playerstate = new ArrayList<>(List.of(2));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));

        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,0,2,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        row5 = new ArrayList<>(List.of(0,0,0,1,2,0,0));
        row6 = new ArrayList<>(List.of(0,0,1,1,2,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        playerstate = new ArrayList<>(List.of(2));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));

        row1 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row2 = new ArrayList<>(List.of(0,0,0,0,0,0,0));
        row3 = new ArrayList<>(List.of(0,0,0,2,0,0,0));
        row4 = new ArrayList<>(List.of(0,0,0,1,2,1,2));
        row5 = new ArrayList<>(List.of(0,0,0,1,1,2,2));
        row6 = new ArrayList<>(List.of(0,0,1,1,2,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3,row4,row5,row6));
        playerstate = new ArrayList<>(List.of(2));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));
    }

    @Test
    void isWinForMaxTicTacToe() {
        ConsecutivePieces cp = new ConsecutivePieces(3);
        List<Integer> row1 = new ArrayList<>(List.of(1,1,1));
        List<Integer> row2 = new ArrayList<>(List.of(0,2,0));
        List<Integer> row3 = new ArrayList<>(List.of(2,0,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        List<Integer> playerstate = new ArrayList<>(List.of(1));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));

        row1 = new ArrayList<>(List.of(2,1,2));
        row2 = new ArrayList<>(List.of(0,1,0));
        row3 = new ArrayList<>(List.of(2,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        playerstate = new ArrayList<>(List.of(1));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));

        row1 = new ArrayList<>(List.of(1,2,2));
        row2 = new ArrayList<>(List.of(0,1,0));
        row3 = new ArrayList<>(List.of(2,2,1));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        playerstate = new ArrayList<>(List.of(1));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));
    }

    @Test
    void isWinForMinTicTacToe() {
        ConsecutivePieces cp = new ConsecutivePieces(3);
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1));
        List<Integer> row2 = new ArrayList<>(List.of(1,2,0));
        List<Integer> row3 = new ArrayList<>(List.of(2,2,2));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        List<Integer> playerstate = new ArrayList<>(List.of(2));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));

        row1 = new ArrayList<>(List.of(1,1,2));
        row2 = new ArrayList<>(List.of(0,0,2));
        row3 = new ArrayList<>(List.of(1,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        playerstate = new ArrayList<>(List.of(2));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));

        row1 = new ArrayList<>(List.of(2,1,1));
        row2 = new ArrayList<>(List.of(0,2,0));
        row3 = new ArrayList<>(List.of(2,1,2));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        playerstate = new ArrayList<>(List.of(2));
        assertTrue(cp.isWin(playerstate,new Grid(sampleConfig),new Grid(sampleConfig), false));
    }
}
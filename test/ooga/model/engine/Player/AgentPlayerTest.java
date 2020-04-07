package ooga.model.engine.Player;

import ooga.model.engine.Agent.TicTacToeAgent;
import ooga.model.engine.Board;
import ooga.model.engine.BoardFramework;
import ooga.model.engine.Coordinate;
import ooga.model.engine.InvalidMoveException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
//TODO: check to make sure works (currently things aren't implemented so can't run tests)
class AgentPlayerTest {

    private AgentPlayer myTicTacToeAgentPlayer = new AgentPlayer(1, new TicTacToeAgent(1,2,3),2);
    @Test
    void getPlayerID() {
        assertEquals(1, myTicTacToeAgentPlayer.getPlayerID());
        assertNotEquals(2, myTicTacToeAgentPlayer.getPlayerID());
    }
/**
    @Test
    void calculateMove() throws InvalidMoveException {
        //empty board -> test when everything is equal
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        BoardFramework myBoard = new Board("Tic-Tac-Toe", sampleConfig);
        Map<Coordinate, Coordinate> moveChosen = new HashMap<>();
        moveChosen.put(new Coordinate(1,1), new Coordinate(1,1));
        for(Map.Entry<Coordinate, Coordinate> moves: moveChosen.entrySet()){
            assertEquals(moves, myTicTacToeAgentPlayer.calculateMove(myBoard));
        }

        //test when everything is even in current position
        row1 = new ArrayList<>(List.of(0,0,0));
        row2 = new ArrayList<>(List.of(1,0,2));
        row3 = new ArrayList<>(List.of(0,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        myBoard = new Board("Tic-Tac-Toe", sampleConfig);
        moveChosen = new HashMap<>();
        moveChosen.put(new Coordinate(0,2), new Coordinate(0,2));
        for(Map.Entry<Coordinate, Coordinate> moves: moveChosen.entrySet()){
            assertEquals(moves, myTicTacToeAgentPlayer.calculateMove(myBoard));
        }

        //test finds winning position
        row1 = new ArrayList<>(List.of(1,1,0));
        row2 = new ArrayList<>(List.of(2,0,0));
        row3 = new ArrayList<>(List.of(2,0,0));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        myBoard = new Board("Tic-Tac-Toe", sampleConfig);
        moveChosen = new HashMap<>();
        moveChosen.put(new Coordinate(0,2), new Coordinate(0,2));
        for(Map.Entry<Coordinate, Coordinate> moves: moveChosen.entrySet()){
            assertEquals(moves, myTicTacToeAgentPlayer.calculateMove(myBoard));
        }

        //test prevents losing position
        row1 = new ArrayList<>(List.of(1,2,0));
        row2 = new ArrayList<>(List.of(0,2,0));
        row3 = new ArrayList<>(List.of(2,1,1));
        sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        myBoard = new Board("Tic-Tac-Toe", sampleConfig);
        moveChosen = new HashMap<>();
        moveChosen.put(new Coordinate(0,2), new Coordinate(0,2));
        for(Map.Entry<Coordinate, Coordinate> moves: moveChosen.entrySet()){
            assertEquals(moves, myTicTacToeAgentPlayer.calculateMove(myBoard));
        }
    }
*/
}
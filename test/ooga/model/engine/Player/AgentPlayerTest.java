package ooga.model.engine.Player;

import ooga.model.engine.Agent.TicTacToeAgent;
import ooga.model.engine.Board;
import ooga.model.engine.BoardFramework;
import ooga.model.engine.Coordinate;
import ooga.model.engine.GameTypeFactory.TicTacToeFactory;
import ooga.model.engine.InvalidMoveException;
import ooga.model.engine.Neighborhood.VerticalNeighborhood;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AgentPlayerTest {
    //doing search depth of 1 so it can easily be checked
    private AgentPlayer myTicTacToeAgentPlayer = new AgentPlayer(1, new TicTacToeAgent(1,2,3),2, 1);
    @Test
    void getPlayerID() {
        assertEquals(1, myTicTacToeAgentPlayer.getPlayerID());
        assertNotEquals(2, myTicTacToeAgentPlayer.getPlayerID());
    }

    @Test
    void calculateMoveEmptyBoard() throws InvalidMoveException {
        //empty board -> test when everything is equal
        List<Integer> row1 = new ArrayList<>(List.of(0, 0, 0));
        List<Integer> row2 = new ArrayList<>(List.of(0, 0, 0));
        List<Integer> row3 = new ArrayList<>(List.of(0, 0, 0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1, row2, row3));
        BoardFramework myBoard = new Board(new TicTacToeFactory(2,1), sampleConfig, new ArrayList<>(List.of("horizontal")));
        Coordinate coord = new Coordinate(1, 1);
        Map.Entry<Coordinate, Coordinate> move = new AbstractMap.SimpleImmutableEntry<>(coord, coord);
        Map.Entry<Coordinate, Coordinate> myMove = myTicTacToeAgentPlayer.calculateMove(myBoard);
        try {
            assertEquals(move, myMove);
        } catch (Exception e) {
            System.out.println("board exception");
        }
    }
    @Test
    void calculateMoveTiedBoard() throws InvalidMoveException {

        //test when everything is even in current position
        List<Integer> row1 = new ArrayList<>(List.of(0, 0, 0));
        List<Integer> row2 = new ArrayList<>(List.of(1, 0, 2));
        List<Integer> row3 = new ArrayList<>(List.of(0, 0, 0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1, row2, row3));
        BoardFramework myBoard = new Board(new TicTacToeFactory(2,1), sampleConfig, new ArrayList<>(List.of("horizontal")));
        Coordinate coord = new Coordinate(2,2);
        Map.Entry<Coordinate, Coordinate> move = new AbstractMap.SimpleImmutableEntry<>(coord, coord);
        Map.Entry<Coordinate, Coordinate> myMove = myTicTacToeAgentPlayer.calculateMove(myBoard);
        try {
            assertEquals(move, myMove);
        } catch (Exception e) {
            System.out.println("board exception");
        }
    }

    @Test
    void calculateMoveWinningBoard() throws InvalidMoveException {
        //test finds winning position
        List<Integer> row1 = new ArrayList<>(List.of(1, 1, 0));
        List<Integer> row2 = new ArrayList<>(List.of(2, 0, 0));
        List<Integer> row3 = new ArrayList<>(List.of(2, 0, 0));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1, row2, row3));
        BoardFramework myBoard = new Board(new TicTacToeFactory(2,1), sampleConfig, new ArrayList<>(List.of("horizontal")));
        Coordinate coord = new Coordinate(0, 2);
        Map.Entry<Coordinate, Coordinate> move = new AbstractMap.SimpleImmutableEntry<>(coord, coord);
        Map.Entry<Coordinate, Coordinate> myMove = myTicTacToeAgentPlayer.calculateMove(myBoard);
        try {
            assertEquals(move, myMove);
        } catch (Exception e) {
            System.out.println("board exception");
        }
    }

    @Test
    void calculateMoveLosingBoard() throws InvalidMoveException {
        //test prevents losing position
        List<Integer> row1 = new ArrayList<>(List.of(1,2,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,2,0));
        List<Integer> row3 = new ArrayList<>(List.of(2,1,1));
        List<List<Integer>> sampleConfig = new ArrayList<>(List.of(row1,row2,row3));
        BoardFramework myBoard = new Board(new TicTacToeFactory(2,1), sampleConfig, new ArrayList<>(List.of("horizontal")));
        Coordinate coord = new Coordinate(0,2);
        Map.Entry<Coordinate, Coordinate> move = new AbstractMap.SimpleImmutableEntry<>(coord, coord);
        Map.Entry<Coordinate, Coordinate> myMove = myTicTacToeAgentPlayer.calculateMove(myBoard);
        try{
            assertEquals(move, myMove);
        }catch(Exception e){
            System.out.println("board exception");
        }

    }

}
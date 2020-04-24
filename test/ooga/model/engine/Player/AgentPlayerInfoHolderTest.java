package ooga.model.engine.Player;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.Agent.evaluationFunctions.NumOpenLines;
import ooga.model.engine.Agent.winTypes.ConsecutivePieces;
import ooga.model.engine.Agent.winTypes.WinType;
import ooga.model.engine.Board;
import ooga.model.engine.BoardFramework;
import ooga.model.engine.Coordinate;
import ooga.model.engine.exceptions.InvalidMoveException;
import ooga.model.engine.pieces.GamePieceFactory;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AgentPlayerInfoHolderTest {
    //doing search depth of 1 so it can easily be checked
    private List<Integer> user = new ArrayList<>(List.of(2));
    private List<Integer> agent = new ArrayList<>(List.of(1));
    EvaluationFunction eval = new NumOpenLines(0, agent, user, 3);
    WinType win = new ConsecutivePieces(3);
    Agent myAgent = new Agent(win, new ArrayList<>(List.of(eval)),agent,user);
    private AgentPlayer myTicTacToeAgentPlayer = new AgentPlayer(agent, myAgent,user, 1);
    List<Integer> zeros = new ArrayList<>(List.of(0,0,0));
    List<List<Integer>> objectConfig = new ArrayList<>(List.of(zeros,zeros,zeros));

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
        GamePieceFactory ticTacToeFactory = new GamePieceFactory("Tic-Tac-Toe", user,agent,0, 0,0);
        BoardFramework myBoard = new Board(ticTacToeFactory, sampleConfig,objectConfig, new ArrayList<>(List.of("horizontal")));
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
        GamePieceFactory ticTacToeFactory = new GamePieceFactory("Tic-Tac-Toe", user,agent,0, 0,0);
        BoardFramework myBoard = new Board(ticTacToeFactory, sampleConfig, objectConfig, new ArrayList<>(List.of("horizontal")));
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
        GamePieceFactory ticTacToeFactory = new GamePieceFactory("Tic-Tac-Toe", user,agent,0, 0,0);
        BoardFramework myBoard = new Board(ticTacToeFactory, sampleConfig,objectConfig, new ArrayList<>(List.of("horizontal")));
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
        GamePieceFactory ticTacToeFactory = new GamePieceFactory("Tic-Tac-Toe", user,agent,0, 0,0);
        BoardFramework myBoard = new Board(ticTacToeFactory, sampleConfig,objectConfig, new ArrayList<>(List.of("horizontal")));
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
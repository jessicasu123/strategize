package ooga.model.engine;

import ooga.model.engine.Agent.TicTacToeAgent;
import ooga.model.engine.Player.AgentPlayer;

import java.util.List;

public class Game implements GameFramework{
    private Board myBoard;
    private AgentPlayer myAgentPlayer;
    private int myUserPlayerID;
    private int myAgentPlayerID;

    public Game(String gameType, List<List<Integer>> startingConfiguration) {
        myBoard = new Board(gameType, startingConfiguration);
        //TODO: figure out how to assign player ID's
        myUserPlayerID = 1;
        myAgentPlayerID = 2;
        //TODO: assign Agent to agent player based on gameType
        //myAgentPlayer = new AgentPlayer(myAgentPlayerID, new TicTacToeAgent(), myUserPlayerID);
    }

    @Override
    public void makeUserMove(int player, List<Integer> moveCoordinates) throws InvalidMoveException {
        Coordinate startCoord = new Coordinate(moveCoordinates.get(0), moveCoordinates.get(1));
        Coordinate endCoord = new Coordinate(moveCoordinates.get(2), moveCoordinates.get(3));
        myBoard.makeMove(player, startCoord, endCoord);
    }

    @Override
    public void makeAgentMove(int player) throws InvalidMoveException {
        myBoard.makeMove(myAgentPlayerID,
                myAgentPlayer.calculateMove(myBoard.copyBoard()).getKey(),
                myAgentPlayer.calculateMove(myBoard.copyBoard()).getValue());
    }

    @Override
    public int getEndGameStatus() {
        //TODO: call on agent player findWinner()
        return 0;
    }

    @Override
    public List<List<Integer>> getVisualInfo() {
        return myBoard.getStateInfo();
    }
}

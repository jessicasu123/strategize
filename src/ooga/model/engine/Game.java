package ooga.model.engine;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.AgentFactory;
import ooga.model.engine.Player.AgentPlayer;

import java.util.List;
import java.util.Map;

public class Game implements GameFramework{
    private Board myBoard;
    private Agent myAgent;
    private AgentPlayer myAgentPlayer;
    private int myUserPlayerID;
    private int myAgentPlayerID;

    //TODO: currently throwing exception from agent factory, idk where we want to do this
    //should find a way to shorten parameter
    public Game(String gameType, List<List<Integer>> startingConfiguration, List<String> neighborhoods, int userID, int agentID)  {
        myBoard = new Board(gameType, startingConfiguration, neighborhoods);
        myUserPlayerID = userID;
        myAgentPlayerID = agentID;
        try {
            myAgent = new AgentFactory().createAgent(gameType, myAgentPlayerID, myUserPlayerID);
        } catch (InvalidGameTypeException e) {
            System.out.println("game doesn't exist");
        }
        myAgentPlayer = new AgentPlayer(myAgentPlayerID, myAgent, myUserPlayerID);
    }

    @Override
    public void makeUserMove(int player, List<Integer> moveCoordinates) throws InvalidMoveException {
        Coordinate startCoord = new Coordinate(moveCoordinates.get(0), moveCoordinates.get(1));
        Coordinate endCoord = new Coordinate(moveCoordinates.get(2), moveCoordinates.get(3));
        myBoard.makeMove(player, startCoord, endCoord);
    }

    @Override
    public void makeAgentMove(int player) throws InvalidMoveException {
        Map.Entry<Coordinate, Coordinate> agentMove =  myAgentPlayer.calculateMove(myBoard.copyBoard());
        myBoard.makeMove(myAgentPlayerID, agentMove.getKey(), agentMove.getValue());
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

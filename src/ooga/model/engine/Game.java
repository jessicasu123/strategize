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
    //TODO: pass in FileHandler instead, have that return the gameType, startingConfig, and neighborhood
    public Game(String gameType, List<List<Integer>> startingConfiguration, List<String> neighborhoods, int userID, int agentID) {
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

    /**
     * METHOD PURPOSE:
     *  - makes the move of a user player on the board
     * @param moveCoordinates - list of the coordinates the controller has collected from the view for the move
     */
    @Override
    public void makeUserMove(List<Integer> moveCoordinates) throws InvalidMoveException {
        Coordinate startCoord = new Coordinate(moveCoordinates.get(0), moveCoordinates.get(1));
        Coordinate endCoord = new Coordinate(moveCoordinates.get(2), moveCoordinates.get(3));
        myBoard.makeMove(myUserPlayerID, startCoord, endCoord);
    }

    /**
     * METHOD PURPOSE:
     *  - makes the agent's move on the board
     */
    @Override
    public void makeAgentMove() throws InvalidMoveException {
        Map.Entry<Coordinate, Coordinate> agentMove =  myAgentPlayer.calculateMove(myBoard.copyBoard());
        myBoard.makeMove(myAgentPlayerID, agentMove.getKey(), agentMove.getValue());
    }

    /**
     * METHOD PURPOSE:
     *  - gets the status of the game to know if the game is over, and if so who won
     * @return - if the game is still continuing will return 0
     *          - if the game is over:
     *                  -if player1 has won will return 1
     *                  -if player2 has won will return 2
     *                  -if no one won (no moves left) will return 3
     */
    @Override
    public int getEndGameStatus() {
        int result = myAgent.findGameWinner(myBoard.getStateInfo());
        if (result==0 && myBoard.checkNoMovesLeft(myUserPlayerID, myAgentPlayerID)) return 3;
        return result;
    }

    /**
     * METHOD PURPOSE:
     *  - passes along the visual state info from the board so the view can access it
     */
    @Override
    public List<List<Integer>> getVisualInfo() {
        return myBoard.getStateInfo();
    }

    @Override
    public List<List<Integer>> possibleMovesForView() {
        return myBoard.possibleMovesVisualInfo(myUserPlayerID);
    }
}

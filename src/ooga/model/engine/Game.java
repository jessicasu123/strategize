package ooga.model.engine;

import ooga.model.engine.Agent.oldAgent.Agent;
import ooga.model.engine.GameTypeFactory.GameTypeFactory;
import ooga.model.engine.Player.AgentPlayer;
import ooga.model.engine.exceptions.InvalidMoveException;

import java.util.List;
import java.util.Map;

public class Game implements GameFramework{
    private static final int ID_STATE_POS = 0;
    private Board myBoard;
    private Agent myAgent;
    private AgentPlayer myAgentPlayer;
    private boolean isUserTurn;
    private String playerPass;
    private List<Integer> myUserStates;
    private List<Integer> myAgentStates;
    private boolean noMovesForUser;
    private boolean noMovesForAgent;


    //TODO: currently throwing exception from agent factory, idk where we want to do this
    //TODO: pass in FileHandler instead, have that return the gameType, startingConfig, and neighborhood
    public Game(GameTypeFactory gameType, List<List<Integer>> startingConfiguration, List<String> neighborhoods,
                List<Integer> userInfo, List<Integer> agentInfo, boolean userIsPlayer1) {
        myBoard = new Board(gameType, startingConfiguration, neighborhoods);
        myUserStates = userInfo;
        myAgentStates = agentInfo;
        isUserTurn = userIsPlayer1;
        myAgent = gameType.createAgent();
        playerPass = "";
        myAgentPlayer = new AgentPlayer(myAgentStates, myAgent, myUserStates);
        noMovesForUser = false;
        noMovesForAgent = false;
    }


    public void makeGameMove(List<Integer> moveCoordinates) throws InvalidMoveException {
        if(isUserTurn && !noMovesForUser){
            makeUserMove(moveCoordinates);
        }
        else if (!isUserTurn && !noMovesForAgent) {
            makeAgentMove();
        }
        if(myBoard.changeTurns() || noMovesForUser || noMovesForAgent){
            isUserTurn = !isUserTurn;
        }
        //TODO: update empty moves check again
        noMovesForUser = myBoard.checkEmptyMovesForPlayer(myUserStates);
        noMovesForAgent = myBoard.checkEmptyMovesForPlayer(myAgentStates);
        if (noMovesForUser) playerPass = "user";
        if (noMovesForAgent) playerPass = "agent";
    }

    public String whichPlayerPassed() { return playerPass; }

    //TODO: implement later for disabling makeMove button
    public boolean isUserTurn(){
        return isUserTurn;
    }

    /**
     * METHOD PURPOSE:
     *  - makes the move of a user player on the board
     * @param moveCoordinates - list of the coordinates the controller has collected from the view for the move
     */

    private void makeUserMove(List<Integer> moveCoordinates) throws InvalidMoveException {
        Coordinate startCoord = new Coordinate(moveCoordinates.get(0), moveCoordinates.get(1));
        Coordinate endCoord = new Coordinate(moveCoordinates.get(2), moveCoordinates.get(3));
        myBoard.makeMove(myUserStates.get(ID_STATE_POS), startCoord, endCoord);
    }

    /**
     * METHOD PURPOSE:
     *  - makes the agent's move on the board
     */

    private void makeAgentMove() throws InvalidMoveException {
        Map.Entry<Coordinate, Coordinate> agentMove =  myAgentPlayer.calculateMove(myBoard.copyBoard());
        myBoard.makeMove(myAgentStates.get(ID_STATE_POS), agentMove.getKey(), agentMove.getValue());
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
        boolean noMovesLeft = myBoard.checkNoMovesLeft(myUserStates, myAgentStates);
        int result = myAgent.findGameWinner(myBoard.getStateInfo(), noMovesLeft);
        if (result==0 && noMovesLeft) { return 3; }
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

    /**
     * METHOD PURPOSE:
     *  - provides visual info for the possible moves - a row, col position in the list of lists
     *  with the value 1 represents a possible move position. a value of 0 indicates that the
     *  position is NOT a possible move.
     */
    @Override
    public List<List<Integer>> possibleMovesForView() {
        return myBoard.possibleMovesVisualInfo(myUserStates);
    }
}

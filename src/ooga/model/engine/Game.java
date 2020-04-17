package ooga.model.engine;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.GameTypeFactory.GameTypeFactory;
import ooga.model.engine.Player.AgentPlayer;

import java.util.List;
import java.util.Map;

public class Game implements GameFramework{
    private Board myBoard;
    private Agent myAgent;
    private AgentPlayer myAgentPlayer;
    private boolean isUserTurn;
    private String playerPass;
    private PlayerInformationHolder myUserPlayerInformation;
    private PlayerInformationHolder myAgentPlayerInformation;


    //TODO: currently throwing exception from agent factory, idk where we want to do this
    //TODO: pass in FileHandler instead, have that return the gameType, startingConfig, and neighborhood
    public Game(GameTypeFactory gameType, List<List<Integer>> startingConfiguration, List<String> neighborhoods,
                PlayerInformationHolder userInformation, PlayerInformationHolder agentInformation, boolean userIsPlayer1) {
        myBoard = new Board(gameType, startingConfiguration, neighborhoods);
        myUserPlayerInformation = userInformation;
        myAgentPlayerInformation = agentInformation;
        int userPlayerID = myUserPlayerInformation.getPlayerID();
        int agentPlayerID = myAgentPlayerInformation.getPlayerID();
        int specialUser = myUserPlayerInformation.getSpecialPlayerID();
        int specialAgent = myAgentPlayerInformation.getSpecialPlayerID();
        isUserTurn = userIsPlayer1;
        myAgent = gameType.createAgent();
        myAgentPlayer = new AgentPlayer(agentPlayerID, specialAgent, myAgent, userPlayerID, specialUser);
        playerPass = "";
    }


    public void makeGameMove(List<Integer> moveCoordinates) throws InvalidMoveException{
        boolean noMovesForUser = myBoard.checkEmptyMovesForPlayer(myUserPlayerInformation.getPlayerID(), myUserPlayerInformation.getSpecialPlayerID());
        boolean noMovesForAgent = myBoard.checkEmptyMovesForPlayer(myAgentPlayerInformation.getPlayerID(), myAgentPlayerInformation.getSpecialPlayerID());
        if (noMovesForUser) playerPass = "user";
        if (noMovesForAgent) playerPass = "agent";
        if(isUserTurn && ! noMovesForUser){
            makeUserMove(moveCoordinates);
        }
        else if (!isUserTurn && !noMovesForAgent){
            makeAgentMove();
        }
        if(myBoard.changeTurns() || noMovesForUser || noMovesForAgent){
            isUserTurn = !isUserTurn;
        }
        //TODO: update empty moves check again 
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
        myBoard.makeMove(myUserPlayerInformation.getPlayerID(), startCoord, endCoord);
    }

    /**
     * METHOD PURPOSE:
     *  - makes the agent's move on the board
     */

    private void makeAgentMove() throws InvalidMoveException {
        Map.Entry<Coordinate, Coordinate> agentMove =  myAgentPlayer.calculateMove(myBoard.copyBoard());
        myBoard.makeMove(myAgentPlayerInformation.getPlayerID(), agentMove.getKey(), agentMove.getValue());
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
        boolean noMovesLeft = myBoard.checkNoMovesLeft(myUserPlayerInformation.getPlayerID(),
                myAgentPlayerInformation.getPlayerID(), myUserPlayerInformation.getSpecialPlayerID(), myAgentPlayerInformation.getSpecialPlayerID());
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

    @Override
    public List<List<Integer>> possibleMovesForView() {
        return myBoard.possibleMovesVisualInfo(myUserPlayerInformation.getPlayerID(),myUserPlayerInformation.getSpecialPlayerID());
    }
}

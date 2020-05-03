package ooga.model.engine;

import ooga.model.engine.agent.Agent;
import ooga.model.engine.neighborhood.Neighborhood;
import ooga.model.engine.player.AgentPlayer;
import ooga.model.engine.player.PlayerInfoHolder;
import ooga.model.exceptions.InvalidMoveException;
import ooga.model.engine.pieces.GamePieceCreator;

import java.util.List;
import java.util.Map;

/**
 * This class is responsible for implementing the logic behind running a game,
 * communicating with the Controller to get information about game-specific qualities
 * or user choices and game elements that must act on this information, such as the
 * board and the players.
 *
 * To be more specific, the following tasks are managed by Game:
 *  - keeping track of the player information (user, agent), whose turn it is, and whose move to execute
 *  - holding a board object and Agent (which will allow the computer to calculate moves)
 *  - determining the game status (still continuing, win/loss, no moves left)
 *
 * @author Jessica Su, Holly Ansel
 */
public class Game implements GameFramework{
    private static final int ID_STATE_POS = 0;
    public static final String USER_PASS = "user";
    public static final String AGENT_PASS = "agent";
    private Board myBoard;
    private Agent myAgent;
    private AgentPlayer myAgentPlayer;
    private boolean isUserTurn;
    private String playerPass;
    private List<Integer> myUserStates;
    private List<Integer> myAgentStates;
    private boolean noMovesForUser;
    private boolean noMovesForAgent;

    /**
     * Constructor for Game
     * @param gamePieces - the object that holds the information about the game pieces for a particular game
     *                   according the information specified in the config file
     * @param startingConfiguration - the starting configuration of states from the config file
     * @param objectConfiguration - the configuration of number of objects at each row, col position
     * @param neighborhoods - the types of neighborhoods that the pieces in this game will need to consider
     * @param userPlayerInfo - all the information for the user player (ex. states, directions, move types, etc. )
     * @param agentPlayerInfo - all the information for the agent player (ex. states, directions, move types, etc. )
     * @param agent - will perform the AI calculation with minimax function
     * @param emptyState - the integer representing the empty state
     */
    public Game(GamePieceCreator gamePieces, BoardConfiguration startingConfiguration,
                BoardConfiguration objectConfiguration, List<Neighborhood> neighborhoods, PlayerInfoHolder userPlayerInfo,
                PlayerInfoHolder agentPlayerInfo, Agent agent, int emptyState) {
        myBoard = new Board(gamePieces, startingConfiguration, objectConfiguration, neighborhoods, emptyState);
        myUserStates = userPlayerInfo.getPlayerStates();
        myAgentStates = agentPlayerInfo.getPlayerStates();
        isUserTurn = userPlayerInfo.isPlayer1();
        myAgent = agent;
        myAgentPlayer = new AgentPlayer(myAgentStates, myAgent, myUserStates);
        noMovesForUser = false;
        noMovesForAgent = false;
        playerPass = "";
    }

    /**
     * METHOD PURPOSE:
     *  - executes both the user's move IF it's the user's turn AND the agent's
     *  move IF it's the agent's turn. Has logic to check if either player has no moves
     *  and must pass
     * @param moveCoordinates - the start and end coordinates of the user's move
     */
    @Override
    public void makeGameMove(List<Integer> moveCoordinates) throws InvalidMoveException {
        playerPass = "";
        if(isUserTurn && !noMovesForUser){
            makeUserMove(moveCoordinates);
        }
        else if (!isUserTurn && !noMovesForAgent) {
            makeAgentMove();
        }
        if(myBoard.changeTurns() || noMovesForUser || noMovesForAgent){
            isUserTurn = !isUserTurn;
        }
        int numMovesStatus = myBoard.checkNoMovesLeft(myUserStates, myAgentStates);
        noMovesForUser = numMovesStatus == 1;
        noMovesForAgent = numMovesStatus == 2;
        if (noMovesForUser) playerPass = USER_PASS;
        if (noMovesForAgent) playerPass = AGENT_PASS;
    }

    /**
     * METHOD PURPOSE:
     *  - passes along the information about which player passed (either user or agent)
     *  - returns "user" if used passed, "agent" if agent passed, and "" if neither passed
     */
    @Override
    public String whichPlayerPassed() { return playerPass; }


    /**
     * METHOD PURPOSE:
     *  - returns whether it's the user's turn or not
     *  - true if it's the user's turn, false if it's the agent's turn
     *  - used by the view to update the board appearance accordingly
     */
    @Override
    public boolean isUserTurn(){
        return isUserTurn;
    }


    private void makeUserMove(List<Integer> moveCoordinates) throws InvalidMoveException {
        Coordinate startCoord = new Coordinate(moveCoordinates.get(0), moveCoordinates.get(1));
        Coordinate endCoord = new Coordinate(moveCoordinates.get(2), moveCoordinates.get(3));
        myBoard.makeMove(myUserStates.get(ID_STATE_POS), startCoord, endCoord);
    }

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
        int numMovesStatus = myBoard.checkNoMovesLeft(myUserStates, myAgentStates);
        boolean noMovesLeft = numMovesStatus == 0;
        int result = myAgent.findGameWinner(myBoard.getStateInfo(),myBoard.getObjectInfo(), noMovesLeft);
        if (result == 0 && noMovesLeft) { return 3; }
        return result;
    }

    /**
     * METHOD PURPOSE:
     *  - passes along the  info from the board about the PLAYER states
     *  so the view can access it
     */
    @Override
    public BoardConfiguration getVisualInfo() {
        return myBoard.getStateInfo();
    }

    /**
     * METHOD PURPOSE:
     *  - passes along the info from the board about how many OBJECTS are in each position
     *  so the view can access it
     */
    @Override
    public BoardConfiguration getObjectInfo() {return myBoard.getObjectInfo();}

    /**
     * METHOD PURPOSE:
     *  - provides visual info for the possible moves, where a possible move is represented with the
     *  value 1 in row, col position in the list of lists. a value of 0 indicates that the
     *  row, col position is NOT a possible move.
     */
    @Override
    public BoardConfiguration possibleMovesForView() {
        return myBoard.getPossibleMovesInfo(myUserStates);
    }
}

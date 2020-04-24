package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Player.Player;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all of the information needed to create a game piece for each player
 * Then when called upon creates that game piece and determines which player's info to use
 */
public class GamePieceCreator {
    private List<Integer> myUserDirections;
    private List<MoveCheck> myUserMoveChecks;
    private List<MoveCheck> myUserNeighborMoveChecks;
    private List<MoveType> myUserMoveTypes;
    private List<Integer> myAgentDirections;
    private List<MoveCheck> myAgentMoveChecks;
    private List<MoveCheck> myAgentNeighborMoveChecks;
    private List<MoveType> myAgentMoveTypes;
    private List<Integer> myUserStates;
    private List<Integer> myAgentStates;

    /**
     * Stores all of the information needed to create each players pieces
     * @param userPlayer - the user player of the program
     * @param agentPlayer - the agent player of the program
     */
    public GamePieceCreator(Player userPlayer, Player agentPlayer){
        myUserDirections = userPlayer.getDirections();
        myUserMoveChecks = userPlayer.getMoveChecks();
        myUserNeighborMoveChecks = userPlayer.getNeighborMoveChecks();
        myUserMoveTypes = userPlayer.getMoveTypes();
        myAgentDirections = agentPlayer.getDirections();
        myAgentMoveChecks = agentPlayer.getMoveChecks();
        myAgentNeighborMoveChecks = agentPlayer.getNeighborMoveChecks();
        myAgentMoveTypes = agentPlayer.getMoveTypes();
        myUserStates = userPlayer.getPlayerStates();
        myAgentStates = agentPlayer.getPlayerStates();
    }

    /**
     * Creates a game piece using the information stored for each player
     * @param state - the state for this piece
     * @param position - the position for this piece
     * @param numObjects - the number of objects this piece will have
     * @return a game piece created based on the parameters
     */
    public GamePiece createGamePiece(int state, Coordinate position, int numObjects){
        if(myUserStates.contains(state)) {
            return new GamePiece(state, position, numObjects, myUserDirections, myUserMoveChecks, myUserNeighborMoveChecks, myUserMoveTypes);
        }else{
            return new GamePiece(state, position, numObjects, myAgentDirections, myAgentMoveChecks, myAgentNeighborMoveChecks, myAgentMoveTypes);
        }
    }
}

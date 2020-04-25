package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.player.PlayerInfoHolder;
import ooga.model.engine.pieces.moveChecks.MoveCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * Stores all of the information needed to create each players pieces
     * @param userPlayerInfoHolder - the user player of the program
     * @param agentPlayerInfoHolder - the agent player of the program
     */
    public GamePieceCreator(PlayerInfoHolder userPlayerInfoHolder, PlayerInfoHolder agentPlayerInfoHolder){
        myUserDirections = userPlayerInfoHolder.getDirections();
        myUserMoveChecks = userPlayerInfoHolder.getMoveChecks();
        myUserNeighborMoveChecks = userPlayerInfoHolder.getNeighborMoveChecks();
        myUserMoveTypes = userPlayerInfoHolder.getMoveTypes();
        myAgentDirections = agentPlayerInfoHolder.getDirections();
        myAgentMoveChecks = agentPlayerInfoHolder.getMoveChecks();
        myAgentNeighborMoveChecks = agentPlayerInfoHolder.getNeighborMoveChecks();
        myAgentMoveTypes = agentPlayerInfoHolder.getMoveTypes();
        myUserStates = userPlayerInfoHolder.getPlayerStates();
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
            return new GamePiece(state, position, numObjects, new ArrayList<>(myUserDirections), myUserMoveChecks, myUserNeighborMoveChecks, myUserMoveTypes);
        }else{
            return new GamePiece(state, position, numObjects, new ArrayList<>(myAgentDirections), myAgentMoveChecks, myAgentNeighborMoveChecks, myAgentMoveTypes);
        }

    }
}

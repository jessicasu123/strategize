package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Player.Player;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;

import java.util.ArrayList;
import java.util.List;

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

    public GamePiece createGamePiece(int state, Coordinate position, int numObjects){
        if(myUserStates.contains(state)) {
            return new GamePiece(state, position, numObjects, myUserDirections, myUserMoveChecks, myUserNeighborMoveChecks, myUserMoveTypes);
        }else if(myAgentStates.contains(state)){
            return new GamePiece(state, position, numObjects, myAgentDirections, myAgentMoveChecks, myAgentNeighborMoveChecks, myAgentMoveTypes);
        }

        return new GamePiece(state, position, numObjects, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}

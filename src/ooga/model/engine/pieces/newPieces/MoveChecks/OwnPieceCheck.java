package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

/**
 * MoveCheck to determine whether the piece belongs to the player who is checking
 * @author Sanya Kochhar
 */
public class OwnPieceCheck implements MoveCheck{
    private List<Integer> myPlayerStates;

    public OwnPieceCheck(List<Integer> playerStates){
        myPlayerStates = playerStates;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        return myPlayerStates.contains(checking.getState()) && myPlayerStates.contains(player);
    }
}

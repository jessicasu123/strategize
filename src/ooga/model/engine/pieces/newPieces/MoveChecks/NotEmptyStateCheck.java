package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

/**
 * MoveCheck to determine whether a piece being checked is a player piece,
 * i.e., not an empty state
 * @author Sanya Kochhar
 */
public class NotEmptyStateCheck implements MoveCheck{

    private int myEmptyState;

    public NotEmptyStateCheck(int emptyState){
        myEmptyState = emptyState;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        return checking.getState() != myEmptyState;
    }
}

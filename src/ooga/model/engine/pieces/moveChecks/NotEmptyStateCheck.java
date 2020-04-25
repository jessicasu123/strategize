package ooga.model.engine.pieces.moveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

import java.util.List;

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

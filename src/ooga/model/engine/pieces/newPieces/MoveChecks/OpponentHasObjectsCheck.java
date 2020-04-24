package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public class OpponentHasObjectsCheck implements MoveCheck{

    private int myEmptyState;
    private List<Integer> myStates;

    public OpponentHasObjectsCheck(int emptyState, List<Integer> playerStates){
        myEmptyState = emptyState;
        myStates = playerStates;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        boolean isNotPlayerState = true;
        for (int myState : myStates) {
            if (checking.getState() == myState) {
                isNotPlayerState = false;
            }
        }
        return isNotPlayerState && checking.getState() != myEmptyState && checking.getNumObjects() > 0;
    }
}

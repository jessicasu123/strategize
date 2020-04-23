package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public class CheckOpponentPiece implements MoveCheck{

    private int myEmptyState;
    private List<Integer> myStates;

    public CheckOpponentPiece(int emptyState, List<Integer> playerStates){
        myEmptyState = emptyState;
        myStates = playerStates;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int state, List<Integer> directions) {
        for (int myState : myStates) {
            if (checking.getState() == myState) {
                return false;
            }
        }
        return checking.getState() != myEmptyState;
    }
}

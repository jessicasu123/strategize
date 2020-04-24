package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public class CheckEmptyState implements MoveCheck{

    private int myEmptyState;

    public CheckEmptyState(int emptystate){
        myEmptyState = emptystate;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        return checking.getState() == myEmptyState;
    }
}

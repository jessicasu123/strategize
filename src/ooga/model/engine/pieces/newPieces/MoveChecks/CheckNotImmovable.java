package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public class CheckNotImmovable implements MoveCheck {
    private int myNonMovingState;
    public CheckNotImmovable(int nonMovingState){
        myNonMovingState = nonMovingState;
    }
    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int state, List<Integer> directions) {
        return checking.getState() != myNonMovingState;
    }
}

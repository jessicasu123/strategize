package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public class CheckStep implements MoveCheck {
    public static final int STEP_SIZE = 1;

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int state, List<Integer> directions) {
        return checkStepRow(startingLocation, checking, directions) && checkStepCol(startingLocation, checking);
    }

    private boolean checkStepRow(Coordinate startingLocation, GamePiece checking, List<Integer> directions){
        for(int direction: directions){
            if(startingLocation.getRow() + direction == checking.getPosition().getRow()){
                return true;
            }
        }
        return false;
    }

    private boolean checkStepCol(Coordinate startingLocation, GamePiece checking){
        return startingLocation.getCol() + STEP_SIZE == checking.getPosition().getCol() ||
                startingLocation.getCol() - STEP_SIZE == checking.getPosition().getCol();
    }
}

package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public class CheckNumObjects implements MoveCheck{

    private int myComparisonNum;

    public CheckNumObjects(int numObjectsToCompare){
        myComparisonNum = numObjectsToCompare;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int state, List<Integer> directions) {
        return checking.getNumObjects() > myComparisonNum;
    }
}

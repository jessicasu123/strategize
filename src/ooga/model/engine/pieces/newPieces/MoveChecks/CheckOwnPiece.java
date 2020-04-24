package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public class CheckOwnPiece implements MoveCheck{
    private List<Integer> myPlayerStates;

    public CheckOwnPiece(List<Integer> playerStates){
        myPlayerStates = playerStates;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int state, List<Integer> directions) {
        return myPlayerStates.contains(checking.getState());
    }
}

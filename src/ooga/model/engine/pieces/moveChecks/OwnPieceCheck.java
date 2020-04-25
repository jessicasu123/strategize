package ooga.model.engine.pieces.moveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

import java.util.List;

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

package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public class CheckEmptyState implements MoveCheck{

    @Override
    public boolean isConditionMet(List<GamePiece> neighbors, int state) {
        return false;
    }

}

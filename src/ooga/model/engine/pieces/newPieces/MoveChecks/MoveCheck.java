package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public interface MoveCheck {

    boolean isConditionMet(List<GamePiece> neighbors, int state);
}

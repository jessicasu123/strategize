package ooga.model.engine.pieces.newPieces.MoveTypes;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public interface MoveType{

    void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction);


}

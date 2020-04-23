package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;

import java.util.List;

public class ForceMoveAgain implements MoveType{

    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors,
                                 int playerState, int direction) {

    }

    @Override
    public boolean addOppositeDirection() {
        return false;
    }

    @Override
    public boolean doesTurnChange() {
        return false;
    }
}

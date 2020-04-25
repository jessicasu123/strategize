package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;

public class ChangeToNewStateMove implements MoveType{

    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        moving.changeState(playerState);
    }


}

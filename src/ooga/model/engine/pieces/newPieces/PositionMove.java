package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class PositionMove implements MoveType {

    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
       for(GamePiece neighbor: neighbors){
           if(neighbor.getPosition().equals(endCoordinateInfo)){
               neighbor.move(moving.getPosition());
           }
       }
       moving.move(endCoordinateInfo);
    }

    @Override
    public boolean addOppositeDirection() {
        return false;
    }


    @Override
    public boolean doesTurnChange() {
        return true;
    }

}

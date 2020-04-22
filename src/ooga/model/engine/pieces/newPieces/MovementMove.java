package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;

import java.util.List;

public class MovementMove implements MoveType {

    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState) {
       for(GamePiece neighbor: neighbors){
           if(neighbor.getPosition().equals(endCoordinateInfo)){
               neighbor.move(moving.getPosition());
           }
       }
       moving.move(endCoordinateInfo);
    }

}

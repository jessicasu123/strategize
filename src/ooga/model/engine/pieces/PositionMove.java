package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import java.util.List;

/**
 * For pieces whose position needs to change, this class swaps the positions of the piece that is being moved
 * and the piece that is at the coordinate of where the other piece is being moved to
 * @author Holly Ansel
 */
public class PositionMove implements MoveType {

    /**
     * Changes the position of the game piece being moved to the end coordinate and
     * finds the game piece living at the end coordinate and moves it to the original location of the
     * piece being moved (swaps their locations)
     * @param moving - the game piece that is being moved
     * @param endCoordinateInfo - the end coordinate of where that piece is being moved to
     * @param neighbors - the neighbors of the game piece being moved
     * @param playerState - the player who is moving the piece
     * @param direction - the direction this piece is being moved in
     */
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
       for(GamePiece neighbor: neighbors){
           if(neighbor.getPosition().equals(endCoordinateInfo)){
               neighbor.move(moving.getPosition());
           }
       }
       moving.move(endCoordinateInfo);
    }



}

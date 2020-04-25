package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;
/**
 * This class changes the gamepiece moving to a new state
 * @author Brian Li
 */
public class ChangeToNewStateMove implements MoveType{

    /**
     * @param moving - the game piece that is being moved
     * @param endCoordinateInfo - the end coordinate of where that piece is being moved to
     * @param neighbors - the neighbors of the game piece being moved
     * @param playerState - the player who is moving the piece
     * @param direction - the direction this piece is being moved in
     */
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        moving.changeState(playerState);
    }


}

package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;

/**
 * Clear Objects move type eliminates all of the objects of a piece
 * This is normally utilized as the last move type enacts for pieces that require this functionality
 * @author Holly Ansel
 */
public class ClearObjectsMove implements MoveType {
    /**
     * Removes all of the objects from this game piece
     * @param moving - the game piece that is being moved
     * @param endCoordinateInfo - the end coordinate of where that piece is being moved to
     * @param neighbors - the neighbors of the game piece being moved
     * @param playerState - the player who is moving the piece
     * @param direction - the direction this piece is being moved in
     */
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        moving.incrementNumObjects(-moving.getNumObjects());
    }


}

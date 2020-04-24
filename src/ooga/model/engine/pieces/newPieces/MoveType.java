package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

/**
 * This interface is responsible for acting to create a move
 * which can be determined by a variety of different conditions.
 *
 * Different move types (specified in the game configuration file)
 * will be used by the GamePiece to create all the effects of a move
 * (when multiple move types are combined). Combining multiple allows
 * for pieces to have complex moves
 *
 * This interface provides the flexibility to potentially change/add/remove conditions
 * that are required for making a move in a game
 */
public interface MoveType{

    /**
     * Gives different move types all of the information that they need in order to complete their action
     * @param moving - the game piece that is being moved
     * @param endCoordinateInfo - the end coordinate of where that piece is being moved to
     * @param neighbors - the neighbors of the game piece being moved
     * @param playerState - the player who is moving the piece
     * @param direction - the direction this piece is being moved in
     */
    void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction);


}

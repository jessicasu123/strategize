package ooga.model.engine.pieces.convertibleNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

import java.util.Collection;
import java.util.List;
/**
 * CODE MASTERPIECE (PT 2):
 * This class goes along with the Code Masterpiece because it shows the ConvertibleNeighborFinder interface,
 * which is a good example of FLEXIBILITY because any concrete class that chooses to implement
 * the findNeighborsToConvert method can be called on by the ChangeOpponentPiecesMove (PT 1) class.
 */

/**
 * This interface is responsible for finding all the neighbors that
 * should potentially be converted in a game. These neighbors can be
 * converted (by another class) to the empty state, another player state, or a state with
 * a different number of objects.
 *
 */
public interface ConvertibleNeighborFinder {
    /**
     * Finds all the neighbors that need to be converted to a different state
     * @param currCoordinate - current (start) coordinate
     * @param endCoordinate - end coordinate (position of the last neighbor to be converted)
     * @param playerID - the current player's ID
     * @param direction  - the direction that the player is moving in
     * @param numObjects - number of objects (continue giving number of objects until there are none left)
     * @param neighbors - the list of all possible neighbors to be considered
     * @return a list of all of the game pieces to convert
     */
    List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate,
                                                 int numObjects, int playerID, int direction, List<GamePiece> neighbors);
}

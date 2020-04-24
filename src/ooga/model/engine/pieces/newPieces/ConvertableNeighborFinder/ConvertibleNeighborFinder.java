package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

/**
 * This interface is responsible for finding all the neighbors that
 * should potentially be converted in a game. These neighbors can be
 * converted to the empty state, another player state, or a state with
 * a different number of objects.
 *
 */
public interface ConvertibleNeighborFinder {
    /**
     * Finds all the neighbors that need to be converted to a different state
     * @param currCoordinate - current (start) coordinate
     * @param endCoordinate - end coordinate (position of the last neighbor to be converted)
     * @param playerID - the current player's ID
     * @param numObjects - number of objects (continue giving number of objects until there are none left)
     * @param neighbors - the list of all possible neighbors to be considered
     * @return
     */
    public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate,
                                                  int numObjects, int playerID,int direction, List<GamePiece> neighbors);
}
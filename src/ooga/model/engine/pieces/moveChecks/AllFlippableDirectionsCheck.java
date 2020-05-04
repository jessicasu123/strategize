package ooga.model.engine.pieces.moveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.convertibleNeighborFinder.NeighborsInAllDirectionsFinder;
import ooga.model.engine.pieces.GamePiece;

import java.util.List;

/**
 * This class is responsible for checking all eight directions around a certain cell
 * and determining whether at least one of them contains an opponent that can be flipped.
 * Any number of opponents can be flipped as long as they are in a contiguous line between
 * an empty spot and another player piece.
 *
 * @author Jessica Su
 */
public class AllFlippableDirectionsCheck implements MoveCheck{
    private NeighborsInAllDirectionsFinder myNeighborhoodFinder;
    private int[][] myDirections;

    public AllFlippableDirectionsCheck() {
        myNeighborhoodFinder = new NeighborsInAllDirectionsFinder();
        myDirections = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
    }

    /**
     * Determines if there is AT LEAST 1 direction containing a flippable neighbor.
     * @param startingLocation - starting location of piece
     * @param checking - the piece being checked
     * @param neighbors - all the piece's neighbors
     * @param player - the player's state
     * @param directions - the directions of the pieces
     * @return true if there is at least 1 flippable neighbor, false if there are none
     */
    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        for (int i = 0; i < myDirections.length;i++) {
            int[] direction = myDirections[i];
            if (myNeighborhoodFinder.checkFlippableDirection(startingLocation.getRow(),
                    startingLocation.getCol(), player, direction[0], direction[1], neighbors)) {
                return true;
            }
        }

        return false;
    }
}

package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

public class NeighborAtEndCoordinateFinder implements ConvertibleNeighborFinder {
    /**
     * Given a list of all of the neighbors and an end coordinate, finds the piece at that end coordinate
     * @author Sanya Kochhar
     */
    @Override
    public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate, int numObjects,
                                                  int playerID, int direction, List<GamePiece> neighbors) {
        List<GamePiece> neighborToReturn = new ArrayList<>();

        for (GamePiece neighbor: neighbors) {
            if (neighbor.getPosition().equals(endCoordinate)) {
                neighborToReturn.add(neighbor);
            }
        }
        return neighborToReturn;
    }
}

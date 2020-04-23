package ooga.model.engine.pieces.newPieces.NeighborhoodConverters;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

public class FindNeighborAtEndCoord implements NeighborConverterFinder {
    /**
     * Given a list of all of the neighbors and an end coordinate, finds the piece at that end coordinate
     */
    @Override
    public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate, int numObjects,
                                                  int playerID, int direction, List<GamePiece> neighbors) {
        List<GamePiece> neighborToReturn = new ArrayList<>();
        for (GamePiece neighbor: neighbors) {
            if (neighbor.getPosition() == endCoordinate) {
                neighborToReturn.add(neighbor);
            }
        }
        return neighborToReturn;
    }
}

package ooga.model.engine.pieces.convertibleNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for finding neighbors in between coordinates
 * It searches by incrementing the row and column until the target row and column is reached
 * Works for any direction type (row, column, diagonal)
 * @author Holly Ansel
 */
public class NeighborsBetweenCoordinatesFinder implements ConvertibleNeighborFinder {

    /**
     * @param currCoordinate - current (start) coordinate
     * @param endCoordinate - end coordinate (position of the last neighbor to be converted)
     * @param numObjects - number of objects (continue giving number of objects until there are none left)
     * @param playerID - the current player's ID
     * @param direction  - the direction that the player is moving in
     * @param neighbors - the list of all possible neighbors to be considered
     * @return a list of all the game pieces in between the start and end coordinates
     */
    @Override
    public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate, int numObjects,
                                                  int playerID, int direction, List<GamePiece> neighbors) {
        List<GamePiece> neighborsToConvert = new ArrayList<>();
        int currRow = currCoordinate.getRow();
        int currCol = currCoordinate.getCol();
        int targetRow = endCoordinate.getRow();
        int targetCol = endCoordinate.getCol();

        int rowDirection = getDirectionGroup(currRow, targetRow);
        int colDirection = getDirectionGroup(currCol, targetCol);
        checkInBetween(currRow,currCol,rowDirection, colDirection, neighbors,targetRow,targetCol, neighborsToConvert);

        return neighborsToConvert;
    }

    private int getDirectionGroup(int curr, int target) {
        return Integer.compare(target, curr);
    }

    private void checkInBetween(int currRowPos, int currColPos, int rowOffset, int colOffset, List<GamePiece> neighbors,
                                   int targetRow, int targetCol, List<GamePiece> neighborsToConvert) {
        int currRow = currRowPos + rowOffset;
        int currCol = currColPos + colOffset;
        GamePiece neighbor = getPieceNeighborFromCoordinate(neighbors, new Coordinate(currRow, currCol));
        while (currRow != targetRow || currCol != targetCol) {
            if(neighbor != null){
                neighborsToConvert.add(neighbor);
            }
            if(currRow != targetRow) {
                currRow += rowOffset;
            }
            if(currCol != targetCol) {
                currCol += colOffset;
            }
            neighbor = getPieceNeighborFromCoordinate(neighbors, new Coordinate(currRow, currCol));
        }
    }

    private GamePiece getPieceNeighborFromCoordinate(List<GamePiece> neighbors, Coordinate coord) {
        for (GamePiece neighbor: neighbors) {
            if (neighbor.getPosition().equals(coord)) {
                return neighbor;
            }
        }
        return null;
    }



}

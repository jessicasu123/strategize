package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

public class FindAllFlippableNeighbors implements ConvertableNeighborFinder {

    private int currRowPos;
    private int currColPos;
    private int[][] directions;
    private List<GamePiece> neighborsToConvert;
    private int myPlayerID;

    public FindAllFlippableNeighbors() {
        neighborsToConvert = new ArrayList<>();
        directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
    }

    @Override
    public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate, int numObjects,
                                                  int playerID,int direction, List<GamePiece> neighbors) {
        myPlayerID = playerID;
        currRowPos = currCoordinate.getRow();
        currColPos = currCoordinate.getCol();
        return checkValidMoveInAnyDirection(neighbors);
    }

    private List<GamePiece> checkValidMoveInAnyDirection(List<GamePiece> neighbors) {
        for (int i = 0; i < directions.length;i++) {
            int[] direction = directions[i];
            checkFlippableDirection(currRowPos, currColPos, myPlayerID, direction[0], direction[1], neighbors);
        }
        return neighborsToConvert;
    }

    /**
     * Used by CheckAllFlippableDirections.
     * @param rowOffset
     * @param colOffset
     * @param neighbors
     * @return
     */
    public boolean checkFlippableDirection(int currRowPos, int currColPos, int myPlayerID,
                                           int rowOffset, int colOffset, List<GamePiece> neighbors) {
        int currRow = currRowPos + rowOffset;
        int currCol = currColPos + colOffset;
        int numOpponents = 0;
        List<GamePiece> possibleNeighborsToConvert = new ArrayList<>();
        GamePiece neighbor = getPieceNeighborFromCoordinate(neighbors, new Coordinate(currRow, currCol));
        while (neighbor!=null) {
            if (neighbor.getState()==0) return false;
            else if (neighbor.getState()==myPlayerID && numOpponents==0) return false;
            else if (neighbor.getState()!= myPlayerID && neighbor.getState()!=0) {
                possibleNeighborsToConvert.add(neighbor);
                numOpponents++;
            }
            else if (neighbor.getState()==myPlayerID && numOpponents >0) {
                neighborsToConvert.addAll(possibleNeighborsToConvert);
                return true;
            } else {
                return false;
            }
            currRow += rowOffset;
            currCol += colOffset;
            neighbor = getPieceNeighborFromCoordinate(neighbors, new Coordinate(currRow, currCol));
        }
        return false;
    }

    private GamePiece getPieceNeighborFromCoordinate(List<GamePiece> neighbors, Coordinate c) {
        for (GamePiece g: neighbors) {
            if (g.getPosition().equals(c)) {
                return g;
            }
        }
        return null;
    }
}
